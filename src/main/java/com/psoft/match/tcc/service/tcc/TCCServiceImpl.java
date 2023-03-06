package com.psoft.match.tcc.service.tcc;

import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.repository.tcc.TCCRepository;
import com.psoft.match.tcc.util.exception.tcc.TCCNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TCCServiceImpl implements TCCService {

    @Autowired
    private TCCRepository tccRepository;

    @Override
    public List<TCC> getAllTccs() {
        return tccRepository.findAll();
    }

    @Override
    public List<TCC> getProfessorsTCCs() {
        return tccRepository
                .findAll()
                .stream()
                .filter(TCC::isCreatedByProfessor)
                .collect(Collectors.toList());
    }

    @Override
    public TCC findTCCById(Long id) {
        return tccRepository.findById(id).orElseThrow(() -> new TCCNotFoundException(id));
    }

    @Override
    public List<TCC> getTCCsByStatusAndTerm(TCCStatus tccStatus, String term) {
        return tccRepository.findAllByTccStatusAndTerm(tccStatus, term);
    }

    @Transactional
    @Override
    public TCC saveTCC(TCC tcc) {
        return tccRepository.save(tcc);
    }

    @Transactional
    @Override
    public TCC createTCC(TCCDTO tccDTO, Student student) {
        TCC tcc = new TCC(student, tccDTO.getTitle(), tccDTO.getDescription(), TCCStatus.PENDING_APPROVAL);
        return this.saveTCC(tcc);
    }

    @Transactional
    @Override
    public TCC createTCC(TCCDTO tccDTO, Professor professor) {
        TCC tcc = new TCC(professor, tccDTO.getTitle(), tccDTO.getDescription(), TCCStatus.APPROVED);
        return this.saveTCC(tcc);
    }

    @Override
    public List<TCC> getStudentsTCCs() {
        return this.getAllTccs()
                .stream()
                .filter(TCC::isCreatedByStudent)
                .collect(Collectors.toList());
    }

}
