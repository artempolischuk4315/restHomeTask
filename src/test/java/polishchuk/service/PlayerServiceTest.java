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
import polishchuk.service.mapper.Mapper;


import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    private static final int PLAYER_ID = 1;
    private static final String PLAYER_NAME = "Name";
    private static final String PLAYER_LAST_NAME = "Last name";
    private static final int PLAYER_AGE = 20;
    private static final String PLAYER_TEAM = "f/a";

    private static final PlayerDto playerDto = getPlayerDto();
    private static PlayerDto getPlayerDto() {
        return PlayerDto.builder()
                .id(PLAYER_ID)
                .name(PLAYER_NAME)
                .lastName(PLAYER_LAST_NAME)
                .age(PLAYER_AGE)
                .team(PLAYER_TEAM)
                .build();
    }

    @InjectMocks
    PlayerService systemUnderTest;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    Player playerEntity;

    @Mock
    Mapper<PlayerDto, Player> playerMapper;

    @Mock
    Team team;

    @Test
    void findByIdShouldReturnPlayerDtoIfFindingSuccessful(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(playerEntity));
        when(playerMapper.mapEntityToDto(playerEntity)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.findById(PLAYER_ID);

        assertEquals(actual, playerDto);
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfFindingNotSuccessful(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.findById(PLAYER_ID));
    }

    @Test
    void savePlayerShouldThrowExceptionIfSuchPlayerAlreadyExists(){
        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.savePlayer(playerDto));
    }

    @Test
    void savePlayerShouldThrowExceptionIfNewPlayerTeamNotExists(){
        when(teamRepository.findByName(PLAYER_TEAM)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.savePlayer(playerDto));
    }

    @Test
    void saveShouldReturnPlayerDtoIfSavingIsSuccessful(){
        when(playerRepository.findByLastName(PLAYER_LAST_NAME)).thenReturn(Optional.of(playerEntity));
        when(teamRepository.findByName(PLAYER_TEAM)).thenReturn(Optional.of(team));
        when(playerRepository.save(playerEntity)).thenReturn(playerEntity);
        when(playerMapper.mapEntityToDto(playerEntity)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.savePlayer(playerDto);

        assertEquals(playerDto, actual);
    }

    @Test
    void updatePlayerShouldThrowExceptionIfSuchPlayerNotExists(){
        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.updatePlayer(playerDto, PLAYER_ID));
    }

    @Test
    void updatePlayerShouldThrowExceptionIfNewPlayerTeamNotExists(){
        when(teamRepository.findByName(PLAYER_TEAM)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->systemUnderTest.updatePlayer(playerDto, PLAYER_ID));
    }

    @Test
    void updateShouldReturnPlayerDtoIfUpdatingIsSuccessful(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(playerEntity));
        when(teamRepository.findByName(PLAYER_TEAM)).thenReturn(Optional.of(team));
        when(playerRepository.save(playerEntity)).thenReturn(playerEntity);
        when(playerMapper.mapEntityToDto(playerEntity)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.updatePlayer(playerDto, PLAYER_ID);

        assertEquals(playerDto, actual);
    }

    @Test
    void deleteShouldReturnTrueIfDeletingWasSuccessful(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(playerEntity));

        boolean actual = systemUnderTest.deletePlayer(PLAYER_ID);

        assertTrue(actual);
    }

    @Test
    void deleteShouldReturnFalseIfNoSuchUserToDelete(){
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.empty());

        boolean actual = systemUnderTest.deletePlayer(PLAYER_ID);

        assertFalse(actual);
    }
}
