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
@RequestMapping("/teams")
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
    }


    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Integer id){
        return teamService.findById(id);
    }

    @PostMapping("")
    public TeamDto createTeam(@RequestBody TeamDto team){
        return teamService.save(team);
    }


    @PutMapping("/{playerId}")
    public TeamDto updateTeam
            (@RequestBody TeamDto teamDto, @PathVariable Integer playerId){
        return teamService.update(playerId, teamDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTeam(@PathVariable Integer id){
        return teamService.delete(id);
    }
}
