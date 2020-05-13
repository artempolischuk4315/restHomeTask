package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if(!playerTeam.isPresent()||
                playerRepository.findByLastName(player.getLastName()).isPresent()){
            return false;
        }

        Player newPlayer = new Player(player.getName(),
                player.getLastName(), player.getAge(), playerTeam.get());
        playerRepository.save(newPlayer);

        return true;
    }

    public boolean updatePlayer(PlayerDto player){
        Optional<Team> playerTeam = teamRepository.findByName(player.getTeam());
        Optional<Player> playerInDbOptional = playerRepository.findByLastName(player.getLastName());

        if(!playerTeam.isPresent()||!playerInDbOptional.isPresent()){
            return false;
        }

        Player playerInDb = playerInDbOptional.get();
        playerInDb.setAge(player.getAge());
        playerInDb.setName(player.getName());
        playerInDb.setTeam(playerTeam.get());

        playerRepository.save(playerInDb);

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

    public List<Player> findAllFreePlayers(){
        return playerRepository.findAll().stream()
                .filter(player -> player.getTeam().getName().equals("f/a"))
                .collect(Collectors.toList());
    }
}
