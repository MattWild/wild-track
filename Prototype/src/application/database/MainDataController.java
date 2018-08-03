package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.objects.entities.Component.ComponentType;
import application.objects.entities.Collector;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;
import javafx.scene.layout.Region;

public class MainDataController extends DataController {
	
	private static final String IP_ADDRESS = "10.1.220.223";
	private static final String USERNAME = "EscalationsTeam";
	private static final String PASSWORD = "1Esc2ala!tions";
	private static final String DATABASE_NAME  = "EnvironmentManagement";
	
	public MainDataController() throws SQLException, ClassNotFoundException {
		initSQLDB(IP_ADDRESS, USERNAME, PASSWORD, DATABASE_NAME);
	}
	
	public List<Environment> loadEnvironments() throws SQLException {
		List<Environment> result = new ArrayList<Environment>();
		
		List<List<Object>> records = executeQuery("EXEC EnvironmentDetails");
		for (List<Object> record : records) {
			Environment environment = new Environment((Integer) record.get(0), (String) record.get(1));
			if (record.get(2) != null) {
				environment.setCollector(new Collector((Integer) record.get(2), null, null, null, null, null, null, null, null, null));
			}
			
			result.add(environment);
		}
		
		return result;
	}
	
	private void executeBatchUpdate(List<List<Object>> records, String stmtString) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement(stmtString);
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
		
		stmt.close();
	}
	
	private List<List<Object>> executeQuery(String stmtString) throws SQLException {
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
	}
	
	private int executeAddUpdate(List<Object> record, String stmtString) throws SQLException {
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
	}

	public List<List<Object>> getEnvironmentConnectionParameters() throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC EnvironmentConnectionParameters");
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
		
		return results;
	}

	public List<List<Object>> getEnvironmentsData() throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC EnvironmentDetails");
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
		
		return results;
	}

	public List<List<Object>> getServersByEnvironment(int environmentId) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC GetServersByEnvironment ?");
		
		stmt.setInt(1, environmentId);
		
		ResultSet set = stmt.executeQuery();
	
		List<List<Object>> results = new ArrayList<List<Object>>();
		int columnNum = set.getMetaData().getColumnCount();
		while (set.next()) {
			List<Object> details = new ArrayList<Object>();
			for (int j = 0; j < columnNum; j++) {
				details.add(set.getObject(j + 1));
			}
			results.add(details);
		}
		
		return results;
	}

	public List<List<Object>> getComponentsByServer(int serverId) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC GetComponentsByServer ?");
		stmt.setInt(1, serverId);
		
		ResultSet set = stmt.executeQuery();
	
		List<List<Object>> results = new ArrayList<List<Object>>();
		int columnNum = set.getMetaData().getColumnCount();
		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			for (int j = 0; j < columnNum; j++) {
				record.add(set.getObject(j + 1));
			}
			results.add(record);
		}
		
		return results;
	}
	
	public List<List<Object>> getVersionAliases() throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC GetVersionAliases");
		
		ResultSet set = stmt.executeQuery();
	
		List<List<Object>> results = new ArrayList<List<Object>>();
		int columnNum = set.getMetaData().getColumnCount();
		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			for (int j = 0; j < columnNum; j++) {
				record.add(set.getObject(j + 1));
			}
			results.add(record);
		}
		
		return results;
	}
	
	public List<List<Object>> getCheckpointsByEnvironment(int environmentId) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC GetCheckpointsByEnvironment ?");
		stmt.setInt(1, environmentId);
		
		ResultSet set = stmt.executeQuery();
	
		List<List<Object>> results = new ArrayList<List<Object>>();
		int columnNum = set.getMetaData().getColumnCount();
		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			for (int j = 0; j < columnNum; j++) {
				record.add(set.getObject(j + 1));
			}
			results.add(record);
		}
		
		return results;
	}

	public List<List<Object>> getTableData(TableType type) throws SQLException {
		PreparedStatement stmt = null;
		switch(type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC Main_Meters");
			break;
			
		case Collectors:
			stmt = db.generatePreparedSatement("EXEC Main_Collectors");
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement("EXEC Main_Routers");
			break;
			
		case HANDevices:
			stmt = db.generatePreparedSatement("EXEC Main_HANDevices");
			break;
			
		case Sockets:
			stmt = db.generatePreparedSatement("EXEC Main_Sockets");
			break;
		}
		
		
		ResultSet set = stmt.executeQuery();
		List<List<Object>> values = new ArrayList<List<Object>>();
		int columnNum = set.getMetaData().getColumnCount();
		while (set.next()) {
			List<Object> fields = new ArrayList<Object>();
			for (int j = 0; j < columnNum; j++) {
				fields.add(set.getObject(j + 1));
			}
			values.add(fields);
		}
		
		return values;
	}

	public void updateEnvironments(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateEnvironmentFromApp ?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void updateServers(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateServerFromApp ?,?,?,?,?,?,?,?,?,?,?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				System.out.println(record.get(i));
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void updateComponents(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateComponentFromApp ?,?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}
	
	public void updateVersionAliases(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateVersionAlias ?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}
	
	public void updateCheckpoints(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateCheckpointFromApp ?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void updateTableFromTable(TableType type, List<List<Object>> values) throws SQLException {
		System.out.println(values);
		
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC UpdateMetersFromApp ?,?,?,?");
			break;
			
		case Collectors:
			stmt = db.generatePreparedSatement("EXEC UpdateCollectorsFromApp ?,?,?,?,?,?,?,?,?,?");
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement("EXEC UpdateRoutersFromApp ?,?,?");
			break;
			
		case HANDevices:
			stmt = db.generatePreparedSatement("EXEC UpdateHanDevicesFromApp ?,?,?,?,?,?,?");
			break;
			
		case Sockets:
			stmt = db.generatePreparedSatement("EXEC UpdateSocketsFromApp ?,?,?,?,?");
			break;
		}
		
		Iterator<List<Object>> iter = values.iterator();
		
		while (iter.hasNext()) {
			List<Object> record = iter.next();
			
			for (int i = 0 ; i < record.size(); i++) {
				stmt.setObject(i + 1, record.get(i));
			}
			stmt.addBatch();
		} 
		
		stmt.executeBatch();
	}

	public void updateTableFromEnvironment(TableType type, List<List<Object>> values) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC UpdateMetersFromEnvironment ?,?,?,?,?");
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement("EXEC UpdateRoutersFromEnvironment ?,?,?,?");
			break;
			
		default:
			break;
		}
		Iterator<List<Object>> iter = values.iterator();
		
		while (iter.hasNext()) {
			List<Object> record = iter.next();
			
			for (int i = 0 ; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			stmt.addBatch();
		} 
		
		stmt.executeBatch();
	}
	
	public void clearEnvironRelationships() throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC ClearEnvironmentRelations");
			
		stmt.executeUpdate();
	}
	
	public int addEnvironment(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddEnvironment ?");
	
		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(0));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}

	public int addServer(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddServer ?,?");
	
		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(i));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}

	public int addComponent(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddComponent ?,?");
	
		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(i));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}
	
	public int addVersionAlias(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddVersionAlias ?,?");
	
		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(i));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}
	
	public int addCheckpoint(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddCheckpoint ?,?");
	
		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(i));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}

	public int addDevices(TableType type, List<Object> values) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC AddMeter ?,?,?,?");
			break;
			
		case Collectors:
			stmt = db.generatePreparedSatement("EXEC AddCollector ?,?,?,?,?,?,?");
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement("EXEC AddRouter ?,?,?");
			break;
			
		case HANDevices:
			stmt = db.generatePreparedSatement("EXEC AddHANDevice ?,?,?,?,?,?");
			break;
			
		case Sockets:
			stmt = db.generatePreparedSatement("EXEC AddSocket ?,?,?,?");
			break;
		}
		
		for (int i = 0 ; i < values.size(); i++) {
			stmt.setObject(i + 1, values.get(i));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}

	public void deleteEnvironments(List<Integer> ids) throws SQLException{	
		PreparedStatement stmt = db.generatePreparedSatement("EXEC DeleteEnvironment ?");
		
		for(Integer id : ids) {
			stmt.setInt(1, id);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void deleteServers(List<Integer> ids) throws SQLException{	
		PreparedStatement stmt = db.generatePreparedSatement("EXEC DeleteServer ?");
		
		for(Integer id : ids) {
			stmt.setInt(1, id);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void deleteComponents(List<Integer> ids) throws SQLException{	
		PreparedStatement stmt = db.generatePreparedSatement("EXEC DeleteComponent ?");
		
		for(Integer id : ids) {
			stmt.setInt(1, id);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}
	
	public void deleteVersionAliases(List<Integer> ids) throws SQLException{	
		PreparedStatement stmt = db.generatePreparedSatement("EXEC DeleteVersionAlias ?");
		
		for(Integer id : ids) {
			stmt.setInt(1, id);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}
	
	public void deleteCheckpoints(List<Integer> ids) throws SQLException{	
		PreparedStatement stmt = db.generatePreparedSatement("EXEC DeleteCheckpoint ?");
		
		for(Integer id : ids) {
			stmt.setInt(1, id);
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}

	public void delete(TableType type, List<Integer> ids) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC DeleteMeter ?");
			break;
			
		case Collectors:
			stmt = db.generatePreparedSatement("EXEC DeleteCollector ?");
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement("EXEC DeleteRouter ?");
			break;
			
		case HANDevices:
			stmt = db.generatePreparedSatement("EXEC DeleteHANDevice ?");
			break;
			
		case Sockets:
			stmt = db.generatePreparedSatement("EXEC DeleteSocket ?");
			break;
		}
		for(int identifier : ids) {
			stmt.setInt(1, identifier);
			stmt.addBatch();
		}
		stmt.executeBatch();
	}

}
