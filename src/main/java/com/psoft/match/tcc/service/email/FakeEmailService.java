package com.psoft.match.tcc.service.email;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Admin;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeEmailService implements EmailService {

    @Autowired
    private AdminService adminService;

    @Override
    public void notifyNewTCCToInterestedUsers(Student student, StudyArea studyArea, TCC tcc) {
        String email = String.format("NOVO TCC CADASTRADO NA ÁREA: %s: %s", studyArea.getDescription(), tcc.toEmailFormat());
        student.receiveEmail(email);
    }

    @Override
    public void notifyNewOrientationInterestToUser(TCCMatchUser user, Professor interestedProfessor, TCC tcc) {
        String email = String.format("INTERESSE DE ORIENTAÇÃO NO TCC %s PELO PROFESSOR %s", tcc.getTitle(), interestedProfessor.getEmail());
        user.receiveEmail(email);
    }

    @Override
    public void notifyNewOrientationInterestToUser(TCCMatchUser user, Student interestedStudent, TCC tcc) {
        String email = String.format("INTERESSE DE ORIENTAÇÃO NO TCC %s PELO ALUNO %s", tcc.getTitle(), interestedStudent.getFullName());
        user.receiveEmail(email);
    }

    @Override
    public void notifyApprovedOrientationToAdmin(TCC tcc) {
        List<Admin> admins = adminService.findAllAdmins();
        String email = String.format("ORIENTAÇÃO NO TCC %s ACEITA PELO PROFESSOR %s", tcc.getTitle(), tcc.getAdvisor().getFullName());
        admins.forEach(admin -> admin.receiveEmail(email));
    }
}
