package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.service.PlayerService;
import polishchuk.service.TeamService;

import java.util.List;

@Controller
public class TeamController {

    private TeamService teamService;
    private PlayerService playerService;

    @Autowired
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/teams")
    public String showAllPlayers(Model model){

        List<Team> allTeams = teamService.findAllTeams();
        model.addAttribute("teams", allTeams);

        return "all-teams";
    }

    @GetMapping("/teams/redactor")
    public String showTeamRedactor(){

        return "team-redactor";
    }

    @PostMapping("/teams/creator")
    public String createTeam(TeamDto team){

        if(!teamService.save(team)){
            return "team-redactor";
        }

        return "redirect:/";
    }


    @GetMapping("/teams/player-to-team")
    public String showUpdateTeamPage( Model model){

        List<Player> freePlayers = playerService.findAllFreePlayers();
        model.addAttribute("players", freePlayers);

        return "players-adder";
    }

    @PutMapping("/teams/player-to-team")
    public String addPlayerToTeam(String teamName, String lastName){

        teamService.update(lastName, teamName);

        return "redirect:/teams";
    }

    @DeleteMapping("/teams/remover")
    public String deleteTeam(String teamName){

        teamService.delete(teamName);

        return "redirect:/teams";
    }
}
