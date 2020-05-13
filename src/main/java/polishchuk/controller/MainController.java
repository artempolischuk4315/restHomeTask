package polishchuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showPage(){
        return "index";
    }

    @GetMapping("/players/redactor")
    public String showPlayerRedactor(Model model){

        return "player-redactor";
    }
}
