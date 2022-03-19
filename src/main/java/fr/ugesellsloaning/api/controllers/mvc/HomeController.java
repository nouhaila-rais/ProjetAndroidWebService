package fr.ugesellsloaning.api.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/admin")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/admin/home")
    public String home(Model model){
        return "dashboard";
    }
}
