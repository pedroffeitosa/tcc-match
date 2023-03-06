package com.psoft.match.tcc.service.user;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.repository.user.TCCMatchUserRepository;
import com.psoft.match.tcc.service.auth.AuthService;
import com.psoft.match.tcc.service.tcc.orientation.OrientationIssueService;
import com.psoft.match.tcc.util.exception.common.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TCCMatchUserServiceImpl<T extends TCCMatchUser> implements TCCMatchUserService<T> {

    @Autowired
    private AuthService authService;

    @Autowired
    private TCCMatchUserRepository tccMatchUserRepository;

    @Autowired
    private OrientationIssueService orientationIssueService;

    @Override
    public T getLoggedUser(Class<T> klass) {
        T user = (T) authService.getLoggedUser();
        if (klass.isInstance(user)) return user;
        throw new UnauthorizedException();
    }

    @Transactional
    @Override
    public TCCMatchUser saveUser(TCCMatchUser user) {
        return tccMatchUserRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(TCCMatchUser user) {
        tccMatchUserRepository.delete(user);
    }

    @Transactional
    @Override
    public void registerOrientationIssue(TCCMatchUser user, TCC tcc, OrientationIssueDTO orientationIssueDTO) {
        OrientationIssue orientationIssue = new OrientationIssue(orientationIssueDTO.getRelatedIssue(), user, tcc);
        user.addOrientationIssue(orientationIssue);

        orientationIssueService.saveOrientationIssue(orientationIssue);
        tccMatchUserRepository.save(user);
    }

    @Override
    public Optional<TCCMatchUser> findByUsernameOpt(String username) {
        return tccMatchUserRepository.findByUsername(username);
    }

    @Override
    public Optional<TCCMatchUser> findByEmailOpt(String email) {
        return tccMatchUserRepository.findUserByEmail(email);
    }
}
