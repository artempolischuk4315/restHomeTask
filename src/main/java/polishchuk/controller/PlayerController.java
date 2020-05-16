package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polishchuk.dto.PlayerDto;
import polishchuk.service.PlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<PlayerDto> addPlayer(@RequestBody PlayerDto player){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.savePlayer(player));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id){
        playerService.deletePlayer(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <PlayerDto> updatePlayer(@RequestBody PlayerDto playerDto, @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.updatePlayer(playerDto, id));
    }



}
