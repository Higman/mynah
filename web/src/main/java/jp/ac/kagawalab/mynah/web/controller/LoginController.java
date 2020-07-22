package jp.ac.kagawalab.mynah.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        return "admin_login";
    }
}
