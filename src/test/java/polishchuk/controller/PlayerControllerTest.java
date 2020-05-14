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

}
