package com.psoft.match.tcc.service.study_area;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudyAreaService {

    StudyArea saveStudyArea(StudyArea studyArea);

    List<StudyArea> findStudyAreasById(Collection<Long> studyAreasIds);

    void notifyNewTCCToInterestedStudents(StudyArea studyArea, TCC tcc);

    void notifyNewTCCToInterestedStudents(Collection<StudyArea> studyAreas, TCC tcc);

    void deleteStudyArea(StudyArea studyArea);
    
    StudyArea findStudyAreaById(Long id);

    Optional<StudyArea> findStudyAreaByDescriptionOpt(String description);
}
