// tag::sample[]
package calc.entity;

import calc.DTO.PlayerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name = "Player.findByUserName", query = "SELECT p FROM Player p WHERE p.userName = ?1")
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long playerId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Stats> stats;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Outcome> outcomes;

    protected Player() {}

    public Player(String userName) {
        this.userName = userName;
        this.stats = new ArrayList<Stats>();
    }

    @Override
    public String toString() {
        return String.format(
                "Player[id=%d, firstName='%s', lastName='%s']",
                playerId, firstName, lastName);
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public Stats getStats(Tournament tournament) {
        for(Stats s : stats){
            if(s.getTournament().equals(tournament)) {
                return s;
            }
        }
        return null;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
}

