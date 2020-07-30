package jp.ac.kagawalab.mynah.web.dto.mapper;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.entity.RecruitmentDetail;
import jp.ac.kagawalab.mynah.web.dto.RecruitmentInBoardPage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecruitmentToRecruitmentInBoardPage implements Converter<Recruitment, RecruitmentInBoardPage> {
    @Override
    public RecruitmentInBoardPage convert(MappingContext<Recruitment, RecruitmentInBoardPage> context) {
        Recruitment recruitment = context.getSource();
        Board publisher = recruitment.getPublisher();
        List<String> details = recruitment.getRecruitmentDetails()
                .stream()
                .sorted(Comparator.comparingInt(o -> o.getFormComponent()
                        .getOrderNumber()))
                .map(RecruitmentDetail::getValue).collect(Collectors.toList());
        // もし募集登録時情報が少ない場合、空値で補完
        // 多すぎる場合は、末尾から削る
        int sampleDiff = publisher.getFormComponents().size() - details.size();
        if (sampleDiff > 0) {
            for (int i = 0; i < sampleDiff; i++) {
                details.add(" ");
            }
        } else if (sampleDiff < 0) {
            details = details.subList(publisher.getFormComponents().size(), details.size());
        }

        return new RecruitmentInBoardPage(
                recruitment.getId(),
                recruitment.getRecruiter().getUserName(),
                recruitment.getRecruiter().getId(),
                recruitment.getPublisher()
                        .getRecruitmentList().stream()
                        .sorted(Comparator.comparing(Recruitment::getCreatedAt))
                        .collect(Collectors.toList()).indexOf(recruitment) + 1,
                recruitment.isRecruiting(),
                details
        );
    }

}
