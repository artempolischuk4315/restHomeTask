package polishchuk.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

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
    void findAllShouldReturnListOfTeams(){
        when(teamRepository.findAll()).thenReturn(Collections.emptyList());

        List<Team> actual = systemUnderTest.findAllTeams();

        verify(teamRepository, times(1)).findAll();
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void saveShouldReturnFalseIfSuchPlayerAlreadyExists(){
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.of(teamEntity));

        boolean actual = systemUnderTest.save(teamDto);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldReturnTrueIfSuchPlayerNotExists(){
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.save(teamDto);
        boolean expected = true;

        verify(teamRepository, times(1)).save(mapper.mapDtoToEntity(teamDto));
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldReturnFalseIfNoSuchTeamInDB(){

        boolean actual = systemUnderTest.update("lastName", "teamName");
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldReturnFalseIfNoSuchPlayerInDB(){
        when(playerRepository.findByLastName("lastName")).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.update("lastName", "teamName");
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void updateShouldReturnTrueIfUpdateWasSuccessful(){
        when(teamRepository.findByName("teamName")).thenReturn(Optional.of(teamEntity));
        when(playerRepository.findByLastName("lastName")).thenReturn(Optional.of(playerEntity));

        boolean actual = systemUnderTest.update("lastName", "teamName");
        boolean expected = true;

        verify(playerRepository, times(1)).save(playerEntity);
        assertEquals(expected, actual);
    }

    @Test
    void deleteShouldReturnFalseIfNoSuchTeamInDB(){
        when(teamRepository.findByName("teamName")).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.delete( "teamName");
        boolean expected = false;

        assertEquals(expected, actual);
    }


}
