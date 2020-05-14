package polishchuk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "Team name='" + name + '\'';
    }
}
