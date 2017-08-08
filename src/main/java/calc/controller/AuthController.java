package calc.controller;

import calc.DTO.TokenDTO;
import calc.DTO.TokenRequestDTO;
import calc.property.JwtProperties;
import calc.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private Algorithm algorithm;
    
    @Autowired
    private AuthService authService;
    
    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public TokenDTO getToken(@RequestBody TokenRequestDTO tokenRequest) {
        return authService.getToken(tokenRequest);
    }
}
