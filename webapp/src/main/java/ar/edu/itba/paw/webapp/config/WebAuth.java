package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.itba.paw.webapp.auth.JWTFilter;
import ar.edu.itba.paw.webapp.auth.JWTLogin;
import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.auth.Service;


@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.config"})
public class WebAuth extends WebSecurityConfigurerAdapter {
    private final static Logger console = LoggerFactory.getLogger(WebAuth.class);

//	@Autowired
//	private Provider authProvider;

	@Autowired
	private Service userDetailsService;
	
	@Autowired
    private CORSFilter enableCors;
	
    @Autowired
    private JWTLogin jwtLogin;

    @Autowired
    private JWTFilter jwtFilter;
    
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http
			.userDetailsService(userDetailsService)
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
			.and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/users").anonymous()    // Register
	            .antMatchers(HttpMethod.POST, "/api/login").anonymous()    // Login
				.antMatchers("/api/search**").permitAll()
			.and().authorizeRequests()
				.anyRequest()
				.authenticated()
			.and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/api/login")
                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) -> response.sendError(HttpStatus.UNAUTHORIZED.value()))
                .successHandler(jwtLogin)
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
			.and().csrf().disable()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(enableCors, ChannelProcessingFilter.class);
		;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }


    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(userDetailsService);
        return auth;
    }
    
    @Bean(name = "JWTSecretKey")
    public RsaJsonWebKey getJWTSecretKey() throws JoseException {
        return RsaJwkGenerator.generateJwk(2048);
    }
    
	@Override
	public void configure(final WebSecurity http) throws Exception {
		http.ignoring()
		.antMatchers("/styles/**", "/scripts/**", "/images/**", "/favicon.ico", "/static/**", "/403");
	}
}
//
//}
