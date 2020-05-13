package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.service.PlayerService;


import java.util.List;

@Controller
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @GetMapping("/players")
    public String showAllPlayers(Model model){

        List<Player> allPlayers = playerService.findAllPlayers();
        model.addAttribute("players", allPlayers);

        return "all-players";
    }

    @GetMapping("/players/redactor")
    public String showPlayerRedactor(){

        return "player-redactor";
    }

    @PostMapping("/players/creator")
    public String addPlayer(PlayerDto player, Model model){


        if(!playerService.savePlayer(player)){
            model.addAttribute("alreadyExists", true);
            return "player-redactor";
        }


        return "redirect:/";
    }

    @DeleteMapping("/players/remover")
    public String deletePlayer(String lastName, Model model){

        if(!playerService.deletePlayer(lastName)){
            model.addAttribute("error", true);
        }

        return "redirect:/players";
    }

    @PostMapping("/players/editor")
    public String showUpdatePage(String lastName, Model model){

        Player player = playerService.findByLastName(lastName);

        if(player==null){
            model.addAttribute("error", true);
            return "redirect:/players";
        }

        model.addAttribute("player", player);

        return "update-player";
    }

    @PutMapping("/players/editor")
    public String updatePlayer(PlayerDto player){

        if(!playerService.updatePlayer(player)){
            return "update-player";
        }

        return "redirect:/";
    }

}
