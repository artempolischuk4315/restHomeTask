package polishchuk.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polishchuk.dto.TeamDto;
import polishchuk.entity.Player;
import polishchuk.entity.Team;
import polishchuk.mapper.Mapper;
import polishchuk.repository.PlayerRepository;
import polishchuk.repository.TeamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

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




}
