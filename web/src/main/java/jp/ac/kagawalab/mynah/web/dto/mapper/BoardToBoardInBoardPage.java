package jp.ac.kagawalab.mynah.web.dto.mapper;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.FormComponentDto;
import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.entity.FormComponent;
import jp.ac.kagawalab.mynah.web.dto.BoardInBoardPage;
import jp.ac.kagawalab.mynah.web.dto.RecruitmentInBoardPage;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BoardToBoardInBoardPage implements Converter<Board, BoardInBoardPage> {

    private final FormModelMapper formModelMapper;
    private final DtoModelMapper dtoModelMapper;

    public BoardToBoardInBoardPage(FormModelMapper formModelMapper, DtoModelMapper dtoModelMapper) {
        this.formModelMapper = formModelMapper;
        this.dtoModelMapper = dtoModelMapper;
    }

    @Override
    public BoardInBoardPage convert(MappingContext<Board, BoardInBoardPage> context) {
        Board board = context.getSource();
        ModelMapper m = formModelMapper.getModelMapper();
        List<RecruitmentInBoardPage> recruitmentList = board.getRecruitmentList()
                .stream()
                .map(r -> m.map(r, RecruitmentInBoardPage.class)).collect(Collectors.toList());
        List<FormComponentDto> questions = board.getFormComponents()
                .stream()
                .sorted(Comparator.comparingInt(FormComponent::getOrderNumber))
                .map(fc -> dtoModelMapper.getModelMapper().map(fc, FormComponentDto.class))
                .collect(Collectors.toList());
        return new BoardInBoardPage(
                board.getId(),
                board.getTopic(),
                (Calendar) board.getDataPublishedFrom().clone(),
                (Calendar) board.getDataPublishedTo().clone(),
                questions,
                recruitmentList
        );
    }
}
