package polishchuk.mapper.impl;

import org.springframework.stereotype.Component;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;

import java.util.Collections;

@Component
public class TeamMapper implements Mapper<TeamDto, Team> {

    @Override
    public TeamDto mapEntityToDto(Team team) {
        return TeamDto.builder().name(team.getName()).build();
    }

    @Override
    public Team mapDtoToEntity(TeamDto teamDto) {
        return Team.builder().name(teamDto.getName()).players(Collections.emptyList()).build();
    }
}
