package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import polishchuk.dto.PlayerDto;
import polishchuk.service.PlayerService;


@RestController
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @GetMapping("/players/{id}")
    public PlayerDto getPlayer(@PathVariable Integer id){
        return playerService.findById(id);
    }

    @PostMapping()
    public PlayerDto addPlayer(@RequestBody PlayerDto player){
        return playerService.savePlayer(player);
    }

    @DeleteMapping("/players/{id}")
    public boolean deletePlayer(@PathVariable Integer id){
        return playerService.deletePlayer(id);
    }

    @PutMapping("/players/{id}")
    public PlayerDto updatePlauer(@RequestBody PlayerDto playerDto, @PathVariable Integer id){
        return playerService.updatePlayer(playerDto, id);
    }



}
