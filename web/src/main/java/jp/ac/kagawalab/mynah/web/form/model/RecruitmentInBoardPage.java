package jp.ac.kagawalab.mynah.web.form.model;

import lombok.Value;

@Value
public class RecruitmentInBoardPage {
    int id;
    String userName;
    boolean isRecruiting;
    String details;
}
