package jp.ac.kagawalab.mynah.web.dto;

import jp.ac.kagawalab.mynah.core.dto.model.FormComponentDto;
import lombok.Value;

import java.util.Calendar;
import java.util.List;

@Value
public class BoardInBoardPage {
    int number;
    String topic;
    Calendar dataPublishedFrom;
    Calendar dataPublishedTo;
    List<FormComponentDto> questions;
    List<RecruitmentInBoardPage> recruitmentList;
}
