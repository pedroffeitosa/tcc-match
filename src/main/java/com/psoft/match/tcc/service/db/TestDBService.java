package com.psoft.match.tcc.service.db;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.tcc.TCCStatus;
import com.psoft.match.tcc.model.user.Admin;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.repository.StudyAreaRepository;
import com.psoft.match.tcc.repository.tcc.TCCRepository;
import com.psoft.match.tcc.repository.user.ProfessorRepository;
import com.psoft.match.tcc.repository.user.StudentRepository;
import com.psoft.match.tcc.repository.user.TCCMatchUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TestDBService implements DBService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TCCMatchUserRepository TCCMatchUserRepository;

    @Autowired
    private StudyAreaRepository studyAreaRepository;

    @Autowired
    private TCCRepository tccRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void seed() {
        Admin admin = new Admin("admin", "admin@admim.com", "admin", passwordEncoder.encode("admin"));

        Professor professor1 = new Professor("Elmar Melcher", "elmardfsaidfhaisdfhasidfhfhasiufhsduifhasdf@gmail.com", "elmar", passwordEncoder.encode("1234"), Arrays.asList("LSD", "SPLAB"), 10);
        Professor professor2 = new Professor("Francisco Vilar", "asdiasiodjaodijasoidjasiod@gmail.com", "fubica", passwordEncoder.encode("1234"), Arrays.asList("LSD"), 2);
        Professor professor3 = new Professor("Joao Arthur", "ajskdhakjsdhajkdhaksjdhajkhdkasdhjk@gmail.com", "joao_arthur", passwordEncoder.encode("1234"), new ArrayList<>(), 0);

        Student student1 = new Student("Jonatas Ferreira de Lima", "119210036", "jonatas.lima@ccc.ufcg.edu.br", "2024.2", "jonatas", passwordEncoder.encode("1234"));
        Student student2 = new Student("Bernard Dantas Odon", "123", "bernard.odon@ccc.ufcg.edu.br", "2024.2", "bodon", passwordEncoder.encode("1234"));
        Student student3 = new Student("Marcos Cosson", "123", "marcos.cosson@ccc.ufcg.edu.br", "2023.1", "marcos", passwordEncoder.encode("1234"));
        Student student4 = new Student("Kleber Sobrinho", "123", "kleber.sobrinho@ccc.ufcg.edu.br", "2023.1", "marcos", passwordEncoder.encode("1234"));
        Student student5 = new Student("Joao Vitor Moura", "123", "joao.vitor.figueiredo@ccc.ufcg.edu.br", "2023.1", "marcos", passwordEncoder.encode("1234"));

        List<TCCMatchUser> users = Arrays.asList(admin, student1, student2, student3, student4, student5, professor1, professor2, professor3);

        List<Student> students = Arrays.asList(student1, student2, student3);
        List<Professor> professors = Arrays.asList(professor1, professor2, professor3);

        TCCMatchUserRepository.saveAll(users);
        studentRepository.saveAll(students);
        professorRepository.saveAll(professors);

        StudyArea studyArea1 = new StudyArea("Ciencia de Dados");
        StudyArea studyArea2 = new StudyArea("Inteligencia Artifical");
        StudyArea studyArea3 = new StudyArea("Engenharia de software");
        StudyArea studyArea4 = new StudyArea("Sistemas Operacionais");

        List<StudyArea> studyAreas = Arrays.asList(studyArea1, studyArea2, studyArea3, studyArea4);
        studyAreaRepository.saveAll(studyAreas);

        studyArea4.addInterestedStudent(student3);
        student3.addInterestedArea(studyArea4);

        studyArea2.addInterestedStudent(student4);
        student4.addInterestedArea(studyArea2);

        studyArea2.addInterestedStudent(student4);
        student5.addInterestedArea(studyArea2);

        studyArea1.addInterestedProfessor(professor1);
        studyArea2.addInterestedProfessor(professor1);
        studyArea3.addInterestedProfessor(professor1);
        professor1.addInterestedStudyArea(studyArea1);
        professor1.addInterestedStudyArea(studyArea2);
        professor1.addInterestedStudyArea(studyArea3);

        studyArea1.addInterestedProfessor(professor2);
        studyArea4.addInterestedProfessor(professor2);
        professor2.addInterestedStudyArea(studyArea4);
        professor2.addInterestedStudyArea(studyArea1);

        studyArea1.addInterestedStudent(student1);
        studyArea2.addInterestedStudent(student1);
        student1.addInterestedArea(studyArea1);
        student1.addInterestedArea(studyArea2);

        studyArea3.addInterestedStudent(student2);
        studyArea4.addInterestedStudent(student2);
        student2.addInterestedArea(studyArea3);
        student2.addInterestedArea(studyArea4);

        professorRepository.saveAll(professors);
        studentRepository.saveAll(students);
        studyAreaRepository.saveAll(studyAreas);

        TCC tcc1 = new TCC(professor1, "Proposta 1", "descricao da proposta 1", TCCStatus.APPROVED);
        TCC tcc2 = new TCC(professor2, "Proposta 2", "descricao da proposta 2", TCCStatus.APPROVED);
        TCC tcc3 = new TCC(professor3, "Proposta 3", "descricao da proposta 3", TCCStatus.APPROVED);
        TCC tcc4 = new TCC(student2, "Proposta 4", "descricao da proposta 4", TCCStatus.PENDING_APPROVAL);
        TCC tcc5 = new TCC(student3, "Proposta 5", "descricao da proposta 5", TCCStatus.PENDING_APPROVAL);

        List<TCC> tccs = Arrays.asList(tcc1, tcc2, tcc3, tcc4, tcc5);

        tcc1.addStudyArea(studyArea1);
        studyArea1.addTCC(tcc1);

        tcc1.addStudyArea(studyArea2);
        studyArea2.addTCC(tcc1);

        tcc2.addStudyArea(studyArea1);
        studyArea1.addTCC(tcc2);

        tcc3.addStudyArea(studyArea3);
        studyArea3.addTCC(tcc3);

        tcc4.addStudyArea(studyArea4);
        studyArea4.addTCC(tcc4);

        studyAreaRepository.saveAll(studyAreas);
        tccRepository.saveAll(tccs);

        professor1.registerTCC(tcc1);
        professor2.registerTCC(tcc2);
        professor3.registerTCC(tcc3);

        professorRepository.saveAll(professors);

        professor1.addOrientationInterest(tcc4);
        tcc4.addInterestedProfessor(professor1);

        professor2.addOrientationInterest(tcc4);
        tcc4.addInterestedProfessor(professor2);

        professor3.addOrientationInterest(tcc4);
        tcc4.addInterestedProfessor(professor3);

        student2.addOrientationInterest(tcc3);
        tcc3.addInterestedStudent(student2);

        student2.addOrientationInterest(tcc4);
        tcc4.addInterestedStudent(student2);

        student3.addOrientationInterest(tcc4);
        tcc4.addInterestedStudent(student3);

        tccRepository.saveAll(tccs);

        student1.setTcc(tcc1);
        tcc1.setAdvisedStudent(student1);
        tcc1.setTerm("2020.1");
        tcc1.setTccStatus(TCCStatus.ON_GOING);
        tcc1.setAdvisor(professor1);

        tccRepository.saveAll(tccs);
    }
}
