package com.psoft.match.tcc.response;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Student;

import java.util.Collection;

public class SolicitationResponse {

    private String tccTitle;

    private String tccDescription;

    private Collection<StudyArea> studyAreas;

    private Collection<Student> interestedStudents;

    public SolicitationResponse(TCC tcc) {
        this.tccTitle = tcc.getTitle();
        this.tccDescription = tcc.getDescription();
        this.studyAreas = tcc.getStudyAreas();
        this.interestedStudents = tcc.getInterestedStudents();
    }

    public String getTccTitle() {
        return tccTitle;
    }

    public String getTccDescription() {
        return tccDescription;
    }

    public Collection<StudyArea> getStudyAreas() {
        return studyAreas;
    }

    public Collection<Student> getInterestedStudents() {
        return interestedStudents;
    }

    public void setInterestedStudents(Collection<Student> interestedStudents) {
        this.interestedStudents = interestedStudents;
    }
}
