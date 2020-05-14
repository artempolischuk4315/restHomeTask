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
        Player player = mapper.mapDtoToEntity(playerDto);
        Optional<Team> playerTeam = teamRepository.findByName(playerDto.getTeam());

        checkIfPlayerOrTeamExists(player, playerTeam);

        player.setTeam(playerTeam.get());

        return mapper.mapEntityToDto(playerRepository.save(player));

    }

    public PlayerDto updatePlayer(PlayerDto playerDto, Integer id){
        Player player = mapper.mapDtoToEntity(playerDto);
        Optional<Team> playerTeam = teamRepository.findByName(playerDto.getTeam());

        checkIfPlayerOrTeamExists(player, playerTeam);
        playerDto.setId(id);

        Player playerEntity = setPlayerEntityForSavingWithRightIdAndTeam(playerDto, playerTeam);

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

    private Player setPlayerEntityForSavingWithRightIdAndTeam(PlayerDto playerDto, Optional<Team> playerTeam) {
        Player playerEntity = mapper.mapDtoToEntity(playerDto);
        playerEntity.setTeam(playerTeam.get());
        return playerEntity;
    }

    private void checkIfPlayerOrTeamExists(Player player, Optional<Team> playerTeam) {
        if(!playerTeam.isPresent()||
                playerRepository.findByLastName(player.getLastName()).isPresent()){
            throw new EntityNotFoundException("Such player already exists or no such team");
        }
    }
}
