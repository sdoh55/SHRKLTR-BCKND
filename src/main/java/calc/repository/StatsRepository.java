package calc.repository;

import calc.entity.Match;
import calc.entity.Player;
import calc.entity.Stats;
import calc.entity.Tournament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
    List<Stats> findByTournament(Tournament tournament);
    List<Stats> findByPlayer(Player player);
    List<Stats> findByPlayerId(Long playerId);
    public Stats findByPlayerAndTournament(@Param("playerId") Long playerId,@Param("tournamentName") String tournamentId);
}