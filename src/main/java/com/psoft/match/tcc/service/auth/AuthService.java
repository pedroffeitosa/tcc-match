package com.psoft.match.tcc.service.auth;

import com.psoft.match.tcc.dto.CredentialsDTO;
import com.psoft.match.tcc.model.user.TCCMatchUser;

public interface AuthService {

    String login(CredentialsDTO credentialsDTO);

    TCCMatchUser getLoggedUser();
}
