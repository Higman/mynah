package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    final BoardRepository boardRepository;

    public HomeController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
