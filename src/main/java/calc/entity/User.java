// tag::sample[]
package calc.entity;

import calc.DTO.UserDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name = "User.findByUserName", query = "SELECT p FROM User p WHERE p.userName = ?1")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Stats> stats;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Outcome> outcomes;

    protected User() {}

    public User(String userName) {
        this.userName = userName;
        this.stats = new ArrayList<Stats>();
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', lastName='%s']",
                userId, firstName, lastName);
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public void setUserId(Long userId) {
        this.userId = userId;
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

