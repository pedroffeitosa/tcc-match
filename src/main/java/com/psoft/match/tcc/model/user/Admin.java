package com.psoft.match.tcc.model.user;

import javax.persistence.Entity;

@Entity
public class Admin extends TCCMatchUser {

    public Admin() {}

    public Admin(String fullName, String email, String username, String password) {
        super(fullName, email, username, password, UserRole.ADMIN);
    }

}
