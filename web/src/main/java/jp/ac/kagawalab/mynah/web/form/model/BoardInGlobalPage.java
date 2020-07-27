package jp.ac.kagawalab.mynah.web.form.model;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.web.form.mapper.BoardToBoardInIndexPage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
