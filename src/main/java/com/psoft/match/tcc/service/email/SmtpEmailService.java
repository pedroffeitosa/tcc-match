package com.psoft.match.tcc.service.email;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Admin;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;
import com.psoft.match.tcc.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SmtpEmailService implements EmailService {

    @Autowired
    private AdminService adminService;

    @Value("${email.sender}")
    private String SENDER;

    @Autowired
    private MailSender mailSender;

    @Override
    public void notifyNewTCCToInterestedUsers(Student student, StudyArea studyArea, TCC tcc) {
        String subject = String.format("Novo TCC cadastrado na área: %s", studyArea.getDescription().toUpperCase());
        SimpleMailMessage mailMessage = this.buildMailMessage(student.getEmail(), subject, tcc.toEmailFormat());
        mailSender.send(mailMessage);
    }

    @Override
    public void notifyNewOrientationInterestToUser(TCCMatchUser user, Professor interestedProfessor, TCC tcc) {
        String subject = String.format("INTERESSE DE ORIENTAÇÃO NO TCC %s PELO PROFESSOR %s", tcc.getTitle(), interestedProfessor.getEmail());
        SimpleMailMessage mailMessage = this.buildMailMessage(user.getEmail(), subject, tcc.toString());
        mailSender.send(mailMessage);
    }

    @Override
    public void notifyNewOrientationInterestToUser(TCCMatchUser user, Student interestedStudent, TCC tcc) {
        String subject = String.format("INTERESSE DE ORIENTAÇÃO NO TCC %s PELO ALUNO %s", tcc.getTitle(), interestedStudent.getFullName());
        SimpleMailMessage mailMessage = this.buildMailMessage(user.getEmail(), subject, tcc.toEmailFormat());
        mailSender.send(mailMessage);
    }

    @Override
    public void notifyApprovedOrientationToAdmin(TCC tcc) {
        String subject = String.format("ORIENTAÇÃO NO TCC %s ACEITA PELO PROFESSOR %s", tcc.getTitle(), tcc.getAdvisor().getFullName());
        List<Admin> admins = adminService.findAllAdmins();
        admins.forEach(admin -> {
            SimpleMailMessage mailMessage = this.buildMailMessage(admin.getEmail(), subject, tcc.toEmailFormat());
            mailSender.send(mailMessage);
        });
    }

    private SimpleMailMessage buildMailMessage(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(SENDER);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setSentDate(new Date());
        return mailMessage;
    }
}
