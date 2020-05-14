package polishchuk.mapper.impl;

import org.springframework.stereotype.Component;
import polishchuk.dto.PlayerDto;
import polishchuk.entity.Player;
import polishchuk.mapper.Mapper;

@Component
public class PlayerMapper implements Mapper<PlayerDto, Player> {
    @Override
    public PlayerDto mapEntityToDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .lastName(player.getLastName())
                .age(player.getAge())
                .team(player.getTeam().getName())
                .build();
    }

    @Override
    public Player mapDtoToEntity(PlayerDto playerDto) {
        return Player.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .lastName(playerDto.getLastName())
                .age(playerDto.getAge())
                .build();
    }
}
