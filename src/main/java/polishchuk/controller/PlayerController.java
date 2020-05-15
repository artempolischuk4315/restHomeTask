package polishchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public PlayerDto getPlayer(@PathVariable Integer id){
        return playerService.findById(id);
    }

    @PostMapping()
    public PlayerDto addPlayer(@RequestBody PlayerDto player){
        return playerService.savePlayer(player);
    }

    @DeleteMapping("/{id}")
    public boolean deletePlayer(@PathVariable Integer id){
        return playerService.deletePlayer(id);
    }

    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@RequestBody PlayerDto playerDto, @PathVariable Integer id){
        return playerService.updatePlayer(playerDto, id);
    }



}
