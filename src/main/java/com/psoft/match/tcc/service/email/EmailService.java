package com.psoft.match.tcc.service.email;

import com.psoft.match.tcc.model.StudyArea;
import com.psoft.match.tcc.model.tcc.TCC;
import com.psoft.match.tcc.model.user.Professor;
import com.psoft.match.tcc.model.user.Student;
import com.psoft.match.tcc.model.user.TCCMatchUser;

public interface EmailService {

    void notifyNewTCCToInterestedUsers(Student student, StudyArea studyArea, TCC tcc);

    void notifyNewOrientationInterestToUser(TCCMatchUser user, Professor interestedProfessor, TCC tcc);

    void notifyNewOrientationInterestToUser(TCCMatchUser user, Student interestedStudent, TCC tcc);

    void notifyApprovedOrientationToAdmin(TCC tcc);
}
