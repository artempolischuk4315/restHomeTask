package polishchuk.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.service.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    public static final int TEAM_ID = 1;
    @InjectMocks
    TeamService systemUnderTest;

    @Mock
    TeamRepository teamRepository;


    @Mock
    PlayerRepository playerRepository;

    @Mock
    TeamDto teamDto;

    @Mock
    Team teamEntity;


    @Mock
    Player playerEntity;

    @Mock
    Mapper<TeamDto, Team> mapper;

    @Test
    void findByIdShouldReturnTeamDto(){
        when(teamRepository.findById(TEAM_ID)).thenReturn(java.util.Optional.of(teamEntity));
        when(mapper.mapEntityToDto(teamEntity)).thenReturn(teamDto);

        TeamDto actual = systemUnderTest.findById(TEAM_ID);

        assertEquals(teamDto, actual);
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfNoSuchTeam(){
        when(teamRepository.findById(TEAM_ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.findById(TEAM_ID));
    }
}
