package application.database;

import java.sql.SQLException;

public abstract class DataController {
	
	protected DatabaseServer db; 
	
	public void initSQLDB(String ipAddress, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		db = new SQLDB(ipAddress, user, pass, dbName);
	}
	
	public void initOracleDB(String ipAddress, String user, String pass, Integer port, String sid, Boolean useSID) throws SQLException, ClassNotFoundException {
		db = new OracleDB(ipAddress, user, pass, port, sid, useSID);
	}
	
	public void close() throws SQLException {
		db.disconnect();
	}

}
