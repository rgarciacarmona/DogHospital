package db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import db.interfaces.DBManager;
import db.interfaces.DogManager;

public class SQLiteManager implements DBManager {

	private Connection c;
	private DogManager dog;

	public SQLiteManager() {
		super();
	}

	@Override
	public void connect() {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			this.c = DriverManager.getConnection("jdbc:sqlite:./db/dogs.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			// Create DogManager
			dog = new SQLiteDogManager(c);
			// We could initialize other managers here
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTables() {
		Statement stmt1;
		try {
			stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE dogs " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name   TEXT   NOT NULL, " + " breed  TEXT   NOT NULL, " + " weight FLOAT  NOT NULL, "
					+ " admissionDate DATE   NOT NULL, " + " releaseDate DATE)";
			stmt1.executeUpdate(sql1);
			stmt1.close();
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")) {
			} else {
				e.printStackTrace();
			}

		}

	}

	@Override
	public DogManager getDogManager() {
		return dog;
	}
}
