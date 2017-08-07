package calc.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author danny
 */
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenInterceptor.class);
    
    @Autowired
    private JWTVerifier jwtVerifier;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.trace("JwtTokenInterceptor pre-handling request: {}", request.getRequestURI());
        
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        
        // check if the method should be secured
        if (method.isAnnotationPresent(Secured.class)) {
            logger.debug("@Secured method for URI: {}", request.getRequestURI());
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                String token = authHeader.substring(7);
                logger.debug("Authorization Bearer token: {}", token);
                
                try {
                    DecodedJWT jwt = jwtVerifier.verify(token);
                    logger.debug("JWToken verified");
                } catch (JWTVerificationException ve) {
                    logger.warn("JWToken verification failed: {}", ve.getMessage());
                    
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(ve.getMessage());
                    return false;
                }
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Invalid 'Authorization' header");
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception excptn) throws Exception {
        
    }
    
}
