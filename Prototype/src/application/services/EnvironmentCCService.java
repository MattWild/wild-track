package application.services;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.data.framework.DataController;
import application.data.framework.Queries;
import application.objects.environment.Component;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import application.objects.environment.Component.ComponentType;

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
						initOracleDB(targetServer.getIp(), targetDb.getUser(), targetDb.getPass(), targetServer.getPort(), targetServer.getSID(), targetServer.getUsesSid());
					}
				} catch (ClassNotFoundException | SQLException e) {
					this.target = null;
					throw e;
				}
			else
				target = null;
		} else {
			target = null;
		}
	}
	
	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public List<List<Object>> getData() throws SQLException {
		System.out.println(target.getType());
		
		int crc = 0;
		Object obj = executeQuery(Queries.ccCRCQuery(dbIsSQL())).get(0).get(0);
		
		if (obj instanceof Integer)
			crc = ((Integer) obj).intValue();
		
		if (obj instanceof BigDecimal) {
			System.out.println(obj);
			crc = ((BigDecimal) obj).intValue();
		}
		String version = (String) executeQuery(Queries.ccVersionInfoQuery(dbIsSQL())).get(0).get(0);

		target.getParent().getParent().setCRC(crc);
		target.setVersion(version);
		
		List<List<Object>> newValues = new ArrayList<List<Object>>();
		List<Object> record = new ArrayList<Object>();

		record.add(target.getId());
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
	
	@Override
	protected SQLException generateConnectionError() {
		return new SQLException("Database connection for environment : " + target.getParent().getParent().getName() + " was broken.");
	}
}
