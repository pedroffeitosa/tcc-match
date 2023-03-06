package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.repository.user.StudentRepository;
import com.psoft.match.tcc.service.email.EmailService;
import com.psoft.match.tcc.service.study_area.StudyAreaService;
import com.psoft.match.tcc.service.tcc.TCCService;
import com.psoft.match.tcc.service.tcc.orientation.OrientationIssueService;
import com.psoft.match.tcc.util.exception.student.StudentNotFoundException;
import com.psoft.match.tcc.util.exception.tcc.TCCRegisteredByStudentException;
import com.psoft.match.tcc.util.exception.tcc.UnavailableTCCException;
import com.psoft.match.tcc.util.exception.user.UserAlreadyInterestedInStudyAreaException;
import com.psoft.match.tcc.util.exception.user.UserAlreadyInterestedInTCCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TCCService tccService;

    @Autowired
    private StudyAreaService studyAreaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private TCCMatchUserService<Student> tccMatchUserService;

    @Autowired
    private OrientationIssueService orientationIssueService;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public OrientationIssue registerTCCOrientationIssue(OrientationIssueDTO orientationIssueDTO) {
        Student student = tccMatchUserService.getLoggedUser(Student.class);
        TCC tcc = student.getTcc();

        studentRepository.save(student);
        return orientationIssueService.registerOrientationIssue(student, tcc, orientationIssueDTO);
    }

    @Transactional
    @Override
    public void declareTCCOrientationInterest(Long tccId) {
        Student student = tccMatchUserService.getLoggedUser(Student.class);
        TCC interestedTcc = tccService.findTCCById(tccId);

        this.validateTCCOrientationRequest(student, interestedTcc);

        student.addOrientationInterest(interestedTcc);
        interestedTcc.addInterestedStudent(student);

        emailService.notifyNewOrientationInterestToUser(interestedTcc.getAuthor(), student, interestedTcc);

        tccService.saveTCC(interestedTcc);
        studentRepository.save(student);
    }

    private void validateTCCOrientationRequest(Student student, TCC interestedTCC) {
        if (!interestedTCC.isAvailable()) throw new UnavailableTCCException(interestedTCC.getId());
        if (!interestedTCC.isCreatedByProfessor()) throw new TCCRegisteredByStudentException(interestedTCC.getId());
        if (interestedTCC.getInterestedStudents().contains(student)) throw new UserAlreadyInterestedInTCCException(student.getFullName(), interestedTCC.getTitle());
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional
    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
	public StudyArea addInterestedStudyArea(Long idStudyArea) {
		StudyArea studyArea = studyAreaService.findStudyAreaById(idStudyArea);
        Student student = tccMatchUserService.getLoggedUser(Student.class);

        if (student.getInterestedStudyAreas().contains(studyArea)) throw new UserAlreadyInterestedInStudyAreaException(student.getFullName(), studyArea.getDescription());

		student.addInterestedArea(studyArea);
		studyArea.addInterestedStudent(student);

		studyAreaService.saveStudyArea(studyArea);
		studentRepository.save(student);
		return studyArea;
	}

	@Override
	public List<Professor> getProfessorsWithSharedInterests() {
		Student student = tccMatchUserService.getLoggedUser(Student.class);
		return professorService.getAvailableProfessorsWithSharedInterests(student);
	}

    @Transactional
	@Override
	public TCC createStudentTCC(TCCDTO tcc) {
        Student student = tccMatchUserService.getLoggedUser(Student.class);
		return tccService.createTCC(tcc, student);
	}

	@Override
	public List<TCC> getProfessorRegisteredTCCs() {
		return tccService.getProfessorsTCCs();
	}
}
