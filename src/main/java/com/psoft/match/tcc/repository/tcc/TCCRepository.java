package com.psoft.match.tcc.repository.tcc;

import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCCRepository extends JpaRepository<TCC, Long> {

    List<TCC> findAllByTccStatusAndTerm(TCCStatus tccStatus, String term);
}
