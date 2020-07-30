package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.entity.Board;
import jp.ac.kagawalab.mynah.core.oauth2.security.MynahOidcUser;
import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.web.dto.mapper.FormModelMapper;
import jp.ac.kagawalab.mynah.web.dto.BoardInIndexPage;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    private final BoardRepository boardRepository;
    private final FormModelMapper modelMapper;
    private final UserRepository userRepository;

    public IndexController(BoardRepository boardRepository, FormModelMapper modelMapper, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal MynahOidcUser mynahOidcUser, Model model) {
        List<Board> boards = boardRepository.findAll();
        ModelMapper m = this.modelMapper.getModelMapper();
        model.addAttribute("boards", boards.stream()
                .map(b -> m.map(b, BoardInIndexPage.class))
                .collect(Collectors.toList()));
        return "index";
    }
}
