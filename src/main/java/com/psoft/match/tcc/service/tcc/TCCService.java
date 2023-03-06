package com.psoft.match.tcc.service.tcc;

import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;

import java.util.List;

public interface TCCService {

    List<TCC> getAllTccs();

    List<TCC> getProfessorsTCCs();

    TCC findTCCById(Long id);

    List<TCC> getTCCsByStatusAndTerm(TCCStatus tccStatus, String term);

    TCC saveTCC(TCC tcc);

    TCC createTCC(TCCDTO tccDTO, Student student);

    TCC createTCC(TCCDTO tccDTO, Professor professor);

    List<TCC> getStudentsTCCs();
}
