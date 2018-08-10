package application.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.data.framework.DataController;
import application.data.framework.Queries;
import application.objects.environment.Component;
import application.objects.environment.Server;
import application.objects.hardware.Device.DeviceType;

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
				initOracleDB(targetServer.getIp(), target.getUser(), target.getPass(), targetServer.getPort(), targetServer.getSID(), targetServer.getUsesSid());
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
		List<List<Object>> newValues = new ArrayList<List<Object>>();
		List<List<Object>> tempValues;
		
		tempValues = executeQuery(Queries.getMetersDataQuery(dbIsSQL()));
		for (List<Object> record : tempValues) {
			record.add(0, DeviceType.METERS);
			record.add(target.getParent().getParent().getId());
		}
		newValues.addAll(tempValues);
		tempValues.clear();
		
		tempValues = executeQuery(Queries.getRoutersDataQuery(dbIsSQL()));
		for (List<Object> record : tempValues) {
			record.add(0, DeviceType.ROUTERS);	
			record.add(target.getParent().getParent().getId());
		}
		newValues.addAll(tempValues);
		
		return newValues;
	}
	
	@Override 
	public boolean isValid() {
		return (target != null);
	}
	
	@Override
	protected SQLException generateConnectionError() {
		return new SQLException("Database connection for environment : " + target.getParent().getParent().getName() + " was broken.");
	}
}
