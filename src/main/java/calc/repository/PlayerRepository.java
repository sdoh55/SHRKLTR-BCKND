package calc.repository;

import calc.entity.Player;
import calc.entity.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findByLastName(String lastName);
    Player findByUserName(String userName);
}
