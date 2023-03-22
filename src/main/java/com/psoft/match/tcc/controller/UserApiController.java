package com.psoft.match.tcc.controller;

import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.service.auth.AuthService;
import com.psoft.match.tcc.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Api(tags = "User operations")
@RestController
@RequestMapping(Constants.BASE_API_ENDPOINT + "/user")
@CrossOrigin
public class UserApiController {

    @Autowired
    private AuthService authService;

    @GetMapping("/inbox")
    @ApiOperation(value = "Listagem da caixa de entrada do usuário logado")
    public ResponseEntity<Collection<String>> getEmailsList() {
        return new ResponseEntity<>(authService.getLoggedUser().getEmails(), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Informações sobre o usuário logado")
    public ResponseEntity<TCCMatchUser> getMe() {
        return new ResponseEntity<>(authService.getLoggedUser(), HttpStatus.OK);
    }
}
