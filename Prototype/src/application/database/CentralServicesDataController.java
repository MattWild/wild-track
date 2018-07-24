package application.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.presentation.logic.DeviceGridController.TableType;

public class CentralServicesDataController extends DataController {
	
	Map<TableType, List<List<Object>>> stagedChanges;
	Map<TableType, List<String[]>> stagedNotes;
	
	public CentralServicesDataController() {
		stagedChanges = new HashMap<TableType, List<List<Object>>>();
		stagedNotes = new HashMap<TableType, List<String[]>>();
	}

	public List<List<Object>> retrieveData(TableType type) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement(Queries.getMetersDataQuery(db.isSQL()));
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement(Queries.getRoutersDataQuery(db.isSQL()));
			break;
		case Collectors:
			break;
		case HANDevices:
			break;
		case Sockets:
			break;
		default:
			break;
		}
		ResultSet set = stmt.executeQuery();
		List<List<Object>> newValues = new ArrayList<List<Object>>();

		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			
			for (int i = 0 ; i < set.getMetaData().getColumnCount(); i++) {
				record.add(set.getObject(i+1));
			}
			newValues.add(record);
		}
		
		return newValues;
	}
	
	/*public void updateData(TableType type, List<List<Object>> newValues) throws SQLException {
		PreparedStatement stmt = null;
		switch (type) {
		case Meters:
			stmt = db.generatePreparedSatement(Queries.updateEnvironmentMetersTable(db.isSQL()));
			break;
			
		case Routers:
			stmt = db.generatePreparedSatement(Queries.updateEnvironmentRoutersTable(db.isSQL()));
			break;
		}
		
		Iterator<List<Object>> iter = newValues.iterator();
		
		while (iter.hasNext()) {
			List<Object> record = iter.next();
			
			for (int i = 0 ; i < record.size(); i++) {
				stmt.setObject(i+1, record.get(i));
			};
			
			stmt.addBatch();
		} 
		
		stmt.executeBatch();
	}*/

	/*public void stageChange(Entry entry) {
		if (!stagedChanges.containsKey(entry.getType())) stagedChanges.put(entry.getType(), new ArrayList<List<Object>>());
		
		stagedChanges.get(entry.getType()).add(entry.getUpdateableValues());
	}*/
	
	/*public void pushChanges() throws SQLException {
		for (TableType type : stagedChanges.keySet()) {
			updateData(type, stagedChanges.get(type));
		}
		
		for (TableType type : stagedNotes.keySet()) {
			addNotes(type, stagedNotes.get(type));
		}
	}

	public void stageNote(String lanAddress, String text) {
		if (!stagedNotes.containsKey(TableType.Meters)) stagedNotes.put(TableType.Meters, new ArrayList<String[]>());
		
		stagedNotes.get(TableType.Meters).add(new String[] {lanAddress, text});
	}

	private void addNotes(TableType type, List<String[]> list) throws SQLException {
		PreparedStatement stmt = null;
		stmt = db.generatePreparedSatement(Queries.addNoteEnvironment(db.isSQL()));
		

		Iterator<String[]> iter = list.iterator();
		
		while (iter.hasNext()) {
			String[] record = iter.next();
			stmt.setString(1, (String) record[0]);
			stmt.setString(2, (String) record[1]);
			stmt.addBatch();
		} 
		
		stmt.executeBatch();
	}*/
}
