package calc.repository;

import calc.auth.AuthToken;
import calc.entity.Match;
import calc.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clementperez on 10/9/16.
 */
public interface AuthTokenRepository  extends CrudRepository<AuthToken, Long> {
    User findUserIdByToken(String token);
}
