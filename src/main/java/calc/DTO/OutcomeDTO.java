package calc.DTO;

import calc.entity.Match;
import calc.entity.Outcome;
import calc.entity.Player;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by clementperez on 10/2/16.
 */
public class OutcomeDTO {

    private Long outcomeId;
    private double scoreValue;
    private Outcome.Result result;
    private String userName;
    private Long matchId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Outcome.Result getResult() {
        return result;
    }

    public void setResult(Outcome.Result result) {
        this.result = result;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
}
