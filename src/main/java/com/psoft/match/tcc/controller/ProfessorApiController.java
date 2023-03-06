package com.psoft.match.tcc.controller;

import com.psoft.match.tcc.dto.NewQuotaDTO;
import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.response.SolicitationResponse;
import com.psoft.match.tcc.response.TCCResponse;
import com.psoft.match.tcc.service.user.ProfessorService;
import com.psoft.match.tcc.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Professor operations")
@RestController
@RequestMapping(Constants.BASE_API_ENDPOINT + "/professor")
@CrossOrigin
public class ProfessorApiController {

    @Autowired
    private ProfessorService professorService;

    @ApiOperation("Registra interesse em uma proposta de TCC")
    @PutMapping("/tcc/{tccId}/interest")
    public ResponseEntity<Void> declareOrientationInterest(@PathVariable Long tccId) {
        professorService.declareOrientationInterest(tccId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Adiciona um novo TCC ao sistema")
    @PostMapping("/tcc")
    public ResponseEntity<TCC> createTCC(@RequestBody TCCDTO tccDTO) {
        TCC tcc = professorService.createProfessorTCC(tccDTO);
        return new ResponseEntity<>(tcc, HttpStatus.CREATED);
    }

    @ApiOperation("Aprova a solicitação de orientação de um aluno em um TCC")
    @PutMapping("/tcc/{tccId}/student/{studentId}/interest/approve")
    public ResponseEntity<String> approveOrientation(@PathVariable Long tccId, @PathVariable Long studentId) {
        professorService.approveOrientationInterest(tccId, studentId);
        return new ResponseEntity<>(String.format(Constants.ORIENTATION_APPROVED_RESPONSE, tccId), HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Recusa a solicitação de orientação de um aluno em um TCC")
    @PutMapping("/tcc/{tccId}/student/{studentId}/interest/refuse")
    public ResponseEntity<String> refuseOrientation(@PathVariable Long tccId, @PathVariable Long studentId) {
        professorService.refuseOrientationInterest(tccId, studentId);
        return new ResponseEntity<>(String.format(Constants.ORIENTATION_REFUSED_RESPONSE, tccId), HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Adiciona uma área de estudo nos interesses do professor")
    @PutMapping("/study-area/{studyAreaId}")
    public ResponseEntity<Void> addInterestedStudyArea(@PathVariable Long studyAreaId) {
        professorService.addInterestedStudyArea(studyAreaId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza a quota do professor")
    @PutMapping("/quota")
    public ResponseEntity<Void> updateQuota(@RequestBody NewQuotaDTO newQuotaDTO) {
        professorService.updateQuota(newQuotaDTO.getNewQuota());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Lista os temas de TCC cadastrados pelo professor")
    @GetMapping("/tcc")
    public ResponseEntity<Collection<TCCResponse>> getRegisteredTCCs() {
        Collection<TCC> professorTCCs = professorService.getRegisteredTCCs();
        Collection<TCCResponse> response = professorTCCs.stream().map(TCCResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Lista os temas de TCC cadastrados pelos alunos")
    @GetMapping("/tcc/student")
    public ResponseEntity<Collection<TCCResponse>> getStudentsTCCs() {
        Collection<TCC> studentsTCCs = professorService.getStudentsTCCs();
        Collection<TCCResponse> response = studentsTCCs.stream().map(TCCResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Lista as solicitações de orientação em um TCC cadastradas pelos alunos")
    @GetMapping("/tcc/solicitation")
    public ResponseEntity<Collection<SolicitationResponse>> getStudentsSolicitations() {
        Collection<TCC> professorTCCs = professorService.getOrientationSolicitations();
        Collection<SolicitationResponse> response = professorTCCs.stream().map(SolicitationResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ApiOperation("Lista os temas de TCC orientados pelo professor")
    @GetMapping("/tcc/orientation")
    public ResponseEntity<Collection<TCCResponse>> getOngoingGuidelines() {
        Collection<TCC> professorTCCs = professorService.getOngoingOrientations();
        Collection<TCCResponse> response = professorTCCs.stream().map(TCCResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Registra algum problema na orientação de um TCC orientado pelo professor")
    @PostMapping("/tcc/{tccId}/issue")
    public ResponseEntity<OrientationIssue> registerOrientationIssue(@PathVariable Long tccId, @RequestBody OrientationIssueDTO orientationIssueDTO) {
        OrientationIssue orientationIssue = professorService.registerTCCOrientationIssue(tccId, orientationIssueDTO);
        return new ResponseEntity<>(orientationIssue, HttpStatus.CREATED);
    }
}
