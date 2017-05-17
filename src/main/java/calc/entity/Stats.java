package calc.entity;

import javax.persistence.*;

/**
 * Created by clementperez on 9/13/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Stats.findByUserId", query = "SELECT s FROM Stats s WHERE s.user.userId = ?1"),
        @NamedQuery(name = "Stats.findByUserAndTournament", query = "SELECT s FROM Stats s WHERE s.user.userId = ?1 AND s.tournament.name = ?2")
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
    @ManyToOne
    @JoinColumn(name="tournamentId")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    protected Stats() {}

    public Stats(User user, Tournament tournament) {
        this.user = user;
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

    public void setBestScore(double bestScore) { this.bestScore = bestScore;}

    public double getWorstScore() {
        return worstScore;
    }

    public void setWorstScore(double worstScore) {
        this.worstScore = worstScore;
    }

    public int getLonguestWinStreak() {
        return longuestWinStreak;
    }

    public void setLonguestWinStreak(int longuestWinStreak) {
        this.longuestWinStreak = longuestWinStreak;
    }

    public int getLonguestLoseStreak() {
        return longuestLoseStreak;
    }

    public void setLonguestLoseStreak(int longuestLoseStreak) {
        this.longuestLoseStreak = longuestLoseStreak;
    }

    public int getLonguestTieStreak() {return longuestTieStreak;}

    public void setLonguestTieStreak(int longuestTieStreak) {this.longuestTieStreak = longuestTieStreak; }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
