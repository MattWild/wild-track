package application.database;

import java.sql.SQLException;

public abstract class DataController {
	
	protected DatabaseServer db; 
	
	protected void initSQLDB(String ipAddress, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		db = new SQLDB(ipAddress, user, pass, dbName);
	}
	
	protected void initOracleDB(String ipAddress, String user, String pass, Integer port, String sid, Boolean useSID) throws SQLException, ClassNotFoundException {
		db = new OracleDB(ipAddress, user, pass, port, sid, useSID);
	}
	
	public void close() throws SQLException {
		db.disconnect();
	}

}
