package jp.ac.kagawalab.mynah.web.form.model;

import lombok.Value;

@Value
public class RecruitmentInBoardPage {
    int id;
    String userName;
    int recruiterId;
    boolean isRecruiting;
    String details;
}
