package com.psoft.match.tcc.response;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;

import java.util.Collection;
import java.util.stream.Collectors;

public class TCCResponse {

    private String title;

    private Collection<String> studyAreas;

    public TCCResponse(TCC tcc) {
        this.title = tcc.getTitle();
        this.studyAreas = tcc.getStudyAreas().stream().map(StudyArea::getDescription).collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<String> getStudyAreas() {
        return studyAreas;
    }

    public void setStudyAreas(Collection<String> studyAreas) {
        this.studyAreas = studyAreas;
    }
}
