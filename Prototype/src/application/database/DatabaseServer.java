package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DatabaseServer {
	
	protected String user;
	protected String pass;
	private Connection conn;
	
	public DatabaseServer(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	public abstract String generateURL();
	
	protected abstract String getDriverClassName();
	
	public abstract boolean isSQL();
	
	public PreparedStatement generatePreparedSatement(String query) throws SQLException {
		return conn.prepareStatement(query);
	}
	
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName(getDriverClassName());
		conn = DriverManager.getConnection(generateURL(), user, pass);
	}
	
	public void disconnect() throws SQLException {
		conn.close();
	}
}
