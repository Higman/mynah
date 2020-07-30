package jp.ac.kagawalab.mynah.web.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class FormModelMapper {
    private final DtoModelMapper dtoModelMapper;

    public FormModelMapper(DtoModelMapper dtoModelMapper) {
        this.dtoModelMapper = dtoModelMapper;
    }

    public ModelMapper getModelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.addConverter(new BoardToBoardInIndexPage());
        mm.addConverter(new BoardToBoardInBoardPage(this, dtoModelMapper));
        mm.addConverter(new RecruitmentToRecruitmentInBoardPage());
        mm.addConverter(new UserToUserInIndexPage());
        mm.addConverter(new BoardToRecruitConfirmItems(dtoModelMapper));
        return mm;
    }
}
