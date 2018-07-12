package application.database;

import java.sql.SQLException;

public abstract class DataController {
	
	protected AppDatabase db; 
	
	public void initSQLDB(String ipAddress, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		db = new SQLDB(ipAddress, user, pass, dbName);
	}
	
	public void initOracleDB(String ipAddress, String user, String pass, String port, String sid, String useSID) throws SQLException, ClassNotFoundException {
		db = new OracleDB(ipAddress, user, pass, port, sid, useSID);
	}
	
	public void close() throws SQLException {
		db.disconnect();
	}

}