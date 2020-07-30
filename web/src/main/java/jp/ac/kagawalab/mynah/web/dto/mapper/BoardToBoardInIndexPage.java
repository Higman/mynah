package jp.ac.kagawalab.mynah.web.dto.mapper;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.web.dto.BoardInIndexPage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Calendar;

public class BoardToBoardInIndexPage implements Converter<Board, BoardInIndexPage> {

    @Override
    public BoardInIndexPage convert(MappingContext<Board, BoardInIndexPage> context) {
        Board board = context.getSource();
        return new BoardInIndexPage(
                board.getId(),
                board.getTopic(),
                (Calendar) board.getDataPublishedFrom().clone(),
                (Calendar) board.getDataPublishedTo().clone()
        );
    }
}
