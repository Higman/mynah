package jp.ac.kagawalab.mynah.web.form.model;

import lombok.Value;

@Value
public class RecruitmentInBoardPage {
    int id;
    String userName;
    int recruiterId;
    int registrationNumberInBoard;  // 登録順位
    boolean isRecruiting;
    String details;
}
