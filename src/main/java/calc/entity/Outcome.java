package calc.entity;

import javax.persistence.*;

/**
 * Created by clementperez on 9/13/16.
 */

@Entity

@NamedQueries({
        @NamedQuery(name = "Outcome.findByUserId", query = "SELECT o FROM Outcome o WHERE o.user.userId = ?1"),
        @NamedQuery(name = "Outcome.findByMatchId", query = "SELECT o FROM Outcome o WHERE o.match.matchId = ?1")
})
public class Outcome {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long outcomeId;
    private double scoreValue;
    private Result result;
    @ManyToOne
    @JoinColumn(name = "matchId")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    protected Outcome() {}

    public Outcome(double scoreValue, Result results, Match match, User user) {
        this.scoreValue = scoreValue;
        this.result = results;
        this.match = match;
        this.user = user;
    }

    public Long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public double getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Result getResults() {
        return result;
    }

    public void setResults(Result results) {
        this.result = results;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum Result {
        WIN(1),
        TIE(2),
        LOSS(3);

         Result(int i) {
        }
    };
}
