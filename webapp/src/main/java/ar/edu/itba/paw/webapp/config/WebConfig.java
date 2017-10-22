package ar.edu.itba.paw.webapp.config;

import org.postgresql.Driver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;



@ComponentScan({
		"ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.services",
		"ar.edu.itba.paw.persistence"
})
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(5);
	 }
	 
	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		String[] i18n = new String[] { "/WEB-INF/i18n/messages",
									   "/WEB-INF/i18n/trips",
									   "/WEB-INF/i18n/search",
									   "/WEB-INF/i18n/review",
									   "/WEB-INF/i18n/home",
									   "/WEB-INF/i18n/history",
									   "/WEB-INF/i18n/error",
									   "/WEB-INF/i18n/common",
									   "/WEB-INF/i18n/user"};
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(i18n);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Driver.class);
//		dataSource.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2017b-6");
//		dataSource.setUsername("paw-2017b-6");
//		dataSource.setPassword("giCaed7e");
		dataSource.setUrl("jdbc:postgresql:pawdb");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;
	}
}
