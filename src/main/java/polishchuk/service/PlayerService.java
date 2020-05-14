package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(!playerTeam.isPresent()||
                playerRepository.findByLastName(player.getLastName()).isPresent()){
            throw new EntityNotFoundException("Such player already exists or no such team");
        }
        player.setTeam(playerTeam.get());

        return mapper.mapEntityToDto(playerRepository.save(player));

    }

    public PlayerDto updatePlayer(PlayerDto player, Integer id){

        playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such player"));

        System.out.println("TEAM IS "+player.getTeam());
        Team playerTeam = teamRepository.findByName(player.getTeam()).orElseThrow(()-> new EntityNotFoundException("No such team"));

        player.setId(id);
        Player playerEntity = mapper.mapDtoToEntity(player);
        playerEntity.setTeam(playerTeam);

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


    public List<Player> findAllFreePlayers(){
        return playerRepository.findAll().stream()
                .filter(player -> player.getTeam().getName().equals("f/a"))
                .collect(Collectors.toList());
    }
}
