package com.psoft.match.tcc.service.tcc.orientation;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.model.user.UserRole;
import com.psoft.match.tcc.repository.tcc.OrientationIssueRepository;
import com.psoft.match.tcc.service.user.TCCMatchUserService;
import com.psoft.match.tcc.util.exception.user.TCCDoesNotBelongToUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class OrientationIssueServiceImpl implements OrientationIssueService {

    @Autowired
    private OrientationIssueRepository orientationIssueRepository;

    @Autowired
    private TCCMatchUserService tccMatchUserService;

    @Transactional
    @Override
    public void saveOrientationIssue(OrientationIssue orientationIssue) {
        orientationIssueRepository.save(orientationIssue);
    }

    @Transactional
    @Override
    public OrientationIssue registerOrientationIssue(Student student, TCC tcc, OrientationIssueDTO orientationIssueDTO) {
        if (student.getTcc() != null && !student.getTcc().equals(tcc)) throw new TCCDoesNotBelongToUserException(student.getFullName(), tcc.getId());

        return this.createOrientationIssue(student, tcc, orientationIssueDTO);
    }

    @Transactional
    @Override
    public OrientationIssue registerOrientationIssue(Professor professor, TCC tcc, OrientationIssueDTO orientationIssueDTO) {
        if (!professor.getOnGoingTCCs().contains(tcc)) throw new TCCDoesNotBelongToUserException(professor.getFullName(), tcc.getId());

        return this.createOrientationIssue(professor, tcc, orientationIssueDTO);
    }

    private OrientationIssue createOrientationIssue(TCCMatchUser tccMatchUser, TCC tcc, OrientationIssueDTO orientationIssueDTO) {
        OrientationIssue orientationIssue = new OrientationIssue(orientationIssueDTO.getRelatedIssue(), tccMatchUser, tcc);
        tccMatchUser.addOrientationIssue(orientationIssue);

        tccMatchUserService.saveUser(tccMatchUser);
        return orientationIssueRepository.save(orientationIssue);
    }

    @Override
    public Collection<OrientationIssue> getOrientationIssues(String term, UserRole userRole){
        return orientationIssueRepository
                .findAll()
                .stream()
                .filter(o -> o.getTcc().getTerm().equals(term) && o.getUser().getRole().equals(userRole))
                .collect(Collectors.toList());
    }
}
