package com.psoft.match.tcc.config;

import com.psoft.match.tcc.service.db.DBService;
import com.psoft.match.tcc.service.email.EmailService;

public interface SystemConfig {

    EmailService emailService();

    DBService dbService();

}
