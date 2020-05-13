package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private static final String FREE_PLAYERS = "f/a";
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private Mapper<TeamDto, Team> mapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, Mapper<TeamDto, Team> mapper){
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.mapper = mapper;
    }

    public List<Team> findAllTeams(){
       return teamRepository.findAll();
    }

    public boolean save(TeamDto team){
        Optional<Team> teamInDb = teamRepository.findByName(team.getName());
        if(teamInDb.isPresent()){
            return false;
        }

        teamRepository.save(mapper.mapDtoToEntity(team));
        return true;
    }

    public boolean update(String lastName, String teamName){
        Optional <Player> optionalPlayer = playerRepository.findByLastName(lastName);
        Optional <Team> optionalTeam = teamRepository.findByName(teamName);

        if(!optionalPlayer.isPresent()||!optionalTeam.isPresent()) {
            return false;
        }

        optionalPlayer.get().setTeam(optionalTeam.get());
        playerRepository.save(optionalPlayer.get());

        return true;
    }


    @Transactional
    public boolean delete (String teamName){
        Optional <Team> optionalTeam = teamRepository.findByName(teamName);
        if(!optionalTeam.isPresent())
            return false;

        Team teamToDelete = optionalTeam.get();
        FreePlayers(teamToDelete);
        teamRepository.delete(teamToDelete);
        return true;
    }

    private List<Player> FreePlayers(Team teamToDelete) {
        Team freeTeam = teamRepository.findByName(FREE_PLAYERS).get();

        List<Player> playersToFree = teamToDelete.getPlayers().stream()
                .peek(player -> player.setTeam(freeTeam))
                .collect(Collectors.toList());

        return playerRepository.saveAll(playersToFree);
    }
}
