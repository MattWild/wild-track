package application.data.framework;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataController {
	
	private Database db; 
	
	protected void initSQLDB(String ipAddress, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		db = new SQLDB(ipAddress, user, pass, dbName);
	}
	
	protected void initOracleDB(String ipAddress, String user, String pass, Integer port, String sid, Boolean useSID) throws SQLException, ClassNotFoundException {
		db = new OracleDB(ipAddress, user, pass, port, sid, useSID);
	}
	
	public void close() throws SQLException {
		db.disconnect();
	}
	
	protected void executeBatchUpdate(List<List<Object>> records, String stmtString) throws SQLException {
		try {
			PreparedStatement stmt = db.generatePreparedSatement(stmtString);
			
			for(List<Object> record : records) {
				for(int i = 0; i < record.size(); i++) {
					stmt.setObject(i+1, record.get(i));
				}
				
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			
			stmt.close();
		} catch (SQLException e) {
			if (e.getMessage().contains("CONNECTION ERROR"))
				throw generateConnectionError();
			else
				throw e; 
		}
	}
	
	public boolean dbIsSQL() {
		return db.isSQL();
	}
	
	protected List<List<Object>> executeQuery(String stmtString) throws SQLException {
		try {
			PreparedStatement stmt = db.generatePreparedSatement(stmtString);
			ResultSet set = stmt.executeQuery();
			
			ArrayList<List<Object>> results = new ArrayList<List<Object>>();
			
			int columnNum = set.getMetaData().getColumnCount();
			while (set.next()) {
				List<Object> fields = new ArrayList<Object>();
				for (int j = 0; j < columnNum; j++) {
					fields.add(set.getObject(j + 1));
				}
				results.add(fields);
			}
			stmt.close();
			
			return results;
		} catch (SQLException e) {
			if (e.getMessage().contains("CONNECTION ERROR"))
				throw generateConnectionError();
			else
				throw e; 
		}
	}
	
	protected List<List<Object>> executeQueryWithParam(List<Object> param, String stmtString) throws SQLException {
		try {
			PreparedStatement stmt = db.generatePreparedSatement(stmtString);
			
			for (int i = 0; i < param.size(); i++) {
				stmt.setObject(i+1, param.get(i));
			}
			
			ResultSet set = stmt.executeQuery();
			
			ArrayList<List<Object>> results = new ArrayList<List<Object>>();
			
			int columnNum = set.getMetaData().getColumnCount();
			while (set.next()) {
				List<Object> fields = new ArrayList<Object>();
				for (int j = 0; j < columnNum; j++) {
					fields.add(set.getObject(j + 1));
				}
				results.add(fields);
			}
			stmt.close();
			
			return results;
		} catch (SQLException e) {
			if (e.getMessage().contains("CONNECTION ERROR"))
				throw generateConnectionError();
			else
				throw e;
		}
	}
	
	protected int executeAddUpdate(List<Object> record, String stmtString) throws SQLException {
		try {
			PreparedStatement stmt = db.generatePreparedSatement(stmtString);
			
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			ResultSet set = stmt.executeQuery();
			
			int id = -1;
			while (set.next())
				id = set.getInt(1);
			
			stmt.close();
			return id;
		} catch (SQLException e) {
			if (e.getMessage().contains("CONNECTION ERROR"))
				throw generateConnectionError();
			else
				throw e;
		}
	}
	
	protected abstract SQLException generateConnectionError();

}
