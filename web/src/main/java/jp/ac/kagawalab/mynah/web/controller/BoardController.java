package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentRepository;
import jp.ac.kagawalab.mynah.web.form.mapper.FormModelMapper;
import jp.ac.kagawalab.mynah.web.form.model.BoardInBoardPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final FormModelMapper modelMapper;

    public BoardController(BoardRepository boardRepository, RecruitmentRepository recruitmentRepository, FormModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/board/show")
    public String showBoardDetail(@RequestParam(name = "id") int id, Model model) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) throw new IllegalBoardIdException();
        BoardInBoardPage form = modelMapper.getModelMapper().map(board.get(), BoardInBoardPage.class);
        model.addAttribute("board", form);
        return "board";
    }

    static class IllegalBoardIdException extends RuntimeException {
        private static final long serialVersionUID = 2694124247004650886L;

        public IllegalBoardIdException() {
            super();
        }

        public IllegalBoardIdException(String message) {
            super(message);
        }

        public IllegalBoardIdException(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalBoardIdException(Throwable cause) {
            super(cause);
        }

        protected IllegalBoardIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
