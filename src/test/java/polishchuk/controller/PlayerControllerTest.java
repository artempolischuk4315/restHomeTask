package polishchuk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.service.PlayerService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @InjectMocks
    PlayerController systemUnderTest;

    @Mock
    PlayerService playerService;

    @Mock
    Model model;

    @Mock
    PlayerDto player;

    @Mock
    Player playerEntity;

    @Test
    void showAllPlayersShouldReturnAllPlayersPageAndInvokeModel(){
        when(model.addAttribute(anyString(), any())).thenReturn(model);
        when(playerService.findAllPlayers()).thenReturn(Collections.emptyList());

        String actual = systemUnderTest.showAllPlayers(model);
        String expected = "all-players";

        verify(model, times(1)).addAttribute("players", Collections.emptyList());
        assertEquals(expected, actual);
    }

    @Test
    void addPlayerShouldRedirectOnMainIfSavingPlayerIsSuccessful(){
        when(playerService.savePlayer(player)).thenReturn(true);

        String actual = systemUnderTest.addPlayer(player, null);
        String expected = "redirect:/";

        assertEquals(expected, actual);
    }


    @Test
    void addPlayerShouldStayOnPlayerRedactorAndInvokeModelIfSavingPlayerIsFalse(){
        when(model.addAttribute(anyString(), any())).thenReturn(model);
        when(playerService.savePlayer(player)).thenReturn(false);

        String actual = systemUnderTest.addPlayer( player, model);
        String expected = "player-redactor";

        verify(model, times(1)).addAttribute("alreadyExists", true);
        assertEquals(expected, actual);
    }

    @Test
    void deletePlayerShouldAddErrorAttributeAndRedirectOnPlayersPageIfDeletingPlayerWasFalse(){
        when(playerService.deletePlayer("lastName")).thenReturn(false);
        when(model.addAttribute(anyString(), any())).thenReturn(model);

        String actual = systemUnderTest.deletePlayer("lastName", model);
        String expected = "redirect:/players";

        verify(model, times(1)).addAttribute("error", true);
        assertEquals(expected, actual);
    }

    @Test
    void deletePlayerShouldNotAddErrorAttributeButRedirectOnPlayersPageIfDeletingPlayerWasTrue(){
        when(playerService.deletePlayer("lastName")).thenReturn(true);

        String actual = systemUnderTest.deletePlayer("lastName", model);
        String expected = "redirect:/players";

        verify(model, times(0)).addAttribute("error", true);
        assertEquals(expected, actual);
    }

    @Test
    void showUpdatePageShouldAddErrorAttributeAndRedirectOnPlayersPageIfNoSuchUserInDB(){
        when(model.addAttribute(anyString(), any())).thenReturn(model);
        when(playerService.findByLastName("lastName")).thenReturn(null);

        String actual = systemUnderTest.showUpdatePage("lastName", model);
        String expected = "redirect:/players";

        verify(model, times(1)).addAttribute("error", true);
        assertEquals(expected, actual);
    }

    @Test
    void showUpdatePageShouldNotAddErrorAttributeAndRedirectOnPlayersPageIfNoSuchUserInDB(){
        when(model.addAttribute(anyString(), any())).thenReturn(model);
        when(playerService.findByLastName("lastName")).thenReturn(playerEntity);

        String actual = systemUnderTest.showUpdatePage("lastName", model);
        String expected = "update-player";

        verify(playerService, times(1)).findByLastName("lastName");
        verify(model, times(0)).addAttribute("error", true);
        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerShouldReturnUpdatePageIfUpdatingWasNotSuccessful(){
        when(playerService.updatePlayer(player)).thenReturn(false);

        String actual = systemUnderTest.updatePlayer(player);
        String expected = "update-player";

        assertEquals(expected, actual);
    }

    @Test
    void updatePlayerShouldReturnOnMainPageIfUpdatingWasSuccessful(){
        when(playerService.updatePlayer(player)).thenReturn(true);

        String actual = systemUnderTest.updatePlayer(player);
        String expected = "redirect:/";

        assertEquals(expected, actual);
    }
}
