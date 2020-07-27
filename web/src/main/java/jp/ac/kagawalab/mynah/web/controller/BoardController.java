package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.entity.Recruitment;
import jp.ac.kagawalab.mynah.core.entity.User;
import jp.ac.kagawalab.mynah.core.oauth2.model.AbstractMynahUserDetails;
import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentRepository;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.web.form.mapper.FormModelMapper;
import jp.ac.kagawalab.mynah.web.form.model.BoardInGlobalPage;
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
    public String showBoardDetail(@AuthenticationPrincipal AbstractMynahUserDetails userDetails, @RequestParam(name = "board_id") int boardId, Model model) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) throw new IllegalRequestException("Illegal board id requested: id = " + boardId);
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isEmpty()) throw new IllegalRequestException("Illegal user id requested: id = " + userDetails.getId());
        List<Recruitment> recruitment = recruitmentRepository.findAllByRecruiter(user.get());
        model.addAttribute("test", "test");
        if (recruitment.isEmpty() && user.get().getRole().equals(RoleDto.ROLE_USER)) return "forward:/board/confirm";
        BoardInGlobalPage form = modelMapper.getModelMapper().map(board.get(), BoardInGlobalPage.class);
        model.addAttribute("board", form);
        model.addAttribute("userId", userDetails.getId());
        return "board";
    }

    @GetMapping("/board/confirm")
    public String confirmRequest(@AuthenticationPrincipal AbstractMynahUserDetails userDetails, @RequestParam(name = "board_id") int boardId, Model model) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) throw new IllegalRequestException("Illegal board id requested: id = " + boardId);
        model.addAttribute("board", board);
        model.addAttribute("userId", userDetails.getId());
        return "confirm";
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
