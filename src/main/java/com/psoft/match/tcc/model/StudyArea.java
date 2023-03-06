package com.psoft.match.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class StudyArea {

    @Id
    @Column(unique = true)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedStudyAreas")
    private Collection<Student> interestedStudents;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedStudyAreas")
    private Collection<Professor> interestedProfessors;

    @JsonIgnore
    @ManyToMany(mappedBy = "studyAreas")
    private Set<TCC> tccs;

    public StudyArea() {}

    public StudyArea(String description) {
        this.description = description;
        this.interestedStudents = new HashSet<>();
        this.interestedProfessors = new HashSet<>();
        this.tccs = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Student> getInterestedStudents() {
        return interestedStudents;
    }

    public Collection<Professor> getInterestedProfessors() {
        return interestedProfessors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInterestedStudents(Collection<Student> interestedStudents) {
        this.interestedStudents = interestedStudents;
    }

    public void setInterestedProfessors(Collection<Professor> interestedProfessors) {
        this.interestedProfessors = interestedProfessors;
    }

    public boolean addInterestedStudent(Student student) {
        return this.interestedStudents.add(student);
    }

    public boolean removeInterestedStudent(Student student) {
        return this.interestedStudents.remove(student);
    }

    public boolean addInterestedProfessor(Professor professor) {
        return this.interestedProfessors.add(professor);
    }

    public boolean removeInterestedProfessor(Professor professor) {
        return this.interestedProfessors.remove(professor);
    }

    public boolean addTCC(TCC tcc) {
        return this.tccs.add(tcc);
    }

    public boolean removeTCC(TCC tcc) {
        return this.tccs.remove(tcc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyArea that = (StudyArea) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PreRemove
    private void preRemove() {
        this.tccs.forEach(tcc -> tcc.removeStudyArea(this));
        this.interestedProfessors.forEach(professor -> professor.removeInterestedStudyArea(this));
        this.interestedStudents.forEach(student -> student.removeInterestedArea(this));
    }
}
