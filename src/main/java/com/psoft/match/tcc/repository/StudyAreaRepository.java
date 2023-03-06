package com.psoft.match.tcc.repository;

import com.psoft.match.tcc.model.StudyArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyAreaRepository extends JpaRepository<StudyArea, Long> {

    Optional<StudyArea> findByDescription(String description);
}