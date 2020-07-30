package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.dto.mapper.DtoModelMapper;
import jp.ac.kagawalab.mynah.core.dto.model.FormTypeDto;
import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import jp.ac.kagawalab.mynah.core.entity.*;
import jp.ac.kagawalab.mynah.core.oauth2.model.AbstractMynahUserDetails;
import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentDetailRepository;
import jp.ac.kagawalab.mynah.core.repository.RecruitmentRepository;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.web.dto.BoardInBoardPage;
import jp.ac.kagawalab.mynah.web.dto.RecruitConfirmItems;
import jp.ac.kagawalab.mynah.web.dto.mapper.FormModelMapper;
import jp.ac.kagawalab.mynah.web.exception.IllegalRequestException;
import jp.ac.kagawalab.mynah.web.form.ConfirmForm;
import jp.ac.kagawalab.mynah.web.util.dev.FormUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/board/")
@SessionAttributes(types = ConfirmForm.class)
public class BoardController {
    private final BoardRepository boardRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentDetailRepository recruitmentDetailRepository;
    private final UserRepository userRepository;
    private final FormModelMapper formModelMapper;
    private final DtoModelMapper dtoModelMapper;

    public BoardController(BoardRepository boardRepository, RecruitmentRepository recruitmentRepository, RecruitmentDetailRepository recruitmentDetailRepository, UserRepository userRepository, FormModelMapper formModelMapper, DtoModelMapper dtoModelMapper) {
        this.boardRepository = boardRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.recruitmentDetailRepository = recruitmentDetailRepository;
        this.userRepository = userRepository;
        this.formModelMapper = formModelMapper;
        this.dtoModelMapper = dtoModelMapper;
    }

    @GetMapping("/show")
    public String showBoardDetail(@AuthenticationPrincipal AbstractMynahUserDetails userDetails,
                                  @RequestParam(name = "board_id") int boardId, Model model,
                                  @ModelAttribute("confirmForm") ConfirmForm sessionForm,
                                  SessionStatus session) {
        Board board = getBoard(boardId);
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isEmpty()) throw new IllegalRequestException("Illegal user id requested: id = " + userDetails.getId());
        List<Recruitment> recruitment = recruitmentRepository.findAllByUserIdAndBoardId(userDetails.getId(), boardId);
        if (recruitment.isEmpty() && userDetails.getRole().equals(RoleDto.ROLE_USER)) {
            sessionForm.setBoardId(boardId);
            return "forward:/board/confirm";
        }
        BoardInBoardPage boardData = formModelMapper.getModelMapper().map(board, BoardInBoardPage.class);
        model.addAttribute("board", boardData);
        model.addAttribute("userId", userDetails.getId());
        session.setComplete();
        return "board";
    }

    private Board getBoard(int boardId) {
        Optional<Board> boardData = boardRepository.findById(boardId);
        if (boardData.isEmpty()) throw new IllegalRequestException("Illegal board id requested: id = " + boardId);
        return boardData.get();
    }

    @GetMapping("/confirm")
    public String confirmRequest(@AuthenticationPrincipal AbstractMynahUserDetails userDetails,
                                 @ModelAttribute("confirmForm") ConfirmForm sessionForm, Model model) {
        int boardId = sessionForm.getBoardId();
        Board boardData = getBoard(boardId);
        RecruitConfirmItems items = formModelMapper.getModelMapper().map(boardData, RecruitConfirmItems.class);
        model.addAttribute("confirmItems", items);
        model.addAttribute("userId", userDetails.getId());
        return "confirm";
    }

    @PostMapping("/recruit")
    public String recruit(@AuthenticationPrincipal AbstractMynahUserDetails userDetails, @ModelAttribute("confirmForm") ConfirmForm sessionForm, RedirectAttributes redirectAttributes, @RequestBody MultiValueMap<String, String> formData) {
        int boardId = sessionForm.getBoardId();
        Board boardData = getBoard(boardId);
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isEmpty()) throw new IllegalRequestException("Illegal user id requested: id = " + userDetails.getId());
        // 募集エンティティ構築
        Recruitment recruitment = structRecruitment(formData, boardData, user);
        recruitmentRepository.save(recruitment);
        redirectAttributes.addAttribute("board_id", boardId);
        return "redirect:/board/show";
    }

    private Recruitment structRecruitment(@RequestBody MultiValueMap<String, String> formData, Board boardData, Optional<User> user) {
        Recruitment recruitment = new Recruitment()
                .setPublisher(boardData)
                .setRecruiting(true)
                .setRecruiter(user.get());
        List<RecruitmentDetail> recruitmentDetails = new ArrayList<>();
        for (FormComponent formComponent : boardData.getFormComponents()) {
            String value = modifyConfirmValue(Optional.ofNullable(formData.getFirst(formComponent.getParamName())), formComponent);
            recruitmentDetails.add(new RecruitmentDetail().setOwner(recruitment)
                                           .setValue(value)
                                           .setFormComponent(formComponent));
        }
        recruitment.setRecruitmentDetails(recruitmentDetails);
        return recruitment;
    }

    private String modifyConfirmValue(Optional<String> inputedValue, FormComponent formComponent) {
        String value = inputedValue.orElse("");
        // 検証と修正
        switch (dtoModelMapper.getModelMapper().map(formComponent.getType(), FormTypeDto.class)) {
            case PULL_DOWN_MENU: {
                value = FormUtil.pullDownItemsSplit(formComponent.getValue())[Integer.parseInt(value)];
                break;
            }
            case CHECK_BOX: {
                if (value.isBlank()) {
                    value = "false";
                }
                break;
            }
            case TEXT:
                break;
        }
        return value;
    }

    @GetMapping("/add")
    public String addBoard(@AuthenticationPrincipal AbstractMynahUserDetails userDetails, Model model) {
        return "add_board";
    }

    @ModelAttribute("confirmForm")
    public ConfirmForm setupForm() {
        return new ConfirmForm();
    }
}
