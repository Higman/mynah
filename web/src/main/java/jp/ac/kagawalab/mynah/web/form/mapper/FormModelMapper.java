package jp.ac.kagawalab.mynah.web.form.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class FormModelMapper {
    public ModelMapper getModelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.addConverter(new BoardToBoardInIndexPage());
        mm.addConverter(new BoardToBoardInBoardPage(this));
        mm.addConverter(new RecruitmentToRecruitmentInBoardPage());
        mm.addConverter(new UserToUserInIndexPage());
        return mm;
    }
}
