package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.service.PlayerService;
import polishchuk.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
    }


    @GetMapping("/teams/{id}")
    public TeamDto getTeam(@PathVariable Integer id){
        return teamService.findById(id);
    }

    @PostMapping("/teams/creator")
    public TeamDto createTeam(@RequestBody TeamDto team){
        return teamService.save(team);
    }


    @PutMapping("/teams/updater/{playerId}")
    public Map<String, Integer> updateTeam
            (@RequestBody TeamDto teamDto, @PathVariable Integer playerId){
        return teamService.update(playerId, teamDto);
    }

    @DeleteMapping("/teams/remover/{id}")
    public boolean deleteTeam(@PathVariable Integer id){
        return teamService.delete(id);
    }
}
