package com.psoft.match.tcc.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Professor extends TCCMatchUser {

    @JsonIgnore
    @ElementCollection
    private Collection<String> labs;

	private Integer quota;

    @JsonIgnore
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "professor_id"), inverseJoinColumns = @JoinColumn(name = "study_area_id"))
    private Collection<StudyArea> interestedStudyAreas;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "tcc_id")
    )
    private Set<TCC> interestedTCCs;

    public Professor() {}

    public Professor(String fullName, String email, String username, String password, Collection<String> labs, Integer quota) {
        super(fullName, email, username, password, UserRole.PROFESSOR);
        this.interestedStudyAreas = new HashSet<>();
        this.interestedTCCs = new HashSet<>();
        this.labs = labs;
        this.quota = quota;
    }

    public Collection<StudyArea> getInterestedStudyAreas() {
        return interestedStudyAreas;
    }
    
    public boolean addInterestedStudyArea(StudyArea studyArea) {
        return this.interestedStudyAreas.add(studyArea);
    }

    public boolean removeInterestedStudyArea(StudyArea studyArea) {
        return this.interestedStudyAreas.remove(studyArea);
    }

    public Collection<String> getLabs() {
        return labs;
    }

    public Collection<TCC> getInterestedTCCs() {
        return interestedTCCs;
    }

    @JsonIgnore
    public Collection<TCC> getOnGoingTCCs() {
        return this.getRegisteredTCCs()
                .stream()
                .filter(tcc -> tcc.getTccStatus().equals(TCCStatus.ON_GOING))
                .collect(Collectors.toList());
    }

    public boolean addOrientationInterest(TCC orientationInterest) {
        return this.interestedTCCs.add(orientationInterest);
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public boolean isAvailable() {
        return this.getOnGoingTCCs().size() < this.quota;
    }

    public boolean hasSharedInterestedWith(Student student) {
        for (StudyArea s : student.getInterestedStudyAreas()) {
            if (this.getInterestedStudyAreas().contains(s)) {
                return true;
            }
        }
        return false;
    }

    @PreRemove
    private void preRemove() {
        this.getRegisteredTCCs().forEach(tcc -> {
            tcc.setAdvisor(null);
            tcc.setTccStatus(TCCStatus.PENDING_APPROVAL);
            tcc.setTerm(null);
            tcc.setAuthor(null);
        });
        this.interestedTCCs.forEach(tcc -> tcc.removeInterestedStudent(this));
    }
}
