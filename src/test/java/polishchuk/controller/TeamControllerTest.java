package polishchuk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import polishchuk.dto.TeamDto;
import polishchuk.service.PlayerService;
import polishchuk.service.TeamService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @InjectMocks
    TeamController systemUnderTest;

    @Mock
    TeamService teamService;

    @Mock
    PlayerService playerService;

    @Mock
    TeamDto teamDto;

    @Mock
    Model model;

    @Test
    void showAllTeamShouldReturnAllTeamsListAndInvokeModelAndService(){
        when(teamService.findAllTeams()).thenReturn(Collections.emptyList());
        when(model.addAttribute("teams", Collections.emptyList())).thenReturn(model);

        String actual = systemUnderTest.showAllPlayers(model);
        String expected = "all-teams";

        verify(teamService, times(1)).findAllTeams();
        verify(model, times(1)).addAttribute("teams", Collections.emptyList());
        assertEquals(expected, actual);
    }

    @Test
    void showTeamRedactorShouldReturnTeamRedactor(){
        String actual = systemUnderTest.showTeamRedactor();
        String expected = "team-redactor";

        assertEquals(expected, actual);
    }

    @Test
    void createTeamShouldReturnTeamRedactorIfCreationWasFalse(){
        when(teamService.save(teamDto)).thenReturn(false);

        String actual = systemUnderTest.createTeam(teamDto);
        String expected = "team-redactor";

        assertEquals(expected, actual);
    }

    @Test
    void createTeamShouldRedirectOnMainIfCreationWasTrue(){
        when(teamService.save(teamDto)).thenReturn(true);

        String actual = systemUnderTest.createTeam(teamDto);
        String expected = "redirect:/";

        assertEquals(expected, actual);
    }

    @Test
    void showUpdateTeamPageShouldReturnPlayersAdderPageAndInvokeModelAndService(){
        when(playerService.findAllFreePlayers()).thenReturn(Collections.emptyList());
        when(model.addAttribute("players", Collections.emptyList())).thenReturn(model);

        String actual = systemUnderTest.showUpdateTeamPage(model);
        String expected = "players-adder";

        verify(playerService, times(1)).findAllFreePlayers();
        verify(model, times(1)).addAttribute("players", Collections.emptyList());
        assertEquals(expected, actual);
    }

    @Test
    void addPlayerToTeamShouldRedirectToAllTeamsAndInvokeService(){
        when(teamService.update("lastName","team")).thenReturn(true);

        String actual = systemUnderTest.addPlayerToTeam("team", "lastName");
        String expected = "redirect:/teams";

        verify(teamService, times(1)).update("lastName", "team");
        assertEquals(expected, actual);
    }

    @Test
    void deleteTeamShouldRedirectToAllTeamsAndInvokeService(){
        when(teamService.delete("team")).thenReturn(true);

        String actual = systemUnderTest.deleteTeam("team");
        String expected = "redirect:/teams";

        verify(teamService, times(1)).delete("team");
        assertEquals(expected, actual);
    }

}
