package polishchuk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {

    private Integer id;

    private String name;

    private List<String> playersLastNames;

    @Override
    public String toString() {
        return "Team name='" + name + '\'';
    }
}
