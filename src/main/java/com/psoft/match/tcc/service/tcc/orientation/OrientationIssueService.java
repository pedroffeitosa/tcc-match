package com.psoft.match.tcc.service.tcc.orientation;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.UserRole;

import java.util.Collection;

public interface OrientationIssueService {

    void saveOrientationIssue(OrientationIssue orientationIssue);

    OrientationIssue registerOrientationIssue(Student student, TCC tcc, OrientationIssueDTO orientationIssueDTO);

    OrientationIssue registerOrientationIssue(Professor professor, TCC tcc, OrientationIssueDTO orientationIssueDTO);

    Collection<OrientationIssue> getOrientationIssues(String term, UserRole userRole);
}
