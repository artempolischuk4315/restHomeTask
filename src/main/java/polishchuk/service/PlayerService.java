package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.service.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private Mapper<PlayerDto, Player> mapper;


    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository, Mapper<PlayerDto, Player> mapper){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.mapper = mapper;
    }

    public PlayerDto findById(Integer id){
        return mapper.mapEntityToDto(playerRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public PlayerDto savePlayer(PlayerDto playerDto){
        Optional<Player> player = playerRepository.findByLastName(playerDto.getLastName());
        Optional<Team> playerTeam = teamRepository.findByName(playerDto.getTeam());

        checkIfPlayerOrTeamExists(player, playerTeam);

        player.get().setTeam(playerTeam.get());

        return mapper.mapEntityToDto(playerRepository.save(player.get()));

    }

    public PlayerDto updatePlayer(PlayerDto playerDto, Integer id){
        Optional<Player> player = playerRepository.findById(id);
        Optional<Team> playerTeam = teamRepository.findByName(playerDto.getTeam());

        checkIfPlayerOrTeamExists(player, playerTeam);
        playerDto.setId(id);

        Player playerEntity = setPlayerEntityForSavingWithRightIdAndTeam(player, playerTeam);

        return mapper.mapEntityToDto(playerRepository.save(playerEntity));
    }

    public boolean deletePlayer(Integer id){
        Optional<Player> player = playerRepository.findById(id);
        if(!player.isPresent()){
            return false;
        }
        playerRepository.delete(player.get());
        return true;
    }

    private Player setPlayerEntityForSavingWithRightIdAndTeam(Optional<Player> player, Optional<Team> playerTeam) {
        Player playerEntity = player.get();
        playerEntity.setTeam(playerTeam.get());
        return playerEntity;
    }

    private void checkIfPlayerOrTeamExists(Optional<Player> player, Optional<Team> playerTeam) {
        if((!playerTeam.isPresent()) || (!player.isPresent())){
            throw new EntityNotFoundException("Such player already exists or no such team");
        }
    }
}
