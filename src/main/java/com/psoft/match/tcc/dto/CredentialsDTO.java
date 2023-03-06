package com.psoft.match.tcc.dto;

public class CredentialsDTO {

    private String password;

    private String username;

    public CredentialsDTO() {}

    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
