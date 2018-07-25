package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.objects.entities.Component.ComponentType;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;
import javafx.scene.layout.Region;

public class MainDataController extends DataController {
	
	public MainDataController(String ipAddress, String user, String pass, String dbName) throws SQLException, ClassNotFoundException {
		initSQLDB(ipAddress, user, pass, dbName);
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
	
	public void updateTableFromTable(TableType type, List<List<Object>> values) throws SQLException {
		System.out.println(values);
		
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement("EXEC UpdateMetersFromApp ?,?,?,?");
			break;
			
		case Collectors:
			stmt = db.generatePreparedSatement("EXEC UpdateCollectorsFromApp ?,?,?,?,?,?,?,?");
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

	public List<Object> getDistinctColumn(TableType type, int indexOf) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			switch (indexOf) {
			case 0:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterLAN");
				break;
			
			case 1:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterName");
				break;
				
			case 2:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterType");
				break;
				
			case 3:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterNetworkID");
				break;
				
			case 4:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterLocation");
				break;
				
			case 5:
				stmt = db.generatePreparedSatement("EXEC DistinctMeterSocket");
				break;
			}
			break;
			
		case Collectors:
			switch (indexOf) {
			case 0:
				stmt = db.generatePreparedSatement("EXEC DistinctCollectorIP");
				break;
			
			case 1:
				stmt = db.generatePreparedSatement("EXEC DistinctCollectorRadios");
				break;
				
			case 2:
				stmt = db.generatePreparedSatement("EXEC DistinctCollectorApp");
				break;
				
			case 3:
				stmt = db.generatePreparedSatement("EXEC DistinctCollectorType");
				break;
				
			case 4:
				stmt = db.generatePreparedSatement("EXEC DistinctCollectorLocation");
				break;
			}
			break;
			
		case Routers:
			switch (indexOf) {
			case 0:
				stmt = db.generatePreparedSatement("EXEC DistinctRouterLAN");
				break;
			
			case 1:
				stmt = db.generatePreparedSatement("EXEC DistinctRouterNetworkID");
				break;
				
			case 2:
				stmt = db.generatePreparedSatement("EXEC DistinctRouterRadioType");
				break;
				
			case 3:
				stmt = db.generatePreparedSatement("EXEC DistinctRouterLocation");
				break;
			}
			break;
			
		case HANDevices:
			switch (indexOf) {
			case 0:
				stmt = db.generatePreparedSatement("EXEC DistinctHANDeviceUnitID");
				break;
			
			case 1:
				stmt = db.generatePreparedSatement("EXEC DistinctHANDeviceName");
				break;
				
			case 2:
				stmt = db.generatePreparedSatement("EXEC DistinctHANDeviceLocation");
				break;
			}
			break;
			
		case Sockets:
			switch (indexOf) {
			case 0:
				stmt = db.generatePreparedSatement("EXEC DistinctSocketID");
				break;
			
			case 1:
				stmt = db.generatePreparedSatement("EXEC DistinctSocketForm");
				break;
				
			case 2:
				stmt = db.generatePreparedSatement("EXEC DistinctSocketLoadNoLoad");
				break;
				
			case 3:
				stmt = db.generatePreparedSatement("EXEC DistinctSocketLocation");
				break;
			}
			break;
		}
		
		ResultSet set = stmt.executeQuery();
		List<Object> results = new ArrayList<Object>();
		
		while (set.next()) {
			results.add(set.getString(1));
		}
		
		return results;
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

	
	public int getEnvironmentID(int crc) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement(Queries.environmentIDfromCRC(true));
		stmt.setInt(1, crc);
		
		ResultSet set = stmt.executeQuery();
		set.next();
		
		return set.getInt(1);
	}

	public void clearEnvironRelationships() throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement(Queries.clearEnvironmentRelations(true));
			
		stmt.executeUpdate();
	}
	Region regio;
	public void add(TableType type, List<List<Object>> values) throws SQLException {
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
		
		System.out.println(values);
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

	public void updateEnvironmentFromApp(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateEnvironmentFromApp ?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
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
	
	public void updateComponentFromApp(List<List<Object>> records) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC UpdateComponentFromApp ?,?,?");
		
		for(List<Object> record : records) {
			for(int i = 0; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			}
			
			stmt.addBatch();
		}
		
		stmt.executeBatch();
	}
	
	public int addComponent(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddComponent ?,?");

		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(0));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}
	
	public void updateServerFromApp(List<List<Object>> records) throws SQLException {
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
	
	public int addServer(List<Object> values) throws SQLException {
		PreparedStatement stmt = db.generatePreparedSatement("EXEC AddServer ?");

		for(int i = 0; i < values.size(); i++) {
			stmt.setObject(i+1, values.get(0));
		}
		ResultSet set = stmt.executeQuery();
		
		int id = -1;
		while (set.next())
			id = set.getInt(1);
		
		return id;
	}


}
