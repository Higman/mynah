package jp.ac.kagawalab.mynah.web.controller;

import jp.ac.kagawalab.mynah.core.repository.BoardRepository;
import jp.ac.kagawalab.mynah.core.security.oauth2.MynahOidcUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    final BoardRepository boardRepository;

    public HomeController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/")
    public String index(Authentication user) {
        return "index";
    }
}
