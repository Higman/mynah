package jp.ac.kagawalab.mynah.web.form.model;

import lombok.Value;

import java.util.Calendar;
import java.util.List;

@Value
public class BoardInGlobalPage {
    String number;
    String topic;
    Calendar dataPublishedFrom;
    Calendar dataPublishedTo;
    List<RecruitmentInBoardPage> recruitmentList;
}
