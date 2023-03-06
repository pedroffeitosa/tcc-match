package com.psoft.match.tcc.controller;

import com.psoft.match.tcc.dto.ProfessorDTO;
import com.psoft.match.tcc.dto.StudentDTO;
import com.psoft.match.tcc.dto.StudyAreaDTO;
import com.psoft.match.tcc.dto.TCCOrientationDTO;
import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.response.OrientationIssuesSummaryResponse;
import com.psoft.match.tcc.response.TCCSummaryResponse;
import com.psoft.match.tcc.service.user.AdminService;
import com.psoft.match.tcc.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Admin operations")
@RestController
@RequestMapping(Constants.BASE_API_ENDPOINT + "/admin")
@CrossOrigin
public class AdminApiController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/professor")
    @ApiOperation(value = "Criação de um novo professor")
    public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        Professor createdProfessor = adminService.createProfessor(professorDTO);
        return new ResponseEntity<>(createdProfessor, HttpStatus.CREATED);
    }

    @PutMapping(value = "/professor/{id}")
    @ApiOperation(value = "Atualiza as informações de um professor")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO) {
        Professor updatedProfessor = adminService.updateProfessor(id, professorDTO);
        return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
    }

    @DeleteMapping(value = "/professor/{id}")
    @ApiOperation(value = "Deleta um professor")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        adminService.deleteProfessor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/student")
    @ApiOperation(value = "Cria um novo estudante no sistema")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = adminService.createStudent(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    @ApiOperation(value = "Atualiza as informações de um estudante do sistema")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        Student updatedStudent = adminService.updateStudent(id, studentDTO);
        return new ResponseEntity<>(updatedStudent, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/student/{id}")
    @ApiOperation(value = "Remove um estudante do sistema")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        adminService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/study-area")
    @ApiOperation(value = "Criação de uma nova área de estudo")
    public ResponseEntity<StudyArea> createStudyArea(@RequestBody StudyAreaDTO studyAreaDTO) {
        StudyArea createdStudyArea = adminService.createStudyArea(studyAreaDTO);
        return new ResponseEntity<>(createdStudyArea, HttpStatus.CREATED);
    }

    @PutMapping(value = "/study-area/{id}")
    @ApiOperation(value = "Atualização da descrição de uma área de estudo")
    public ResponseEntity<StudyArea> updateStudyArea(@PathVariable Long id, @RequestBody StudyAreaDTO studyAreaDTO) {
        StudyArea updatedStudyArea = adminService.updateStudyArea(id, studyAreaDTO);
        return new ResponseEntity<>(updatedStudyArea, HttpStatus.OK);
    }

    @DeleteMapping(value = "/study-area/{id}")
    @ApiOperation(value = "Deleção de uma área de estudo")
    public ResponseEntity<Void> deleteStudyArea(@PathVariable Long id) {
        adminService.deleteStudyArea(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tcc")
    @ApiOperation(value = "Consulta de TCCs (em andamento ou finalizados) de um período")
    public ResponseEntity<List<TCC>> getTCCs(@RequestParam String status, @RequestParam String term) {
        List<TCC> tccs = adminService.getTCCs(status, term);
        return new ResponseEntity<>(tccs, HttpStatus.OK);
    }

    @PostMapping("/tcc")
    @ApiOperation(value = "Aprova um TCC previamente cadastrado no sistema")
    public ResponseEntity<Void> registerTCC(@RequestBody TCCOrientationDTO tccOrientationDTO) {
        adminService.registerTCC(tccOrientationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/tcc/summary")
    @ApiOperation(value = "Retorna um sumário dos TCCs de um determinado período")
    public ResponseEntity<TCCSummaryResponse> getTCCSummary(@RequestParam String term) {
        TCCSummaryResponse tccSummary = adminService.getTCCSummary(term);
        return new ResponseEntity<>(tccSummary, HttpStatus.OK);
    }

    @PutMapping("/tcc/{tccId}/finalize")
    @ApiOperation(value = "Finaliza um TCC")
    public ResponseEntity<Void> finalizeTCC(@PathVariable Long tccId, @RequestParam String term){
        adminService.finalizeTCC(tccId, term);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/issue/summary")
    @ApiOperation(value = "Retorna um sumário dos problemas de orientação dos TCCs de um determinado periodo")
    public ResponseEntity<OrientationIssuesSummaryResponse> getOrientationIssues(@RequestParam String term) {
        OrientationIssuesSummaryResponse orientationIssuesSummary = adminService.getOrientationIssues(term);
        return new ResponseEntity<>(orientationIssuesSummary, HttpStatus.OK);
    }
}
