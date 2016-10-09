package calc.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by clementperez on 9/13/16.
 */

@Entity


@NamedQueries({
        @NamedQuery(name = "Match.findByTournamentName", query = "SELECT m FROM Match m WHERE m.tournament.name = ?1"),
        @NamedQuery(name = "Match.findByPlayerId",
                query = "SELECT m FROM Match m " +
                        "INNER JOIN m.outcomes o " +
                        "WHERE o.player.playerId = ?1"),
        @NamedQuery(name = "Match.findByPlayerIdByTournamentName",
                query = "SELECT m FROM Match m " +
                        "INNER JOIN m.outcomes o " +
                        "WHERE o.player.playerId = ?1 AND m.tournament.name=?2" )
})
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long matchId;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "tournamentId")
    private Tournament tournament;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Outcome> outcomes;

    protected Match() {}

    public Match(Date date, Tournament tournament) {
        this.date = date;
        this.tournament = tournament;
    }

    public Match(Tournament tournament) {
        this.date = new Date();
        this.tournament = tournament;
    }

    public Match(Tournament tournament, List<Outcome> outcomes) {
        this.date = new Date();
        this.tournament = tournament;
        this.outcomes = outcomes;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
        for(Outcome o : outcomes){
            o.setMatch(this);
        }
    }
}
