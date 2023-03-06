package com.psoft.match.tcc.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends TCCMatchUser {

    private String registration;

    private String expectedConclusionTerm;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "tcc_id")
    )
    private Set<TCC> orientationInterests;

    @JsonIgnore
    @OneToOne
    private TCC tcc;

    @JsonIgnore
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "study_area_id"))
    private Set<StudyArea> interestedStudyAreas;

    public Student() {}

    public Student(String fullName, String registration, String email, String expectedConclusionTerm, String username, String password) {
        super(fullName, email, username, password, UserRole.STUDENT);
        this.registration = registration;
        this.expectedConclusionTerm = expectedConclusionTerm;
        this.interestedStudyAreas = new HashSet<>();
        this.orientationInterests = new HashSet<>();
        this.tcc = null;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getExpectedConclusionTerm() {
        return expectedConclusionTerm;
    }

    public void setExpectedConclusionTerm(String expectedConclusionTerm) {
        this.expectedConclusionTerm = expectedConclusionTerm;
    }

    public Set<StudyArea> getInterestedStudyAreas() {
        return interestedStudyAreas;
    }

    public boolean addInterestedArea(StudyArea studyArea) {
        return this.interestedStudyAreas.add(studyArea);
    }

    public boolean removeInterestedArea(StudyArea studyArea) {
        return this.interestedStudyAreas.remove(studyArea);
    }

    public boolean addOrientationInterest(TCC orientationInterest) {
        return this.orientationInterests.add(orientationInterest);
    }

    public boolean removeOrientationInterest(TCC orientationInterest) {
        return this.orientationInterests.remove(orientationInterest);
    }

    public TCC getTcc() {
        return tcc;
    }

    public void setTcc(TCC tcc) {
        this.tcc = tcc;
    }

    @PreRemove
    private void preRemove() {
        if (this.tcc != null) {
            this.tcc.setAdvisedStudent(null);
            this.tcc.setTccStatus(TCCStatus.PENDING_APPROVAL);
        }
        this.getRegisteredTCCs().forEach(tcc -> {
            tcc.setAuthor(null);
            tcc.setTccStatus(TCCStatus.PENDING_APPROVAL);
        });
    }
}
