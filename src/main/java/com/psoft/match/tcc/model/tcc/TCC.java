package com.psoft.match.tcc.model.tcc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class TCC {

    @Id
    @Column(unique = true)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String term;

    @ManyToOne
    private TCCMatchUser author;

    @OneToOne
    private Student advisedStudent;

    @ManyToOne
    private Professor advisor;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "tcc_id"),
            inverseJoinColumns = @JoinColumn(name = "study_area_id")
    )
    private Collection<StudyArea> studyAreas;

    @JsonIgnore
    @ManyToMany(mappedBy = "orientationInterests")
    private Collection<Student> interestedStudents;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedTCCs")
    private Collection<Professor> interestedProfessors;

    private TCCStatus tccStatus;

    public TCC() {
    }

    public TCC(TCCMatchUser author, String title, String description, TCCStatus tccStatus) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.tccStatus = tccStatus;
        this.studyAreas = new HashSet<>();
        this.interestedStudents = new HashSet<>();
        this.interestedProfessors = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Professor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Professor advisor) {
        this.advisor = advisor;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return this.advisor == null || this.advisedStudent == null;
    }

    public String toEmailFormat() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("[INFORMAÇÕES]\n")
                .append("Título do TCC: ").append(getTitle()).append("\n")
                .append("Descrição: ").append(getDescription()).append("\n")
                .append("Autor: ").append(author.getFullName()).append("\n");

        getStudyAreas().forEach(studyArea -> sb.append("- ").append(studyArea.getDescription()).append("\n"));

        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Student getAdvisedStudent() {
        return advisedStudent;
    }

    public void setAdvisedStudent(Student student) {
        this.advisedStudent = student;
    }

    public Collection<StudyArea> getStudyAreas() {
        return studyAreas;
    }

    public void setStudyAreas(Collection<StudyArea> studyAreas) {
        this.studyAreas = studyAreas;
    }

    public Collection<Student> getInterestedStudents() {
        return interestedStudents;
    }

    public void setTccStatus(TCCStatus tccStatus) {
        this.tccStatus = tccStatus;
    }

    public Collection<Professor> getInterestedProfessors() {
        return interestedProfessors;
    }

    public boolean addInterestedProfessor(Professor interestedProfessor) {
        return this.interestedProfessors.add(interestedProfessor);
    }

    public boolean addInterestedStudent(Student interestedStudent) {
        return this.interestedStudents.add(interestedStudent);
    }

    public boolean removeInterestedStudent(Student interestedStudent) {
        return this.interestedStudents.remove(interestedStudent);
    }

    public boolean removeInterestedStudent(Professor interestedProfessor) {
        return this.interestedProfessors.remove(interestedProfessor);
    }

    public boolean addStudyArea(StudyArea studyArea) {
        return this.studyAreas.add(studyArea);
    }

    public boolean removeStudyArea(StudyArea studyArea) {
        return this.studyAreas.remove(studyArea);
    }

    public TCCStatus getTccStatus() {
        return tccStatus;
    }

    public void approveTCC() {
        this.tccStatus = TCCStatus.ON_GOING;
    }

    public void finalizeTCC() {
        this.tccStatus = TCCStatus.FINISHED;
    }

    public TCCMatchUser getAuthor() {
        return author;
    }

    public void setAuthor(TCCMatchUser author) {
        this.author = author;
    }

    @JsonIgnore
    public boolean isCreatedByStudent() {
        return author != null && author instanceof Student;
    }

    @JsonIgnore
    public boolean isCreatedByProfessor() {
        return author != null && author instanceof Professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TCC tcc = (TCC) o;
        return Objects.equals(id, tcc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}