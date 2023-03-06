package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.ProfessorDTO;
import com.psoft.match.tcc.dto.StudentDTO;
import com.psoft.match.tcc.dto.StudyAreaDTO;
import com.psoft.match.tcc.dto.TCCOrientationDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Admin;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.response.OrientationIssuesSummaryResponse;
import com.psoft.match.tcc.response.TCCSummaryResponse;

import java.util.List;

public interface AdminService {

    List<Admin> findAllAdmins();

    Professor createProfessor(ProfessorDTO professorDTO);

    StudyArea createStudyArea(StudyAreaDTO studyAreaDTO);

    Student createStudent(StudentDTO studentDTO);

    Professor updateProfessor(Long professorId, ProfessorDTO professorDTO);

    StudyArea updateStudyArea(Long studyAreaId, StudyAreaDTO studyAreaDTO);

    Student updateStudent(Long studentId, StudentDTO studentDTO);

    void deleteProfessor(Long professorId);

    void deleteStudyArea(Long studyAreaId);

    void deleteStudent(Long studentId);

    void registerTCC(TCCOrientationDTO tccOrientationDTO);

    void finalizeTCC(Long tccId, String term);

    TCCSummaryResponse getTCCSummary(String term);

    List<TCC> getTCCs(String tccStatus, String term);

    OrientationIssuesSummaryResponse getOrientationIssues(String term);
}
