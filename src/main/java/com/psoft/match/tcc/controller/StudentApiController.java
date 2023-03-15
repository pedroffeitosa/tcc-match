package com.psoft.match.tcc.controller;

import com.psoft.match.tcc.dto.OrientationIssueDTO;
import com.psoft.match.tcc.dto.TCCDTO;
import com.psoft.match.tcc.model.tcc.OrientationIssue;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.service.user.StudentService;
import com.psoft.match.tcc.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Student operations")
@RestController
@RequestMapping(Constants.BASE_API_ENDPOINT + "/student")
@CrossOrigin
public class StudentApiController {

    @Autowired
    private StudentService studentService;

	@ApiOperation("Registra interesse em ser orientado em um TCC")
    @PutMapping("/tcc/{tccId}/orientation")
    public ResponseEntity<Void> registerTccOrientationInterest(@PathVariable Long tccId) {
        studentService.declareTCCOrientationInterest(tccId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

	@ApiOperation("Informa problema na orientação de um TCC")
    @PostMapping("/tcc/issue")
    public ResponseEntity<OrientationIssue> registerTccOrientationIssue(@RequestBody OrientationIssueDTO orientationIssueDTO) {
		OrientationIssue orientationIssue = studentService.registerTCCOrientationIssue(orientationIssueDTO);
        return new ResponseEntity<>(orientationIssue, HttpStatus.CREATED);
    }

	@ApiOperation(value = "Adiciona uma área de estudo no estudante logado")
	@PutMapping(value = "/study-area/{idStudyArea}")
    public ResponseEntity<Void> addInterestedStudyArea(@PathVariable Long idStudyArea) {
		studentService.addInterestedStudyArea(idStudyArea);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Lista os professores que tem interesse nas áreas do estudante")
	@GetMapping(value = "/study-area/professor")
    public ResponseEntity<List<Professor>> listProfessorsWithSharedInterests() {
		List<Professor> professors = studentService.getProfessorsWithSharedInterests();
		return new ResponseEntity<>(professors, HttpStatus.OK);
	}

	@ApiOperation(value = "Adiciona uma proposta de tcc no sistema")
	@PostMapping(value = "/tcc")
    public ResponseEntity<TCC> registerTCC(@RequestBody TCCDTO tccDTO) {
		TCC tccProposal = studentService.createStudentTCC(tccDTO);
    	return new ResponseEntity<>(tccProposal, HttpStatus.CREATED);
    }

	@ApiOperation(value = "Lista os tccs cadastrados pelos professores no sistema")
	@GetMapping(value = "/tcc")
    public ResponseEntity<List<TCC>> listStudentsTCCs() {
		List<TCC> studentsTCCs = studentService.getProfessorRegisteredTCCs();
		return new ResponseEntity<>(studentsTCCs, HttpStatus.OK);
	}
}
