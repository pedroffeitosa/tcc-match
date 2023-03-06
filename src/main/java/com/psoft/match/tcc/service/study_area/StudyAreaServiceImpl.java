package com.psoft.match.tcc.service.study_area;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.repository.StudyAreaRepository;
import com.psoft.match.tcc.service.email.EmailService;
import com.psoft.match.tcc.util.exception.studyarea.StudyAreaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudyAreaServiceImpl implements StudyAreaService {

    @Autowired
    private StudyAreaRepository studyAreaRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public StudyArea saveStudyArea(StudyArea studyArea) {
        return studyAreaRepository.save(studyArea);
    }

    @Override
    public List<StudyArea> findStudyAreasById(Collection<Long> studyAreasIds) {
        return studyAreaRepository.findAllById(studyAreasIds);
    }

    @Override
    public void notifyNewTCCToInterestedStudents(StudyArea studyArea, TCC tcc) {
        studyArea.getInterestedStudents().forEach(student -> emailService.notifyNewTCCToInterestedUsers(student, studyArea, tcc));
    }

    @Override
    public void notifyNewTCCToInterestedStudents(Collection<StudyArea> studyAreas, TCC tcc) {
        studyAreas.forEach(studyArea -> this.notifyNewTCCToInterestedStudents(studyArea, tcc));
    }

    @Transactional
    @Override
    public void deleteStudyArea(StudyArea studyArea){
       studyAreaRepository.delete(studyArea);
   }

    @Override
    public StudyArea findStudyAreaById(Long id) {
        return studyAreaRepository.findById(id).orElseThrow(() -> new StudyAreaNotFoundException(id));
    }

    @Override
    public Optional<StudyArea> findStudyAreaByDescriptionOpt(String description) {
        return studyAreaRepository.findByDescription(description);
    }
}
