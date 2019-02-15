package ar.edu.itba.paw.webapp.auth;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.springframework.security.core.userdetails.User;

@Component
public class JWTLogin extends SavedRequestAwareAuthenticationSuccessHandler {
    private final static Logger console = LoggerFactory.getLogger(JWTLogin.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	try {
	    	User user = (User) authentication.getPrincipal();
	        
	    	
	        JwtClaims claims = new JwtClaims();
	        claims.setIssuer("Carpul");  // who creates the token and signs it
	        claims.setAudience("Users"); // to whom the token is intended to be sent
	        claims.setExpirationTimeMinutesInTheFuture(21600); // expire in 15 days
	        claims.setGeneratedJwtId(); // a unique identifier for the token
	        claims.setIssuedAtToNow();  // when the token was issued/created (now)
	        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
	        claims.setSubject(user.getUsername()); // the subject/principal is whom the token is about
	
	        JsonWebSignature jws = new JsonWebSignature();
	        
	        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
	
	        jws.setKey(rsaJsonWebKey.getPrivateKey());
	
	        
	        // The payload of the JWS is JSON content of the JWT Claims
	        jws.setPayload(claims.toJson());
	
	        // The JWT is signed using the private key
	        jws.setKey(rsaJsonWebKey.getPrivateKey());
	
	        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
	        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
	
	        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
	        // representation, which is a string consisting of three dot ('.') separated
	        // base64url-encoded parts in the form Header.Payload.Signature
	        // If you wanted to encrypt it, you can simply set this jwt as the payload
	        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
	        String jwt;
			jwt = jws.getCompactSerialization();
			response.addHeader("Authorization", "Bearer " + jwt);

    	} catch (JoseException e) {
			console.error(e.getMessage());
		}
    }


    // TODO: Maybe delete this method
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        return "/";
    }

}
