package polishchuk.service.mapper.impl;

import org.springframework.stereotype.Component;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Team;
import polishchuk.service.mapper.Mapper;

import java.util.Collections;

@Component
public class TeamMapper implements Mapper<TeamDto, Team> {

    @Override
    public TeamDto mapEntityToDto(Team team) {
        return TeamDto.builder().name(team.getName()).id(team.getId()).build();
    }

    @Override
    public Team mapDtoToEntity(TeamDto teamDto) {
        return Team.builder().name(teamDto.getName()).players(Collections.emptyList()).id(teamDto.getId()).build();
    }
}
