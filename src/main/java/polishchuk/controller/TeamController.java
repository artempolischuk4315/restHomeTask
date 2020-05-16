package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto team) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.save(team));
    }


    @PutMapping("/{playerId}/players")
    public ResponseEntity<TeamDto> updateTeam
            (@RequestBody TeamDto teamDto, @PathVariable Integer playerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.update(playerId, teamDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id){
        teamService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
