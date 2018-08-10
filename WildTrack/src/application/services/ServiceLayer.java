package application.services;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import application.Main;
import application.objects.environment.Component;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import application.objects.hardware.Device.DeviceType;

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
		//System.out.println(environment.getName());
		if (environment.getCentralServices() != null)
			for (DataService service : deviceServices) {
				try {
					long start = System.currentTimeMillis();
					long end = System.currentTimeMillis();
					System.out.println("Processing Device Service : " + environment.getName());
					System.out.println("Saving");
					start = System.currentTimeMillis();
					main.getObjectLayer().saveEnvironment(environment);
					end = System.currentTimeMillis();
					System.out.println(end - start);
					service.setTarget(environment.getCentralServices());
					
					if (service.isValid()) {
						System.out.println("Clearing relationships DB");
						start = System.currentTimeMillis();
						main.getDataLayer().clearEnvironRelationships(environment);
						end = System.currentTimeMillis();
						System.out.println(end - start);
						Map<DeviceType, List<List<Object>>> recordsMap = new HashMap<DeviceType, List<List<Object>>>();
						
						System.out.println("Retrieving and setting records");
						start = System.currentTimeMillis();
						for (List<Object> record : service.getData()) {
							DeviceType type = (DeviceType) record.get(0);
							record.remove(0);
							
							if (recordsMap.get(type) == null) recordsMap.put(type, new ArrayList<List<Object>>());
							recordsMap.get(type).add(record);
						}
						end = System.currentTimeMillis();
						System.out.println(end - start);
						
						for (DeviceType type : recordsMap.keySet()) {
							System.out.println("Saving devices " + type.toString());
							start = System.currentTimeMillis();
							main.getObjectLayer().saveDevices(type);
							end = System.currentTimeMillis();
							System.out.println(end - start);
							System.out.println("Loading");
							start = System.currentTimeMillis();
							main.getDataLayer().updateDevicesFromService(type, recordsMap.get(type));
							end = System.currentTimeMillis();
							System.out.println(end - start);
						}
						
						System.out.println("\n");
					}
				} catch (Exception e) {
					String errorDetail = "";
					
					if (e instanceof SQLException)
						errorDetail = ", sql query or queries failed";
					
					main.errorHandle("Could not retrieve device data from " + environment.getName() + errorDetail,e.getMessage());
				}
			}
	}
	
	public void processComponentServices(Environment environment) {
		//try {
			//main.getObjectLayer().saveEnvironment(environment);
			for (Server server : environment.getServers()) {
				for (Component component : server.getComponents()) {
					for (DataService service : componentServices) {
						try {
							service.setTarget(component);
							
							if (service.isValid()) {
								main.getDataLayer().updateComponentsFromService(service.getData());
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
		//} catch (SQLException e) {
			//main.errorHandle("Could not save " + environment.getName() + " prior to component service.", e.getMessage());
		//}
	}

}
