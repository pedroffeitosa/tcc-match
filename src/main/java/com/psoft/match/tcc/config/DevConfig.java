package com.psoft.match.tcc.config;

import com.psoft.match.tcc.service.db.DBService;
import com.psoft.match.tcc.service.db.TestDBService;
import com.psoft.match.tcc.service.email.EmailService;
import com.psoft.match.tcc.service.email.FakeEmailService;
import com.psoft.match.tcc.service.email.SmtpEmailService;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Profile("dev")
public class DevConfig implements SystemConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {};
    }

    @Bean
    public boolean seedDB() {
        dbService().seed();
        return true;
    }

    @Bean
    @Override
    public EmailService emailService() {
        return new FakeEmailService();
    }

    @Bean
    @Override
    public DBService dbService() {
        return new TestDBService();
    }

    @Bean
    public MailSenderAutoConfiguration mailSenderAutoConfiguration() {
        return new MailSenderAutoConfiguration();
    }

    @Bean
    public MailSender mailSender() {
        return new JavaMailSenderImpl();
    }

}
