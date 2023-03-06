package com.psoft.match.tcc.response;

import com.psoft.match.tcc.model.tcc.OrientationIssue;

import java.util.Collection;

public class OrientationIssuesSummaryResponse {

    private Collection<OrientationIssue> studentIssues;
    private Collection<OrientationIssue> professorIssues;
    private String term;

    public OrientationIssuesSummaryResponse(Collection<OrientationIssue> studentIssues, Collection<OrientationIssue> professorIssues, String term) {
        this.studentIssues = studentIssues;
        this.professorIssues = professorIssues;
        this.term = term;
    }

    public Collection<OrientationIssue> getStudentIssues() {
        return studentIssues;
    }

    public Collection<OrientationIssue> getProfessorIssues() {
        return professorIssues;
    }

    public String getTerm() {
        return term;
    }
}
