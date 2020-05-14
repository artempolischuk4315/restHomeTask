package polishchuk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.service.PlayerService;
import polishchuk.service.mapper.Mapper;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    private static final PlayerDto playerDto = getPlayerDto();
    private static final int PLAYER_ID = 1;
    private static final String PLAYER_NAME = "Name";
    private static final String TEAM_NAME = "f/a";
    private static final String LAST_NAME = "Last name";
    private static final int AGE = 20;

    private static PlayerDto getPlayerDto() {
        return PlayerDto.builder()
                .id(PLAYER_ID)
                .name(PLAYER_NAME)
                .lastName(LAST_NAME)
                .age(AGE)
                .team(TEAM_NAME)
                .build();
    }

    @InjectMocks
    PlayerController systemUnderTest;

    @Mock
    PlayerService playerService;

    @Test
    void getPlayerShouldReturnPlayerDtoWithTheSameIdAsEntered(){
        when(playerService.findById(PLAYER_ID)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.getPlayer(PLAYER_ID);

        assertEquals(playerDto, actual);
    }

    @Test
    void addPlayerShouldReturnPlayerDto(){
        when(playerService.savePlayer(playerDto)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.addPlayer(playerDto);

        assertEquals(playerDto, actual);
    }

    @Test
    void updatePlayerShouldReturnUpdatedPlayerDto(){
        when(playerService.updatePlayer(playerDto, PLAYER_ID)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.updatePlayer(playerDto, PLAYER_ID);

        assertEquals(playerDto, actual);
    }

    @Test
    void deletePlayerShouldReturnFalseIfDeletingWasNotSuccessful(){
        when(playerService.deletePlayer(PLAYER_ID)).thenReturn(false);

        boolean actual = systemUnderTest.deletePlayer(PLAYER_ID);

        assertFalse(actual);
    }

    @Test
    void deletePlayerShouldReturnTrueIfDeletingWasSuccessful(){
        when(playerService.deletePlayer(PLAYER_ID)).thenReturn(true);

        boolean actual = systemUnderTest.deletePlayer(PLAYER_ID);

        assertTrue(actual);
    }


}
