package com.psoft.match.tcc.dto;

import java.util.Collection;

public class ProfessorDTO {

    private String fullName;

    private String email;

    private Collection<String> labs;

    private String username;

    private String password;

    private Integer quota;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Collection<String> getLabs() {
        return labs;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getQuota() {
        return quota;
    }
}
