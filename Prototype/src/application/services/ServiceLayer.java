package application.services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import application.Main;
import application.objects.entities.Component;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;
import application.objects.entities.Component.ComponentType;

public class ServiceLayer {
	
	private Main main;
	
	private List<DataService> deviceServices;
	private List<DataService> componentServices;
	
	
	public ServiceLayer(Main main) {
		this.main = main;
		
		deviceServices = new ArrayList<DataService>();
		deviceServices.add(new EnvironmentDatabaseService());
		
		componentServices = new ArrayList<DataService>();
		componentServices.add(new ComponentRequestService());
		componentServices.add(new EnvironmentCCService());
	}
	
	public void processDeviceServices(Environment environment) {
		if (environment.getCentralServices() != null)
			for (DataService service : deviceServices) {
				try {
					service.setTarget(environment.getCentralServices());
					
					if (service.isValid()) {
						Map<TableType, List<List<Object>>> recordsMap = new HashMap<TableType, List<List<Object>>>();
						
						for (List<Object> record : service.getData()) {
							TableType type = (TableType) record.get(0);
							record.remove(0);
							
							if (recordsMap.get(type) == null) recordsMap.put(type, new ArrayList<List<Object>>());
							recordsMap.get(type).add(record);
						}
						
						for (TableType type : recordsMap.keySet()) {
							main.getMainDBController().updateTableFromEnvironment(type, recordsMap.get(type));
						}
					}
				} catch (Exception e) {
					String errorDetail = "";
					
					if (e instanceof SQLException)
						errorDetail = ", sql query or queries failed";
					
					main.errorHandle("Could not retrieve device data from " + environment.getName() + errorDetail,e.getMessage());
				}
			}
	}
	
	public void processComponentServices(Environment environment){
		for (Server server : environment.getServers()) {
			for (Component component : server.getComponents()) {
				for (DataService service : componentServices) {
					try {
						service.setTarget(component);
						
						if (service.isValid()) {
							main.getMainDBController().updateComponents(service.getData());
						}
					} catch (Exception e) {
						String errorDetail = "";
						if (e instanceof SQLException)
							errorDetail = ", sql query or queries failed";
						else if (e instanceof RemoteException || e instanceof ServiceException) 
							errorDetail = ", component web service for " + component.getType().toString() + " not responding or failed";
						
						main.errorHandle("Could not retrieve device data from " + environment.getName() + errorDetail,e.getMessage());
					}
				}
			}
		}
	}

}
