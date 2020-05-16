package polishchuk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import polishchuk.dto.TeamDto;
import polishchuk.service.TeamService;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        ResponseEntity<TeamDto> actual = systemUnderTest.getTeam(TEAM_ID);

        assertEquals(ResponseEntity.status(HttpStatus.OK).body(teamDto), actual);
    }

    @Test
    void createTeamShouldReturnTeamDto() {
        when(teamService.save(teamDto)).thenReturn(teamDto);

        ResponseEntity<TeamDto> actual = systemUnderTest.createTeam(teamDto);

        assertEquals(ResponseEntity.status(HttpStatus.OK).body(teamDto), actual);
    }

    @Test
    void updateTeamShouldReturnMapOfStringAndInteger() {
        when(teamService.update(PLAYER_ID, teamDto)).thenReturn(teamDto);

        ResponseEntity<TeamDto> actual = systemUnderTest.updateTeam(teamDto, PLAYER_ID);

        assertEquals(ResponseEntity.status(HttpStatus.OK).body(teamDto), actual);
    }

    @Test
    void deleteTeamShouldReturnTrueIfDeletingWasSuccessful() {
        ResponseEntity<Void> actual = systemUnderTest.deleteTeam(TEAM_ID);

        assertEquals(ResponseEntity.status(HttpStatus.OK).build(), actual);
    }

}
