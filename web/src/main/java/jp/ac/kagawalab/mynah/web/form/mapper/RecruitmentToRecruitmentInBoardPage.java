package jp.ac.kagawalab.mynah.web.form.mapper;

import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.web.form.model.RecruitmentInBoardPage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class RecruitmentToRecruitmentInBoardPage implements Converter<Recruitment, RecruitmentInBoardPage> {
    @Override
    public RecruitmentInBoardPage convert(MappingContext<Recruitment, RecruitmentInBoardPage> context) {
        Recruitment recruitment = context.getSource();

        return new RecruitmentInBoardPage(
                recruitment.getId(), recruitment.getRecruiter().getUserName(), recruitment.getRecruiter().getId(), recruitment.isRecruiting(), recruitment.getDetail()
        );
    }
}
