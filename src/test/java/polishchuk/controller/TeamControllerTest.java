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



}
