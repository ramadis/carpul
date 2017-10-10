package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.auth.Service;
import org.slf4j.Logger;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class WebAuth extends WebSecurityConfigurerAdapter {

    static Logger logger = LoggerFactory.getLogger(WebConfig.class);
    
	@Autowired
	Provider authProvider;
	
	@Autowired
	Service userDetailsService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authenticationProvider(authProvider)
			.userDetailsService(userDetailsService)
			.sessionManagement()
			.invalidSessionUrl("/login")
			.and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/user").anonymous()
				.antMatchers("/**").authenticated()
			.and().formLogin()
				.loginPage("/login")
				//.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/", false)
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().rememberMe()
				.rememberMeParameter("j_rememberme")
				.key("this shouldn't be under version control, 12factorize it!")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(60))
				.userDetailsService(userDetailsService)
			.and().csrf()
				.disable()
			;
		logger.debug("Spring Security configured, up 'n running");
	}
	
	@Override
	public void configure(final WebSecurity http) throws Exception {
		http.ignoring()
			.antMatchers("/styles/**", "/scripts/**", "/images/**", "/favicon.ico", "/static/**", "/403");
	}
}