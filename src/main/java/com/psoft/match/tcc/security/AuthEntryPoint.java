package com.psoft.match.tcc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psoft.match.tcc.util.exception.CustomErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class AuthEntryPoint implements AuthenticationEntryPoint {

    private static final String UNAUTHORIZED_MSG = "User is not authenticated.";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CustomErrorType error = JWTUtil.buildAuthError(UNAUTHORIZED_MSG, HttpStatus.UNAUTHORIZED.value(), response);

        OutputStream out = response.getOutputStream();
        out.write(new ObjectMapper().writeValueAsBytes(error));
        out.flush();
        out.close();
    }
}
