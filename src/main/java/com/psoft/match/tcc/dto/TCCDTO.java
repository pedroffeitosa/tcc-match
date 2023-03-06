package com.psoft.match.tcc.dto;

import java.util.Collection;

public class TCCDTO {

    private String title;

    private String description;

    private Collection<Long> studyAreasIds;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Long> getStudyAreasIds() {
        return studyAreasIds;
    }
}
