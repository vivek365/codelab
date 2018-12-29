package com.codelab.configuration;

import org.flywaydb.core.Flyway;

public class FlyWayConfig extends Flyway {
	public void repairAndMigrate() {
		this.repair();
		this.migrate();
	}
}
