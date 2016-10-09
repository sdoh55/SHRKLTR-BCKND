package calc.repository;

import calc.entity.Outcome;
import calc.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by clementperez on 9/23/16.
 */
@Repository
public interface OutcomeRepository extends CrudRepository<Outcome, Long> {
    List<Outcome> findByPlayer(Player player);
    List<Outcome> findByPlayerId(Long playerId);
    List<Outcome> findByMatchId(Long matchId);
}
