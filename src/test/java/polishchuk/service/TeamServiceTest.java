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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    private static final int TEAM_ID = 1;
    private static final String TEAM_NAME = "name";
    private static final int PLAYER_ID = 10;
    private static final int NUMBER_OF_PLAYERS = 0;
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

    @Test
    void saveShouldThrowExceptionIfSuchTeamAlreadyExists(){
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.of(teamEntity));

        assertThrows(EntityExistsException.class, ()->systemUnderTest.save(teamDto));
    }

    @Test
    void saveShouldReturnTeamDtoIfSavingWasSuccessful(){
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.empty());
        when(mapper.mapDtoToEntity(teamDto)).thenReturn(teamEntity);
        when(mapper.mapEntityToDto(teamEntity)).thenReturn(teamDto);
        when(teamRepository.save(teamEntity)).thenReturn(teamEntity);

        TeamDto actual = systemUnderTest.save(teamDto);

        assertEquals(teamDto, actual);
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionIfTeamNotExists(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(playerEntity));
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.update(PLAYER_ID, teamDto));
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionIfPlayerNotExists(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.update(PLAYER_ID, teamDto));
    }

    @Test
    void updateShouldReturnMapIfUpdatingWasSuccessful(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(playerEntity));
        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.of(teamEntity));
        when(playerRepository.save(playerEntity)).thenReturn(playerEntity);
        when(mapper.mapEntityToDto(teamEntity)).thenReturn(teamDto);

        TeamDto actual = systemUnderTest.update(PLAYER_ID, teamDto);

        assertEquals(teamDto, actual);
    }

    @Test
    void deleteShouldReturnTrueIfDeletingSuccessful(){
        when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity));
        when(teamRepository.findByName("f/a")).thenReturn(Optional.of(new Team()));
        when(playerRepository.saveAll(anyCollection())).thenReturn(Collections.emptyList());

        boolean actual = systemUnderTest.delete(TEAM_ID);

        assertTrue(actual);
    }

    @Test
    void deleteShouldReturnTrueIfThereIsNoTeamToDelete(){
        when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.delete(TEAM_ID);

        assertFalse(actual);
    }
}
