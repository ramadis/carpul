package ar.edu.itba.paw.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.itba.paw.webapp.auth.Provider;
import ar.edu.itba.paw.webapp.auth.Service;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class WebAuth extends WebSecurityConfigurerAdapter {

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
				.accessDeniedPage("/404")
			.and().rememberMe()
				.rememberMeParameter("remember")
				.key("secret key. sh.")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(60))
				.userDetailsService(userDetailsService)
			.and().csrf()
				.disable()
			;
	}
	
	@Override
	public void configure(final WebSecurity http) throws Exception {
		http.ignoring()
			.antMatchers("/styles/**", "/scripts/**", "/images/**", "/favicon.ico", "/static/**", "/403");
	}
}