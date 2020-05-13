package polishchuk.entity;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String lastName;

    @Column
    private int age;

    @ManyToOne
    @JoinColumn(name="team", referencedColumnName = "name", nullable=false)
    private Team team;

    public Player(String name, String lastName, int age, Team team) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age + '\''
                +"team: "+ team.getName();
    }
}
