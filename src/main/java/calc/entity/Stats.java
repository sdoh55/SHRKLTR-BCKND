package calc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by clementperez on 9/13/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Stats.findByPlayerId", query = "SELECT s FROM Stats s WHERE s.player.playerId = ?1"),
        @NamedQuery(name = "Stats.findByPlayerAndTournament", query = "SELECT s FROM Stats s WHERE s.player.playerId = ?1 AND s.tournament.name = ?2")
})
public class Stats {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long statsId;
    private double score;
    private int gameCount;
    private int winCount;
    private int loseCount;
    private int tieCount;
    private int winStreak;
    private int loseStreak;
    private int tieStreak;
    private double bestScore;
    private double worstScore;
    private int longuestWinStreak;
    private int longuestLoseStreak;
    private int longuestTieStreak;
    private Date bestScoreDate;
    private Date worstScoreDate;
    private Date longuestWinStreakDate;
    private Date longuestLoseStreakDate;
    private Date longuestTieStreakDate;
    @ManyToOne
    @JoinColumn(name="tournamentId")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name="playerId")
    private Player player;

    protected Stats() {}

    public Stats(Player player, Tournament tournament) {
        this.player = player;
        this.tournament = tournament;
        this.score = 1000;
        this.worstScore = this.score;
    }

    public Long getStatsId() {
        return statsId;
    }

    public void setStatsId(Long statsId) {
        this.statsId = statsId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        setBestScore(Math.max(this.score,this.bestScore));
        setWorstScore(Math.min(this.score, this.worstScore));
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public void addWin() {
        gameCount++;
        winCount++;
        setWinStreak(winStreak + 1);
        loseStreak = 0;
        tieStreak = 0;
    }

    public void addLose() {
        gameCount++;
        loseCount++;
        setLoseStreak(loseStreak + 1);
        winStreak = 0;
        tieStreak = 0;
    }

    public void addTie() {
        gameCount++;
        tieCount++;
        setTieStreak(tieStreak + 1);
        winStreak = 0;
        loseStreak = 0;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public int getTieCount() {
        return tieCount;
    }

    public void setTieCount(int tieCount) {
        this.tieCount = tieCount;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
        setLonguestWinStreak(Math.max(this.winStreak, this.longuestWinStreak));
    }

    public int getLoseStreak() {
        return loseStreak;
    }

    public void setLoseStreak(int loseStreak) {
        this.loseStreak = loseStreak;
        setLonguestLoseStreak(Math.max(this.loseStreak, this.longuestLoseStreak));
    }

    public int getTieStreak() {
        return tieStreak;
    }

    public void setTieStreak(int tieStreak) {
        this.tieStreak = tieStreak;
        setLonguestTieStreak(Math.max(this.tieStreak, this.longuestTieStreak));
    }

    public double getBestScore() {
        return bestScore;
    }

    public void setBestScore(double bestScore) {
        if(bestScore >= this.bestScore)
            this.bestScoreDate = new Date();
        this.bestScore = bestScore;}

    public double getWorstScore() {
        return worstScore;
    }

    public void setWorstScore(double worstScore) {
        if(worstScore >= this.worstScore)
            this.worstScoreDate = new Date();
        this.worstScore = worstScore;
    }

    public int getLonguestWinStreak() {
        return longuestWinStreak;
    }

    public void setLonguestWinStreak(int longuestWinStreak) {
        if(longuestWinStreak >= this.longuestWinStreak)
            this.longuestWinStreakDate = new Date();
        this.longuestWinStreak = longuestWinStreak;
    }

    public int getLonguestLoseStreak() {
        return longuestLoseStreak;
    }

    public void setLonguestLoseStreak(int longuestLoseStreak) {
        if(longuestLoseStreak >= this.longuestLoseStreak)
            this.longuestLoseStreakDate = new Date();
        this.longuestLoseStreak = longuestLoseStreak;
    }

    public int getLonguestTieStreak() {return longuestTieStreak;}

    public void setLonguestTieStreak(int longuestTieStreak) {
        if(longuestTieStreak >= this.longuestTieStreak)
            this.longuestTieStreakDate = new Date();
        this.longuestTieStreak = longuestTieStreak;

    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getBestScoreDate() {
        return bestScoreDate;
    }

    public void setBestScoreDate(Date bestScoreDate) {
        this.bestScoreDate = bestScoreDate;
    }

    public Date getWorstScoreDate() {
        return worstScoreDate;
    }

    public void setWorstScoreDate(Date worstScoreDate) {
        this.worstScoreDate = worstScoreDate;
    }

    public Date getLonguestWinStreakDate() {
        return longuestWinStreakDate;
    }

    public void setLonguestWinStreakDate(Date longuestWinStreakDate) {
        this.longuestWinStreakDate = longuestWinStreakDate;
    }

    public Date getLonguestLoseStreakDate() {
        return longuestLoseStreakDate;
    }

    public void setLonguestLoseStreakDate(Date longuestLoseStreakDate) {
        this.longuestLoseStreakDate = longuestLoseStreakDate;
    }

    public Date getLonguestTieStreakDate() {
        return longuestTieStreakDate;
    }

    public void setLonguestTieStreakDate(Date longuestTieStreakDate) {
        this.longuestTieStreakDate = longuestTieStreakDate;
    }
}
