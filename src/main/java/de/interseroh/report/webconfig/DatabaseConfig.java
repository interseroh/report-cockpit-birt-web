package de.interseroh.report.webconfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("de.interseroh.report.auth")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.interseroh.report.auth", entityManagerFactoryRef = "reportcockpitEntityManagerFactory")
public class DatabaseConfig {

	@Value("${hibernate.dialect.reportcockpit:false}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql.reportcockpit:false}")
	private String hibernateShowSql;
	@Value("${hibernate.hbm2ddl.auto.reportcockpit:none}")
	private String hibernateHbm2ddlAuto;

	@Value("${jdbc.username.reportcockpit}")
	private String jdbcUsername;
	@Value("${jdbc.password.reportcockpit}")
	private String jdbcPassword;
	@Value("${jdbc.driver.classname.reportcockpit}")
	private String jdbcDriverClass;
	@Value("${jdbc.url.reportcockpit}")
	private String jdbcUrl;

	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource reportcockpitDataSource() {
		final HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(jdbcDriverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean reportcockpitEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(reportcockpitDataSource());
		entityManagerFactory.setPersistenceProviderClass(
				HibernatePersistenceProvider.class);
		entityManagerFactory.setPackagesToScan("de.interseroh.report.auth");
		entityManagerFactory
				.setJpaProperties(reportcockpitHibernateProperties());
		entityManagerFactory
				.setPersistenceUnitName("reportcockpitPersistenceUnit");

		return entityManagerFactory;
	}

	private Properties reportcockpitHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", hibernateShowSql);
		properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				reportcockpitEntityManagerFactory().getObject());
		return transactionManager;
	}

}
