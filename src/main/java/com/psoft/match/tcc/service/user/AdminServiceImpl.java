package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.ProfessorDTO;
import com.psoft.match.tcc.dto.StudentDTO;
import com.psoft.match.tcc.dto.StudyAreaDTO;
import com.psoft.match.tcc.dto.TCCOrientationDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;
import com.psoft.match.tcc.model.user.*;
import com.psoft.match.tcc.repository.user.AdminRepository;
import com.psoft.match.tcc.response.OrientationIssuesSummaryResponse;
import com.psoft.match.tcc.response.TCCSummaryResponse;
import com.psoft.match.tcc.service.study_area.StudyAreaService;
import com.psoft.match.tcc.service.tcc.TCCService;
import com.psoft.match.tcc.service.tcc.orientation.OrientationIssueService;
import com.psoft.match.tcc.util.exception.professor.UnavailableProfessorException;
import com.psoft.match.tcc.util.exception.studyarea.StudyAreaAlreadyExistsException;
import com.psoft.match.tcc.util.exception.tcc.InvalidTermException;
import com.psoft.match.tcc.util.exception.tcc.PendingTCCException;
import com.psoft.match.tcc.util.exception.user.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TCCMatchUserService<Admin> tccMatchUserService;

    @Autowired
    private StudyAreaService studyAreaService;

    @Autowired
    private TCCService tccService;

    @Autowired
    private OrientationIssueService orientationIssueService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Transactional
    @Override
    public Professor createProfessor(ProfessorDTO professorDTO) {
        TCCMatchUser user = tccMatchUserService.findByEmailOpt(professorDTO.getEmail()).orElse(null);
        if (user != null) throw new UserAlreadyExistsException(professorDTO.getEmail());

        Professor professor = this.buildProfessor(professorDTO);
        tccMatchUserService.saveUser(professor);
        return professorService.saveProfessor(professor);
    }

    @Transactional
    @Override
    public StudyArea createStudyArea(StudyAreaDTO studyAreaDTO){
        StudyArea studyArea = studyAreaService.findStudyAreaByDescriptionOpt(studyAreaDTO.getDescription()).orElse(null);
        if (studyArea != null) {
            throw new StudyAreaAlreadyExistsException(studyAreaDTO.getDescription());
        }

        StudyArea newStudyArea = this.buildStudyArea(studyAreaDTO);
        return studyAreaService.saveStudyArea(newStudyArea);
    }

    @Transactional
    @Override
    public Professor updateProfessor(Long id, ProfessorDTO professorDTO) {
        Professor professor = professorService.findProfessorById(id);

        this.updateProfessorFields(professor, professorDTO);

        tccMatchUserService.saveUser(professor);
        return professorService.saveProfessor(professor);
    }

    private void updateProfessorFields(Professor professor, ProfessorDTO professorDTO) {
        if (professorDTO.getFullName() != null) {
            professor.setFullName(professorDTO.getFullName());
        }
        if (professorDTO.getUsername() != null) {
            professor.setUsername(professorDTO.getUsername());
        }
        if (professorDTO.getEmail() != null) {
            professor.setEmail(professorDTO.getEmail());
        }
    }

    @Transactional
    @Override
    public Student createStudent(StudentDTO studentDTO) {
        TCCMatchUser user = tccMatchUserService.findByEmailOpt(studentDTO.getEmail()).orElse(null);
        if (user != null) throw new UserAlreadyExistsException(studentDTO.getEmail());

        Student student = this.buildStudent(studentDTO);
        tccMatchUserService.saveUser(student);
        return studentService.saveStudent(student);
    }

    @Transactional
    @Override
    public StudyArea updateStudyArea(Long studyAreaId, StudyAreaDTO studyAreaDTO) {
        StudyArea studyArea = studyAreaService.findStudyAreaById(studyAreaId);
        studyArea.setDescription(studyAreaDTO.getDescription());
        return studyAreaService.saveStudyArea(studyArea);
    }

    @Transactional
    @Override
    public Student updateStudent(Long studentId, StudentDTO studentDTO) {
        Student student = studentService.findStudentById(studentId);

        this.updateStudentFields(student, studentDTO);

        tccMatchUserService.saveUser(student);
        return studentService.saveStudent(student);
    }

    private void updateStudentFields(Student oldStudent, StudentDTO newStudent) {
        if (newStudent.getEmail() != null) {
            oldStudent.setEmail(newStudent.getEmail());
        }
        if (newStudent.getUsername() != null) {
            oldStudent.setUsername(newStudent.getUsername());
        }
        if (newStudent.getFullName() != null) {
            oldStudent.setFullName(newStudent.getFullName());
        }
        if (newStudent.getRegistration() != null) {
            oldStudent.setRegistration(newStudent.getRegistration());
        }
    }

    @Transactional
    @Override
    public void deleteProfessor(Long professorId) {
        Professor professor = professorService.findProfessorById(professorId);
        professorService.deleteProfessor(professor);
        tccMatchUserService.deleteUser(professor);
    }

    @Transactional
    @Override
    public void deleteStudyArea(Long studyAreaId) {
        StudyArea studyArea = studyAreaService.findStudyAreaById(studyAreaId);
        studyAreaService.deleteStudyArea(studyArea);
    }

    @Transactional
    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentService.findStudentById(studentId);
        studentService.deleteStudent(student);
        tccMatchUserService.deleteUser(student);
    }

    @Transactional
    @Override
    public void registerTCC(TCCOrientationDTO tccOrientationDTO) {
        Student student = studentService.findStudentById(tccOrientationDTO.getStudentId());
        TCC tcc = tccService.findTCCById(tccOrientationDTO.getTccId());
        Professor advisor = tcc.getAdvisor();

        this.validateTerm(tccOrientationDTO.getTerm());
        this.validateTCCRegistration(tcc);

        student.setTcc(tcc);
        tcc.setAdvisedStudent(student);
        tcc.setTccStatus(TCCStatus.ON_GOING);
        tcc.setTerm(tccOrientationDTO.getTerm());

        professorService.saveProfessor(advisor);
        studentService.saveStudent(student);
        tccService.saveTCC(tcc);
    }

    private void validateTCCRegistration(TCC tcc) {
        Professor advisor = tcc.getAdvisor();

        if (!advisor.isAvailable()) throw new UnavailableProfessorException(advisor.getId());

        if (tcc.getTccStatus().equals(TCCStatus.PENDING_APPROVAL)) {
            throw new PendingTCCException(tcc.getId());
        }
    }

    @Override
    public void finalizeTCC(Long tccId, String term) {
        this.validateTerm(term);

        TCC tcc = tccService.findTCCById(tccId);

        tcc.setTerm(term);
        tcc.finalizeTCC();
        tccService.saveTCC(tcc);
    }

    @Override
    public TCCSummaryResponse getTCCSummary(String term) {
        this.validateTerm(term);

        Collection<TCC> onGoingTCCs = tccService.getTCCsByStatusAndTerm(TCCStatus.ON_GOING, term);
        Collection<TCC> finishedTCCs = tccService.getTCCsByStatusAndTerm(TCCStatus.FINISHED, term);

        return new TCCSummaryResponse(term, onGoingTCCs, finishedTCCs);
    }

    @Override
    public List<TCC> getTCCs(String tccStatus, String term) {
        this.validateTerm(term);

        TCCStatus status = TCCStatus.fromText(tccStatus);
        return tccService.getTCCsByStatusAndTerm(status, term);
    }

    @Override
    public OrientationIssuesSummaryResponse getOrientationIssues(String term) {
        this.validateTerm(term);

        Collection<OrientationIssue> studentIssues = orientationIssueService.getOrientationIssues(term, UserRole.STUDENT);
        Collection<OrientationIssue> professorIssues = orientationIssueService.getOrientationIssues(term, UserRole.PROFESSOR);

        return new OrientationIssuesSummaryResponse(studentIssues, professorIssues, term);
    }

    private void validateTerm(String term) {
        Pattern pattern2000 = Pattern.compile("20[0-2]\\d\\.[1-2]");
        Pattern pattern1900 = Pattern.compile("19\\d{2}\\.[1-2]");

        if (!(pattern2000.matcher(term).find() || pattern1900.matcher(term).find())) throw new InvalidTermException(term);
    }

    private Professor buildProfessor(ProfessorDTO professorDTO) {
        String encryptedPassword = passwordEncoder.encode(professorDTO.getPassword());
        return new Professor(professorDTO.getFullName(), professorDTO.getEmail(), professorDTO.getUsername(), encryptedPassword, professorDTO.getLabs(), professorDTO.getQuota());
    }

    private Student buildStudent(StudentDTO studentDTO) {
        return new Student(studentDTO.getFullName(), studentDTO.getRegistration(), studentDTO.getEmail(), studentDTO.getExpectedConclusionTerm(), studentDTO.getUsername(), passwordEncoder.encode(studentDTO.getPassword()));
    }

    private StudyArea buildStudyArea(StudyAreaDTO studyAreaDTO){
        return new StudyArea(studyAreaDTO.getDescription());
    }
}
