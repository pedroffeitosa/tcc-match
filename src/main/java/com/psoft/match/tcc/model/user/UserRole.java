package com.psoft.match.tcc.model.user;

public enum UserRole {

    PROFESSOR("PROFESSOR"),
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
