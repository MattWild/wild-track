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

public class EnvironmentCCService extends DataController implements DataService {
	
	private Component target;
	
	@Override
	public void setTarget(Object component) throws Exception {
		Component commandCenter = (Component) component;
		if (commandCenter.getType() == ComponentType.COMMANDCENTER) {
			this.target = (Component) commandCenter;
			
			Server targetServer = null;
			Component targetDb = null;
			
			for (Server server : target.getParent().getParent().getServers())
				for (Component sub : server.getComponents())
					if (sub.getType() == ComponentType.CENTRALSERVICES) {
						targetServer = server;
						targetDb = sub;
					}
			if (targetServer != null)
				try {
					if (targetServer.getIsSQL()) {
						initSQLDB(targetServer.getIp(), targetDb.getUser(), targetDb.getPass(), "CentralServices");
					} else {
						initOracleDB(targetServer.getIp(), targetDb.getUser(), targetDb.getPass(), targetServer.getPort(), targetServer.getSID(), targetServer.usesSID());
					}
				} catch (ClassNotFoundException | SQLException e) {
					this.target = null;
					throw e;
				}
			else
				target = null;
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
		
		stmt = db.generatePreparedSatement(Queries.ccVersionInfoQuery(db.isSQL()));
		set = stmt.executeQuery();
		
		String version = null; 
		
		while (set.next()) {
			version = set.getString(1);
		}
		
		List<Object> record = new ArrayList<Object>();
		
		record.add(target.getId());
		target.setVersion(version);
		record.add(target.getVersion());
		record.add(target.getUser()); 
		record.add(target.getPass());
		
		newValues.add(record);
		return newValues;
	}
	
	@Override 
	public boolean isValid() {
		return (target != null);
	}
}
