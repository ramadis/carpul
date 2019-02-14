//package ar.edu.itba.paw.webapp.config;
//
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.access.channel.ChannelProcessingFilter;
//
//import ar.edu.itba.paw.webapp.auth.Provider;
//import ar.edu.itba.paw.webapp.auth.Service;
//
//
//@Configuration
//@EnableWebSecurity
//@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.config"})
//public class WebAuth extends WebSecurityConfigurerAdapter {
////
////	@Autowired
////	private Provider authProvider;
//
//	@Autowired
//	private Service userDetailsService;
//
//	@Override
//	protected void configure(final HttpSecurity http) throws Exception {
//		http
//			.userDetailsService(userDetailsService)
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and().authorizeRequests()
//			.and().authorizeRequests()
//				.antMatchers("/api/login").anonymous()
//				.antMatchers("/api/user/*").anonymous()
//				.antMatchers("/api/search**").permitAll()
//				.antMatchers("/api/*").permitAll() // TODO: Check this jaja
//			.and().authorizeRequests()
//				.anyRequest()
//				.authenticated()
//			.and().formLogin()
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .loginProcessingUrl("/api/login")
//			.and().logout()
//				.logoutUrl("/logout")
//				.logoutSuccessUrl("/login")
//			.and().exceptionHandling()
//				.accessDeniedPage("/404")
//			.and().addFilterBefore(enableCors, ChannelProcessingFilter.class);
//			.and().csrf().disable();
//	}
////
////	@Override
////	public void configure(final WebSecurity http) throws Exception {
////		http.ignoring()
////			.antMatchers("/styles/**", "/scripts/**", "/images/**", "/favicon.ico", "/static/**", "/403");
////	}
////}
