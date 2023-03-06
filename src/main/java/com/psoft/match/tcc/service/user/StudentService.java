package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;

import java.util.List;

public interface StudentService {

	OrientationIssue registerTCCOrientationIssue(OrientationIssueDTO orientationIssue);

    void declareTCCOrientationInterest(Long tccId);

	Student findStudentById(Long id);

	Student saveStudent(Student student);

	void deleteStudent(Student student);

	StudyArea addInterestedStudyArea(Long idStudyArea);
	
	List<Professor> getProfessorsWithSharedInterests();
	
	TCC createStudentTCC(TCCDTO tcc);
	
	List<TCC> getProfessorRegisteredTCCs();
}
