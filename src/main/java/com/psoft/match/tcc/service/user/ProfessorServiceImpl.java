package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.repository.user.ProfessorRepository;
import com.psoft.match.tcc.service.email.EmailService;
import com.psoft.match.tcc.service.study_area.StudyAreaService;
import com.psoft.match.tcc.service.tcc.TCCService;
import com.psoft.match.tcc.service.tcc.orientation.OrientationIssueService;
import com.psoft.match.tcc.util.exception.professor.*;
import com.psoft.match.tcc.util.exception.student.StudentDoesNotHaveOrientationInterestException;
import com.psoft.match.tcc.util.exception.tcc.TCCRegisteredByProfessorException;
import com.psoft.match.tcc.util.exception.user.TCCDoesNotBelongToUserException;
import com.psoft.match.tcc.util.exception.user.UserAlreadyInterestedInStudyAreaException;
import com.psoft.match.tcc.util.exception.user.UserAlreadyInterestedInTCCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TCCMatchUserService<Professor> tccMatchUserService;

    @Autowired
    private TCCService tccService;

    @Autowired
    private StudyAreaService studyAreaService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrientationIssueService orientationIssueService;

    @Override
    public List<Professor> getAvailableProfessors() {
        return professorRepository
                .findAll()
                .stream()
                .filter(Professor::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public List<Professor> getAvailableProfessorsWithSharedInterests(Student student) {
        return this.getAvailableProfessors()
                .stream()
                .filter(professor -> professor.hasSharedInterestedWith(student))
                .collect(Collectors.toList());
    }

    @Override
    public Professor findProfessorById(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    @Transactional
    @Override
    public Professor saveProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional
    @Override
    public void approveOrientationInterest(Long tccId, Long studentId) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        Student student = studentService.findStudentById(studentId);
        TCC tcc = tccService.findTCCById(tccId);

        this.validateOrientation(professor, tcc, student);

        tcc.approveTCC();
        tcc.setAdvisedStudent(student);
        tcc.setAdvisor(professor);

        emailService.notifyApprovedOrientationToAdmin(tcc);

        tccService.saveTCC(tcc);
    }

    @Transactional
    @Override
    public void refuseOrientationInterest(Long tccId, Long studentId) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        Student student = studentService.findStudentById(studentId);
        TCC tcc = tccService.findTCCById(tccId);

        this.validateOrientation(professor, tcc, student);

        tcc.removeInterestedStudent(student);
        student.removeOrientationInterest(tcc);

        tccService.saveTCC(tcc);
        studentService.saveStudent(student);
    }

    private void validateOrientation(Professor professor, TCC tcc, Student student) {
        if (!professor.getRegisteredTCCs().contains(tcc)) throw new TCCDoesNotBelongToUserException(professor.getFullName(), tcc.getId());
        if (!tcc.getInterestedStudents().contains(student)) throw new StudentDoesNotHaveOrientationInterestException(student.getFullName(), tcc.getTitle());
    }

    @Transactional
    @Override
    public void deleteProfessor(Professor professor) {
        professorRepository.delete(professor);
    }

    @Transactional
    @Override
    public void declareOrientationInterest(Long tccId) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        TCC tcc = tccService.findTCCById(tccId);

        this.validateOrientationInterest(professor, tcc);

        professor.addOrientationInterest(tcc);
        tcc.addInterestedProfessor(professor);

        emailService.notifyNewOrientationInterestToUser(tcc.getAuthor(), professor, tcc);

        tccService.saveTCC(tcc);
        professorRepository.save(professor);
    }

    private void validateOrientationInterest(Professor professor, TCC tcc) {
        if (!tcc.isCreatedByStudent()) throw new TCCRegisteredByProfessorException(tcc.getId());
        if (tcc.getInterestedProfessors().contains(professor)) throw new UserAlreadyInterestedInTCCException(professor.getFullName(), tcc.getTitle());
    }

    @Transactional
    @Override
    public void addInterestedStudyArea(Long studyAreaId) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        StudyArea studyArea = studyAreaService.findStudyAreaById(studyAreaId);

        this.validateAddInterestedStudyArea(professor, studyArea);

        professor.addInterestedStudyArea(studyArea);
        studyArea.addInterestedProfessor(professor);

        professorRepository.save(professor);
        studyAreaService.saveStudyArea(studyArea);
    }

    private void validateAddInterestedStudyArea(Professor professor, StudyArea studyArea){
        if(professor.getInterestedStudyAreas().contains(studyArea)){
            throw new UserAlreadyInterestedInStudyAreaException(professor.getFullName(), studyArea.getDescription());
        }
    }

    @Transactional
    @Override
    public void updateQuota(Integer quota) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);

        this.validateQuota(quota);

        professor.setQuota(quota);

        professorRepository.save(professor);
    }

    @Transactional
    @Override
    public OrientationIssue registerTCCOrientationIssue(Long tccId, OrientationIssueDTO orientationIssueDTO) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        TCC tcc = tccService.findTCCById(tccId);

        professorRepository.save(professor);
        return orientationIssueService.registerOrientationIssue(professor, tcc, orientationIssueDTO);
    }

    private void validateQuota(Integer quota) {
        if(quota < 0) throw new InvalidQuotaException();
    }

    @Override
    public Collection<TCC> getRegisteredTCCs() {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);

        return professor.getRegisteredTCCs();
    }

    @Override
    public Collection<TCC> getOrientationSolicitations() {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        return professor
                .getRegisteredTCCs()
                .stream()
                .filter(TCC::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TCC> getStudentsTCCs() {
        return tccService.getStudentsTCCs();
    }

    @Transactional
    @Override
    public TCC createProfessorTCC(TCCDTO tccdto) {
        Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
        Collection<StudyArea> studyAreas = studyAreaService.findStudyAreasById(tccdto.getStudyAreasIds());

        TCC tcc = tccService.createTCC(tccdto, professor);
        professor.registerTCC(tcc);

        professorRepository.save(professor);
        studyAreaService.notifyNewTCCToInterestedStudents(studyAreas, tcc);

        return tcc;
    }

	@Override
	public Collection<TCC> getOngoingOrientations() {
		Professor professor = tccMatchUserService.getLoggedUser(Professor.class);
		return professor.getOnGoingTCCs();
	}

}
