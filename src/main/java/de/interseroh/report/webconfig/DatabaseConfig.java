package de.interseroh.report.webconfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("de.interseroh.report.auth")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.interseroh.report.auth", entityManagerFactoryRef = "reportcockpitEntityManagerFactory")
public class DatabaseConfig {

	public static final String REPORTCOCKPIT_PERSISTENCE_UNIT = "reportcockpitPersistenceUnit";
	public static final String REPORTCOCKPIT_JPA_TX_MANAGER = "reportcockpitJpaTransactionManager";
	private static final String JDBC_PASSWORD_REPORTCOCKPIT = "jdbc.password.reportcockpit";
	private static final String JDBC_USERNAME_REPORTCOCKPIT = "jdbc.username.reportcockpit";
	private static final String JDBC_URL_REPORTCOCKPIT = "jdbc.url.reportcockpit";
	private static final String JDBC_DRIVER_CLASSNAME_REPORTCOCKPIT = "jdbc.driver.classname.reportcockpit";
	private static final String REPORTCOCKPIT_ENTITYMANAGER_PACKAGES_TO_SCAN = "de.interseroh.report.auth";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect.reportcockpit";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql.reportcockpit";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto.reportcockpit";

	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource reportcockpitDataSource() {
		final HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(
				env.getProperty(JDBC_DRIVER_CLASSNAME_REPORTCOCKPIT));
		dataSource.setJdbcUrl(env.getProperty(JDBC_URL_REPORTCOCKPIT));
		dataSource.setUsername(env.getProperty(JDBC_USERNAME_REPORTCOCKPIT));
		dataSource.setPassword(env.getProperty(JDBC_PASSWORD_REPORTCOCKPIT));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager reportcockpitJdbcTransactionManager() {
		return new DataSourceTransactionManager(reportcockpitDataSource());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean reportcockpitEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(reportcockpitDataSource());
		entityManagerFactory.setPersistenceProviderClass(
				HibernatePersistenceProvider.class);
		entityManagerFactory.setPackagesToScan(
				REPORTCOCKPIT_ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactory
				.setJpaProperties(reportcockpitHibernateProperties());
		entityManagerFactory
				.setPersistenceUnitName(REPORTCOCKPIT_PERSISTENCE_UNIT);

		return entityManagerFactory;
	}

	private Properties reportcockpitHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		return properties;
	}

	@Bean
	public JpaTransactionManager reportcockpitJpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				reportcockpitEntityManagerFactory().getObject());
		return transactionManager;
	}

}
