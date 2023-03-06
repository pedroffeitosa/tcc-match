package com.psoft.match.tcc.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TCCMatchUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private UserRole role;

    @JsonIgnore
    @ElementCollection
    private Collection<String> emails;

    @JsonIgnore
    @OneToMany
    private Set<OrientationIssue> orientationIssues;

    @JsonIgnore
    @OneToMany
    private Set<TCC> registeredTCCs;

    public TCCMatchUser() {}

    public TCCMatchUser(String fullName, String email, String username, String password, UserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.emails = new ArrayList<>();
        this.orientationIssues = new HashSet<>();
        this.registeredTCCs = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void receiveEmail(String email) {
        this.emails.add(email);
    }

    public Collection<String> getEmails() {
        return emails;
    }

    public Set<OrientationIssue> getOrientationIssues() {
        return orientationIssues;
    }

    public boolean addOrientationIssue(OrientationIssue orientationIssue) {
        return this.orientationIssues.add(orientationIssue);
    }

    public Set<TCC> getRegisteredTCCs() {
        return registeredTCCs;
    }

    public boolean registerTCC(TCC tcc) {
        return this.registeredTCCs.add(tcc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TCCMatchUser user = (TCCMatchUser) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
