package application.data.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Database {
	
	protected String user;
	protected String pass;
	private Connection conn;
	
	public Database(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName(getDriverClassName());
		conn = DriverManager.getConnection(generateURL(), user, pass);
	}
	
	public void disconnect() throws SQLException {
		conn.close();
	}
	
	public PreparedStatement generatePreparedSatement(String query) throws SQLException {
		if (conn.isValid(5))
			return conn.prepareStatement(query);
		else 
			throw new SQLException("CONNECTION ERROR");
	}
	
	public abstract boolean isSQL();
	
	protected abstract String generateURL();
	
	protected abstract String getDriverClassName();
}
