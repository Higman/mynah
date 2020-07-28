package jp.ac.kagawalab.mynah.web.form.mapper;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentRepository;
import jp.ac.kagawalab.mynah.web.form.model.RecruitmentInBoardPage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Comparator;
import java.util.stream.Collectors;

public class RecruitmentToRecruitmentInBoardPage implements Converter<Recruitment, RecruitmentInBoardPage> {
    @Override
    public RecruitmentInBoardPage convert(MappingContext<Recruitment, RecruitmentInBoardPage> context) {
        Recruitment recruitment = context.getSource();

        return new RecruitmentInBoardPage(
                recruitment.getId(),
                recruitment.getRecruiter().getUserName(),
                recruitment.getRecruiter().getId(),
                recruitment.getPublisher()
                        .getRecruitmentList().stream()
                        .sorted(Comparator.comparing(Recruitment::getCreatedAt))
                        .collect(Collectors.toList()).indexOf(recruitment) + 1,
                recruitment.isRecruiting(),
                recruitment.getDetail()
        );
    }
}
