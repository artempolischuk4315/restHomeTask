package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> findAllPlayers(){
       return playerRepository.findAll();
    }

    public boolean savePlayer(PlayerDto player){

        Optional<Team> playerTeam = teamRepository.findByName(player.getTeam());

        if(!playerTeam.isPresent()){
            return false;
        }

        Player newPlayer = new Player(player.getName(),
                player.getLastName(), player.getAge(), playerTeam.get());

        try {
            playerRepository.save(newPlayer);
        }catch (DataIntegrityViolationException e){
            return false;
        }

        return true;
    }

    public boolean updatePlayer(PlayerDto player){

        Optional<Team> playerTeam = teamRepository.findByName(player.getTeam());

        if(!playerTeam.isPresent()){
            return false;
        }

        Player playerInDb = playerRepository.findByLastName(player.getLastName()).get();

        playerInDb.setAge(player.getAge());
        playerInDb.setName(player.getName());
        playerInDb.setTeam(playerTeam.get());

        try {
            playerRepository.save(playerInDb);
        }catch (DataIntegrityViolationException e){
            return false;
        }

        return true;
    }

    public boolean deletePlayer(String lastName){

        Optional<Player> player = playerRepository.findByLastName(lastName);
        if(!player.isPresent()){
            return false;
        }
        playerRepository.delete(player.get());
        return true;
    }

    public Player findByLastName(String lastName) {
        Optional<Player> player = playerRepository.findByLastName(lastName);

        return player.orElse(null);

    }

}
