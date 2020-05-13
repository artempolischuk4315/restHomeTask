package polishchuk.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @InjectMocks
    PlayerService systemUnderTest;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    PlayerDto playerDto;

    @Mock
    Team team;

    @Mock
    Player playerEntity;

    @Test
    void findAllShouldReturnListOfPlayers(){
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Player> actual = systemUnderTest.findAllPlayers();

        verify(playerRepository, times(1)).findAll();
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void savePlayerShouldReturnFalseIfNoSuchTeamPlayerInDBAsEntered(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.savePlayer(playerDto);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void savePlayerShouldReturnFalseWhenSavingIsNotSuccessfulBecauseThisPlayerExists(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.of(team));
        when(playerRepository.findByLastName(playerDto.getLastName())).thenReturn(Optional.of(playerEntity));

        boolean actual = systemUnderTest.savePlayer(playerDto);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void savePlayerShouldReturnTrueIfSavingIsSuccessful(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.of(team));

        boolean actual = systemUnderTest.savePlayer(playerDto);
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerShouldReturnFalseIfNoSuchTeamInDBAsEntered(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.updatePlayer(playerDto);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerShouldReturnTrueIfNoSuchPlayerInDB(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.of(team));
        when(playerRepository.findByLastName(playerDto.getLastName())).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.updatePlayer(playerDto);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerShouldReturnTrueIfUpdateIsSuccessful(){
        when(teamRepository.findByName(playerDto.getTeam())).thenReturn(Optional.of(team));
        when(playerRepository.findByLastName(playerDto.getLastName())).thenReturn(Optional.of(playerEntity));

        boolean actual = systemUnderTest.updatePlayer(playerDto);
        boolean expected = true;

        verify(playerEntity, times(1)).setAge(playerDto.getAge());
        verify(playerEntity, times(1)).setName(playerDto.getName());
        verify(playerEntity, times(1)).setTeam(team);
        verify(playerRepository, times(1)).save(playerEntity);
        assertEquals(expected, actual);
    }

    @Test
    void deletePlayerShouldReturnFalseIfNoSuchUserInDB(){
        when(playerRepository.findByLastName(playerDto.getLastName())).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.deletePlayer(playerDto.getLastName());
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    void deletePlayerShouldReturnTrueIfDeletingSuccessful(){
        when(playerRepository.findByLastName(playerDto.getLastName())).thenReturn(Optional.of(playerEntity));

        boolean actual = systemUnderTest.deletePlayer(playerDto.getLastName());
        boolean expected = true;

        verify(playerRepository, times(1)).delete(playerEntity);
        assertEquals(expected, actual);
    }

    @Test
    void findByLastNameShouldReturnNullIfNoSuchPlayer(){
        when(playerRepository.findByLastName("LastName")).thenReturn(Optional.empty());

        Player actual = systemUnderTest.findByLastName("LastName");

        assertNull(actual);
    }

    @Test
    void findByLastNameShouldReturnPlayerIfSuchPlayerExists(){
        when(playerRepository.findByLastName("LastName")).thenReturn(Optional.of(playerEntity));

        Player actual = systemUnderTest.findByLastName("LastName");

        assertNotNull(actual);
        assertEquals(playerEntity, actual);
    }
}
