package de.interseroh.report.webconfig;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by idueppe on 27.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@PropertySources({ @PropertySource("classpath:config.properties"),
		@PropertySource(value = "classpath:config-${profile}.properties", ignoreResourceNotFound = true) })
public class DatabaseConfigTest {

	@Autowired
	@Qualifier("reportcockpitDataSource")
	private DataSource dataSource;

	@Test
	public void testReportcockpitDataSourceConfiguration() throws Exception {
		assertThat(dataSource, is(notNullValue()));
		assertThat(((HikariDataSource) dataSource).getJdbcUrl(),
				is("jdbc:hsqldb:mem:testdb;sql.syntax_ora=true"));
		assertThat(((HikariDataSource) dataSource).getUsername(), is("sa"));
		assertThat(((HikariDataSource) dataSource).getPassword(), is(""));
		assertThat(((HikariDataSource) dataSource).getDriverClassName(),
				is("org.hsqldb.jdbcDriver"));

	}
}