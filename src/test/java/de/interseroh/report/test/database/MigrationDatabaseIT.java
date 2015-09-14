package de.interseroh.report.test.database;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;

public class MigrationDatabaseIT {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		// Create the Flyway instance
		Flyway flyway = new Flyway();

		// Point it to the database
		flyway.setDataSource("jdbc:hsqldb:mem:testdb;sql.syntax_ora=true",
				"sa", null);

		// Start the migration
		flyway.migrate();
	}

}
