package jp.ac.kagawalab.mynah.web.dto;

import lombok.Value;

import java.util.List;

@Value
public class RecruitmentInBoardPage {
    int id;
    String userName;
    int recruiterId;
    int registrationNumberInBoard;  // 登録順位
    boolean isRecruiting;
    List<String> details;
}
