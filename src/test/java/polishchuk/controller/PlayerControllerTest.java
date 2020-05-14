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

    private static PlayerDto getPlayerDto() {
        return PlayerDto.builder()
                .id(1)
                .name("Name")
                .lastName("Last name")
                .age(20)
                .team("f/a")
                .build();
    }

    @InjectMocks
    PlayerController systemUnderTest;

    @Mock
    PlayerService playerService;

    @Test
    void getPlayerShouldReturnPlayerDtoWithTheSameIdAsEntered(){
        when(playerService.findById(1)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.getPlayer(1);

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
        when(playerService.updatePlayer(playerDto, 1)).thenReturn(playerDto);

        PlayerDto actual = systemUnderTest.updatePlayer(playerDto, 1);

        assertEquals(playerDto, actual);
    }

    @Test
    void deletePlayerShouldReturnFalseIfDeletingWasNotSuccessful(){
        when(playerService.deletePlayer(1)).thenReturn(false);

        boolean actual = systemUnderTest.deletePlayer(1);

        assertFalse(actual);
    }

    @Test
    void deletePlayerShouldReturnTrueIfDeletingWasSuccessful(){
        when(playerService.deletePlayer(1)).thenReturn(true);

        boolean actual = systemUnderTest.deletePlayer(1);

        assertTrue(actual);
    }


}
