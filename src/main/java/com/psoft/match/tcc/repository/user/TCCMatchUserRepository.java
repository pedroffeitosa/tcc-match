package com.psoft.match.tcc.repository.user;

import com.psoft.match.tcc.model.user.TCCMatchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TCCMatchUserRepository extends JpaRepository<TCCMatchUser, Long> {

    Optional<TCCMatchUser> findUserByEmail(String email);

    Optional<TCCMatchUser> findByUsername(String username);
}
