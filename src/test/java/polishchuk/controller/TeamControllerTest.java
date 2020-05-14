package polishchuk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.TeamDto;
import polishchuk.service.TeamService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    private static final TeamDto teamDto = getTeamDto();
    private static final int PLAYER_ID = 5;
    private static final int TEAM_ID = 1;
    private static final String TEAM_NAME = "Dynamo";
    private static final int NUMBER_OF_PLAYERS = 20;

    private static TeamDto getTeamDto() {
        return TeamDto.builder()
                .id(1)
                .name("Dynamo")
                .build();
    }

    @InjectMocks
    TeamController systemUnderTest;

    @Mock
    TeamService teamService;

    @Test
    void getTeamShouldReturnTeamDto() {
        when(teamService.findById(TEAM_ID)).thenReturn(teamDto);

        TeamDto actual = systemUnderTest.getTeam(TEAM_ID);

        assertEquals(teamDto, actual);
    }

    @Test
    void createTeamShouldReturnTeamDto() {
        when(teamService.save(teamDto)).thenReturn(teamDto);

        TeamDto actual = systemUnderTest.createTeam(teamDto);

        assertEquals(teamDto, actual);
    }

    @Test
    void updateTeamShouldReturnMapOfStringAndInteger() {
        Map<String, Integer> teamAndNumberOfPlayers = new HashMap<>();
        teamAndNumberOfPlayers.put(TEAM_NAME, NUMBER_OF_PLAYERS);
        when(teamService.update(PLAYER_ID, teamDto)).thenReturn(teamAndNumberOfPlayers);

        Map<String, Integer> actual = systemUnderTest.updateTeam(teamDto, PLAYER_ID);

        assertEquals(teamAndNumberOfPlayers, actual);
    }

    @Test
    void deleteTeamShouldReturnTrueIfDeletingWasSuccessful() {
        when(teamService.delete(TEAM_ID)).thenReturn(true);

        boolean actual = systemUnderTest.deleteTeam(TEAM_ID);

        assertTrue(actual);
    }

    @Test
    void deleteTeamShouldReturnFalseIfDeletingWasNotSuccessful() {
        when(teamService.delete(TEAM_ID)).thenReturn(false);

        boolean actual = systemUnderTest.deleteTeam(TEAM_ID);

        assertFalse(actual);
    }
}
