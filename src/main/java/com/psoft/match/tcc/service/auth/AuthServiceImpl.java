package com.psoft.match.tcc.service.auth;

import com.psoft.match.tcc.dto.CredentialsDTO;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.security.JWTUtil;
import com.psoft.match.tcc.service.user.TCCMatchUserService;
import com.psoft.match.tcc.util.exception.auth.InvalidCredentialsException;
import com.psoft.match.tcc.util.exception.auth.NoOneLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TCCMatchUserService<TCCMatchUser> tccMatchUserService;

    @Override
    public String login(CredentialsDTO credentialsDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword(), new ArrayList<>());
            authenticationManager.authenticate(authToken);
            String token = jwtUtil.generateToken(credentialsDTO.getUsername());
            return token;
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public TCCMatchUser getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return tccMatchUserService.findByUsernameOpt(username).orElseThrow(NoOneLoggedException::new);
    }

}
