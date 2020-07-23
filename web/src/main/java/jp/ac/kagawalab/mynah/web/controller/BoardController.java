package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.entity.User;
import jp.ac.kagawalab.mynah.core.oauth2.model.AbstractMynahUserDetails;
import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentRepository;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.web.form.mapper.FormModelMapper;
import jp.ac.kagawalab.mynah.web.form.model.BoardInBoardPage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;
    private final FormModelMapper modelMapper;

    public BoardController(BoardRepository boardRepository, RecruitmentRepository recruitmentRepository, UserRepository userRepository, FormModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/board/show")
    public String showBoardDetail(@AuthenticationPrincipal AbstractMynahUserDetails userDetails, @RequestParam(name = "id") int id, Model model) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) throw new IllegalRequestException("Illegal board id requested: id = " + id);
        BoardInBoardPage form = modelMapper.getModelMapper().map(board.get(), BoardInBoardPage.class);
        model.addAttribute("board", form);
        model.addAttribute("userId", userDetails.getId());
        return "board";
    }

    @GetMapping("/board/confirm")
    public String confirmRequest(@RequestParam(name = "id") int id, Model model) {

        return "forward:/board/show";
    }

    static class IllegalRequestException extends RuntimeException {
        private static final long serialVersionUID = 2694124247004650886L;

        public IllegalRequestException() {
            super();
        }

        public IllegalRequestException(String message) {
            super(message);
        }

        public IllegalRequestException(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalRequestException(Throwable cause) {
            super(cause);
        }

        protected IllegalRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
