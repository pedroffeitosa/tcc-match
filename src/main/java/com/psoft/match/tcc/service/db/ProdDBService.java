package com.psoft.match.tcc.service.db;

import com.psoft.match.tcc.model.user.Admin;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.repository.user.ProfessorRepository;
import com.psoft.match.tcc.repository.user.StudentRepository;
import com.psoft.match.tcc.repository.user.TCCMatchUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProdDBService implements DBService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TCCMatchUserRepository TCCMatchUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void seed() {
        Admin admin = new Admin("admin", "admin", "admin", passwordEncoder.encode("admin"));
        System.out.println(admin.getPassword());
        Professor professor = new Professor("professor", "professor@gmail.com", "prof", passwordEncoder.encode("123"), Arrays.asList("SPLab"), 10);
        Student student = new Student("student", "119210036", "ferreiradelimajonatas@gmail.com", "2024.2", "stud", passwordEncoder.encode("123"));

        List<TCCMatchUser> users = Arrays.asList(admin, professor, student);

        TCCMatchUserRepository.saveAll(users);
        professorRepository.save(professor);
        studentRepository.save(student);
    }
}
