package ar.edu.itba.paw.webapp.config;

import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;



@ComponentScan({
		"ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.services",
		"ar.edu.itba.paw.persistence"
})
@EnableWebMvc
@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Value("classpath:schema.sql")
	private Resource schemaSql;
	
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
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
		factoryBean.setDataSource(dataSource());
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		
		// TODO: Si ponen esto en prod, hay tabla!!!
		properties.setProperty("hibernate.show_sql", "true");
		
		properties.setProperty("format_sql", "true");
		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
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
	
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
//		final DataSourceInitializer dsi = new DataSourceInitializer();
//		dsi.setDataSource(ds);
//		dsi.setDatabasePopulator(databasePopulator());
//		return dsi;
//	}
//	
//	private DatabasePopulator databasePopulator() {
//		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
//		dbp.addScript(schemaSql);
//		return dbp;
//	}

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
