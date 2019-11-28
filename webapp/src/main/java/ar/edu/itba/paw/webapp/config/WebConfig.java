package ar.edu.itba.paw.webapp.config;

import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;



@EnableTransactionManagement
@Configuration
@ComponentScan({
	"ar.edu.itba.paw.webapp.controllers",
	"ar.edu.itba.paw.services",
	"ar.edu.itba.paw.persistence"
})
public class WebConfig {
	
	@Value("classpath:schema.sql")
	private Resource schemaSql;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
		factoryBean.setDataSource(dataSource());
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		properties.setProperty("format_sql", "true");
		factoryBean.setJpaProperties(properties);
		
		return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	

    @Bean
    public Validator validator() {
        return Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        int maxUploadSize = 5 * 1024 * 1024; // 5MB
        multipartResolver.setMaxUploadSize(maxUploadSize);
        return multipartResolver;
    }

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Driver.class);
//		dataSource.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2017b-6");
//		dataSource.setUsername("paw-2017b-6");
//		dataSource.setPassword("giCaed7e");
		dataSource.setUrl("jdbc:postgresql://localhost:5433/pawdb");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;
	}
}
