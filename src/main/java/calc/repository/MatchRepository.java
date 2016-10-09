package calc.repository;

import calc.entity.Match;
import calc.entity.Player;
import calc.entity.Stats;
import calc.entity.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByTournament(Tournament tournament);
    public List<Match> findByPlayerIdByTournamentName(@Param("playerId") Long playerId, @Param("tournamentName") String tournamentId);
    List<Match> findByTournamentName(String tournamentName);
    List<Match> findByPlayerId(Long playerId);
}