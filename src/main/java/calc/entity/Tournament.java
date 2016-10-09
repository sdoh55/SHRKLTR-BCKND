package calc.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by clementperez on 9/13/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Tournament.findByName", query = "SELECT t FROM Tournament t WHERE t.name = ?1"),
        @NamedQuery(name = "Tournament.findBySportId", query = "SELECT t FROM Tournament t WHERE t.sport.sportId = ?1")
})
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Tournament {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long tournamentId;
    private String name;
    private String displayName;
    private Boolean isOver;

    @ManyToOne
    @JoinColumn(name = "sportId")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player owner;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Match> matchs;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Stats> stats;

    protected Tournament() {}

    public Tournament(String displayName, Sport sport, Player owner) {
        displayName = displayName;
        name = displayName.replaceAll("\\s+","").toLowerCase();
        this.displayName = displayName;
        this.sport = sport;
        this.owner = owner;
        this.isOver = false;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Player getOwner() { return owner; }

    public void setOwner(Player owner) { this.owner = owner; }

    public List<Match> getMatchs() {
        return matchs;
    }

    public void setMatchs(List<Match> matchs) {
        this.matchs = matchs;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public boolean equals(Tournament obj) {
        if (this.getTournamentId() == null || obj == null) {
            return false;
        }
        else {
            return this.getTournamentId() == obj.getTournamentId();
        }
    }
}
