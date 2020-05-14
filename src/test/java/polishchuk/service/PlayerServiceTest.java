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


}
