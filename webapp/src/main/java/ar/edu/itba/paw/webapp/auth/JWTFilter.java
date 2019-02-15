package ar.edu.itba.paw.webapp.auth;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
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
    @Autowired
    private UserDetailsService detailsService;

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
        	
        	String secret = "MIIEogIBAAKCAQEAgfTnl0hhUTZTyfNaB3IVCOeSFeKFPx1qQXvEC0kQrsk1WvXh\n" + 
	        		"za2VPQ/YzbUuG8KAj7CuLOBFMS+PSQqsige8oSk/DvtdYuVdrLTPRViV8VNyFkTo\n" + 
	        		"I4AohCLZ6RtBMTAc9cAryBXtdiXyDSHG+QoF7YAeYHqIWbLyncKPzEBTR5HdIvbt\n" + 
	        		"ocyn3o4fmHlJzTm+tdA5dclkZxcqiqGGPL1sF94iY2QYOXFPxKZz8Wxygy6Jxi/O\n" + 
	        		"7D+IX6VPxbF75dpAWi/gil1KGJsCrlYzyLylvI7x74sI5Btt2sbvnucBMrsPz/Jz\n" + 
	        		"zE5ZeaVYDrtK7M5SYF+oTHfSp2rSkQgm1Nx7DwIDAQABAoIBAFxgMpMFvy9UN8EQ\n" + 
	        		"u0kZnbwjp2LpDqArj5EWoecHu9USb1vF9gkWv0OVVR38ENdorav2AgkiLezliqt3\n" + 
	        		"6+d90maYhBtpMBuRQMH4EHGRo5vUHf+YYY6Clco/X7hkSDXnpl5FiJmEhLP8Cw+s\n" + 
	        		"l9wEdOw6qt0XnH5uRik/2KlufAQdtpIfofoDp93Nj95Lv10Ug6IE+G/zCGT7rauA\n" + 
	        		"ErwfZDz06bsisKjaFjA6ifeKLgxc94ZaljZRC/EO2ds5t9GUOXtftb9kFn5f664A\n" + 
	        		"kQZaiHKkUvKKweI6b8jGwKoxfDyArUd3WwmBWRe24aDD9vLstIhDxgBG+TxJ1bQ4\n" + 
	        		"FhIj1uECgYEAvkPtSOZh5jozq3yw8jFtHbzfy93ox+dkpeD8HvADryU4MH7VbOoq\n" + 
	        		"Vp8pg/MqDrb5+G5L0h7PvytDS6ZWlbWYoDkHthSQQt2kTHClbAsJ9BzIHYhxKUjp\n" + 
	        		"Y2/iqC+Fc4gZicNkJd6YUFHsTqEsXAxHrBuMCjkOQRrLdACAvrgTdhECgYEArtr1\n" + 
	        		"gg75vXaJUc1M5aAY7Av0h9V7o2JmvVAlIr0nYQJQiF2d/FzilCxp5SO1jsjgBc9C\n" + 
	        		"cZLEX8l/9IPsBm74BUz/YmQeKXVMxLoi70Ikt7IMxsqiVjCn3bNr8JqI+uLkg05b\n" + 
	        		"5gHSZj2APZ564dklzBlQG3T/pKqHGiAbSjpDPx8CgYBo7HK1af8BBIWsAyZdWW5w\n" + 
	        		"INFVzrlpTCzP4UOIGBogAyl6bOdrmbLhdAJj9rvxw2wUWVbMuBsetE/3LvK+updB\n" + 
	        		"fFHJUou/pBTP5qsC6w+BJEIjLzQgbUnvgSsATryMWriipTLcQ2XBnXvwXKdm14rK\n" + 
	        		"2aIfnRlEQ++AuCpHgqGQ0QKBgF0Hed98gDrhOiYfiG4TQ68ynSM33dQLooXOgjN9\n" + 
	        		"V9oxFQ+4XznJYXNcPer/mLUjAq2Um9E5OOmNl1pMZeL2X1bSpH2Artt34SQ5tCR/\n" + 
	        		"mHFzdYC+hqg8stMthpw4W/C/jrydkB9LkNl8lyIxk0dd9NImFYzGLs05PTAVLO/N\n" + 
	        		"F8IZAoGAJBqPwIRhSjWZukDd5bbznZL0jUurmVcCcMFn+OhmjXwox2PX1q1/H4st\n" + 
	        		"9zqsDIwFdeWinegPVo0Oh1UYjOuSRS8nlwJ1W/WFKi+Bs4iXM32vNYzHiCOrmarR\n" + 
	        		"KqRY6Cbrl9GJpOzoD2daB7bMY4MW2sC80ihAxKOjteIWP6h8pUY=";
        	
        	JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime() // the JWT must have an expiration time
                    .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                    .setRequireSubject() // the JWT must have a subject claim
                    .setExpectedIssuer("Carpul") // whom the JWT needs to have been issued by
                    .setExpectedAudience("Users") // to whom the JWT is intended for
                    .setVerificationKey(new HmacKey(secret.getBytes())) // verify the signature with the public key
                    .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                            new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
                                    AlgorithmIdentifiers.RSA_USING_SHA256))
                    .build(); // create the JwtConsumer instance
        	
        	try {
	        	JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
	        	String username = jwtClaims.getSubject();
	        	UserDetails details = detailsService.loadUserByUsername(username);
	            return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword());
        	} catch (Throwable e) { return null; }
        	
        	
        }
        return null;
    }

}
