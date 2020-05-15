package polishchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.service.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private static final String FREE_PLAYERS_TEAM = "f/a";
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private Mapper<TeamDto, Team> mapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, Mapper<TeamDto, Team> mapper){
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.mapper = mapper;
    }

    public TeamDto findById(Integer id){
        return mapper.mapEntityToDto(teamRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public TeamDto save(TeamDto team){
        Optional<Team> teamInDb = teamRepository.findByName(team.getName());
        if(teamInDb.isPresent()){
           throw new EntityExistsException("Such team already exists");
        }

        return mapper.mapEntityToDto(teamRepository.save(mapper.mapDtoToEntity(team)));
    }

    @Transactional
    public TeamDto update(Integer playerId, TeamDto teamDto){
        Player player = playerRepository
                .findById(playerId).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository
                .findByName(teamDto.getName()).orElseThrow(EntityNotFoundException::new);

        player.setTeam(team);
        team.getPlayers().add(player);

        playerRepository.save(player);
        return mapper.mapEntityToDto(team);
    }


    @Transactional
    public boolean delete (Integer id){
        Optional <Team> optionalTeam = teamRepository.findById(id);
        if(!optionalTeam.isPresent())
            return false;

        Team teamToDelete = optionalTeam.get();
        FreePlayers(teamToDelete);
        teamRepository.delete(teamToDelete);
        return true;
    }

    private List<Player> FreePlayers(Team teamToDelete) {
        Team freeTeam = teamRepository.findByName(FREE_PLAYERS_TEAM).get();

        List<Player> playersToFree = teamToDelete.getPlayers().stream()
                .peek(player -> player.setTeam(freeTeam))
                .collect(Collectors.toList());

        return playerRepository.saveAll(playersToFree);
    }
}
