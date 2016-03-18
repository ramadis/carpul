package ar.edu.itba.paw.webapp.config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;

@ComponentScan({
		"ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.services",
		"ar.edu.itba.paw.persistence"
})
@EnableWebMvc
@Configuration
public class WebConfig {

	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(JDBCDriver.class);
		dataSource.setUrl("jdbc:hsqldb:mem:paw");
		dataSource.setUsername("hq");
		dataSource.setPassword("");

		return dataSource;
	}
}
