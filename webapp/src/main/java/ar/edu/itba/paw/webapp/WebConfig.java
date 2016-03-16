package ar.edu.itba.paw.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@ComponentScan({"ar.edu.itba.paw.webapp.controllers"})
@EnableWebMvc
@Configuration
public class WebConfig {
	
	public WebConfig() {
		System.out.println("Inicializando WebConfig...");
	}
	
	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("WEB-INF/jsp");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
