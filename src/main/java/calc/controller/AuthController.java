package calc.controller;

import calc.DTO.TokenDTO;
import calc.property.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danny
 */
@RestController
public class AuthController {
    @Autowired
    private JwtProperties jwtProperties;
    
    @Autowired
    private Algorithm algorithm;
    
    private long tokenExpirationMillis;
    
    @PostConstruct
    private void setUp() {
        tokenExpirationMillis = jwtProperties.getTokenExpirationMinutes() * 60 * 1000;
    }
    
    @RequestMapping(value = "/auth/token", method = RequestMethod.GET)
    public TokenDTO getToken() {
        Date now = new Date();
        
        String jwt = JWT.create()
            .withIssuer(jwtProperties.getIss())
            .withIssuedAt(now)
            .withExpiresAt(new Date(now.getTime() + tokenExpirationMillis))
            .sign(algorithm);
        
        return new TokenDTO(jwt);
    }
}
