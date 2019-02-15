package ar.edu.itba.paw.webapp.auth;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.config"})
public class JWTFilter extends GenericFilterBean {
    private final static Logger console = LoggerFactory.getLogger(JWTFilter.class);

    @Autowired
    private UserDetailsService detailsService;
    
    @Autowired
    @Qualifier("JWTSecretKey")
    private RsaJsonWebKey jwtSecretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {}

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (jwt != null) {
        	
        	JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime() // the JWT must have an expiration time
                    .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                    .setRequireSubject() // the JWT must have a subject claim
                    .setExpectedIssuer("Carpul") // whom the JWT needs to have been issued by
                    .setExpectedAudience("Users") // to whom the JWT is intended for
                    .setVerificationKey(jwtSecretKey.getPublicKey()) // verify the signature with the public key
                    .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                            new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
                                    AlgorithmIdentifiers.RSA_USING_SHA256))
                    .build(); // create the JwtConsumer instance
        	
        	try {
	        	JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt.replace("Bearer ", ""));
	        	String username = jwtClaims.getSubject();
	        	UserDetails details = detailsService.loadUserByUsername(username);
	            return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword());
        	} catch (Throwable e) {
        		console.error(e.getMessage());
        	}
        	
        	
        }
        return null;
    }

}
