package application.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.database.DataController;
import application.database.Queries;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;

public class EnvironmentDatabaseService extends DataController implements DataService {
	
	private Component target;
	
	@Override
	public void setTarget(Object centralServices) throws Exception {
		this.target = (Component) centralServices;
		
		Server targetServer = target.getParent();
		
		try {
			if (targetServer.getIsSQL()) {
				initSQLDB(targetServer.getIp(), target.getUser(), target.getPass(), "CentralServices");
			} else {
				System.out.println(targetServer.getIp() + " "+ target.getUser() + " "+ target.getPass() + " " + targetServer.getPort() + " " + targetServer.getSID() + " " + targetServer.usesSID());
				initOracleDB(targetServer.getIp(), target.getUser(), target.getPass(), targetServer.getPort(), targetServer.getSID(), targetServer.usesSID());
			}
		} catch (ClassNotFoundException | SQLException e) {
			this.target = null;
			throw e;
		}
	}
	
	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public List<List<Object>> getData() throws SQLException {
		PreparedStatement stmt = null;
		List<List<Object>> newValues = new ArrayList<List<Object>>();
		ResultSet set = null;
		
		stmt = db.generatePreparedSatement(Queries.getMetersDataQuery(db.isSQL()));
		set = stmt.executeQuery();
		
		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			record.add(TableType.Meters);
			
			for (int i = 0 ; i < set.getMetaData().getColumnCount(); i++) {
				record.add(set.getObject(i+1));
			}
			newValues.add(record);
		}
			
		stmt = db.generatePreparedSatement(Queries.getRoutersDataQuery(db.isSQL()));
		set = stmt.executeQuery();

		while (set.next()) {
			List<Object> record = new ArrayList<Object>();
			record.add(TableType.Routers);
			
			for (int i = 0 ; i < set.getMetaData().getColumnCount(); i++) {
				record.add(set.getObject(i+1));
			}
			newValues.add(record);
		}
		
		return newValues;
	}
	
	@Override 
	public boolean isValid() {
		return (target != null);
	}
}
