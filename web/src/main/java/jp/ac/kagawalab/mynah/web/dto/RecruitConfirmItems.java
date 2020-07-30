package jp.ac.kagawalab.mynah.web.dto;

import jp.ac.kagawalab.mynah.core.dto.model.FormComponentDto;
import lombok.Value;

import java.util.List;

@Value
public class RecruitConfirmItems {
    int number;
    String topic;
    List<FormComponentDto> formComponents;
}
