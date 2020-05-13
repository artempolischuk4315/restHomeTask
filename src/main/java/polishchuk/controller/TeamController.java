package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.service.TeamService;

import java.util.List;

@Controller
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/allTeams")
    public String showAllPlayers(Model model){

        List<Team> allTeams = teamService.findAllTeams();
        model.addAttribute("teams", allTeams);

        return "all-teams";
    }


}
