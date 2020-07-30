package jp.ac.kagawalab.mynah.web.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.FormComponentDto;
import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.web.dto.RecruitConfirmItems;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardToRecruitConfirmItems implements Converter<Board, RecruitConfirmItems> {
    private final DtoModelMapper modelMapper;

    public BoardToRecruitConfirmItems(DtoModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RecruitConfirmItems convert(MappingContext<Board, RecruitConfirmItems> context) {
        Board board = context.getSource();
        List<FormComponentDto> formComponents = board.getFormComponents().stream()
                .map(f -> modelMapper.getModelMapper().map(f, FormComponentDto.class)).collect(Collectors.toList());
        return new RecruitConfirmItems(board.getId(), board.getTopic(), formComponents);
    }
}
