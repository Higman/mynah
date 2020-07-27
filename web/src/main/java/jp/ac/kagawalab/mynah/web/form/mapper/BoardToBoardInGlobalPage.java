package jp.ac.kagawalab.mynah.web.form.mapper;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.web.form.model.BoardInGlobalPage;
import jp.ac.kagawalab.mynah.web.form.model.RecruitmentInBoardPage;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class BoardToBoardInGlobalPage implements Converter<Board, BoardInGlobalPage> {

    private final FormModelMapper modelMapper;

    public BoardToBoardInGlobalPage(FormModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BoardInGlobalPage convert(MappingContext<Board, BoardInGlobalPage> context) {
        Board board = context.getSource();
        ModelMapper m = modelMapper.getModelMapper();
        List<RecruitmentInBoardPage> recruitmentList = board.getRecruitmentList()
                .stream()
                .map(r -> m.map(r, RecruitmentInBoardPage.class)).collect(Collectors.toList());

        return new BoardInGlobalPage(
                String.format("%02d", board.getId()),
                board.getTopic(),
                (Calendar) board.getDataPublishedFrom().clone(),
                (Calendar) board.getDataPublishedTo().clone(),
                recruitmentList
        );
    }
}
