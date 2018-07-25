package application.object;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.database.CentralServicesDataController;
import application.database.DataController;
import application.objects.entities.Collector;
import application.objects.entities.CollectorComponent;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Entry;
import application.objects.entities.Environment;
import application.objects.entities.HANDevice;
import application.objects.entities.Meter;
import application.objects.entities.Router;
import application.objects.entities.Server;
import application.objects.entities.Socket;
import application.presentation.logic.DeviceGridController.TableType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ObjectLayer {
	
	private Map<TableType, ObservableList<Entry>> entries;
	private Map<TableType, List<Entry>> deleteDeviceList;
	private ObservableList<Environment> environments;
	private List<Environment> deleteEnvironmentList;
	private List<Environment> addEnvironmentList;
	private Map<Environment, List<Server>> deleteServerLists;
	private	Map<Environment, List<Server>> addServerLists;
	private Map<Server, List<Component>> deleteComponentLists;
	private Map<Server, List<Component>> addComponentLists;
	private Main main;
	
	public ObjectLayer(Main main) {
		this.main = main;
		entries = new HashMap<TableType, ObservableList<Entry>>();
		environments = FXCollections.observableArrayList();
		deleteEnvironmentList = new ArrayList<Environment>();
		addEnvironmentList = new ArrayList<Environment>();
		deleteServerLists =  new HashMap<Environment, List<Server>>();
		addServerLists =  new HashMap<Environment, List<Server>>();
		deleteComponentLists = new HashMap<Server, List<Component>>();
		addComponentLists = new HashMap<Server, List<Component>>();
		
		for (TableType type : TableType.values()) {
			ObservableList<Entry> list = FXCollections.observableArrayList();
			entries.put(type, list);
		}
		deleteDeviceList = new HashMap<TableType, List<Entry>>();
	}
	
	public void loadTable(TableType type) throws SQLException {
		entries.get(type).clear();
		for (List<Object> values : main.getMainDBController().getTableData(type)) {
			switch (type) {
			case Meters:
				entries.get(type).add(new Meter(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(Integer) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6), 
						(String) values.get(7), 
						(String) values.get(8)));
				break;
			case Collectors:
				entries.get(type).add(new Collector(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6), 
						(String) values.get(7)));
				break;
			case HANDevices:
				entries.get(type).add(new HANDevice(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6)));
				break;
			case Routers:
				entries.get(type).add(new Router(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(Integer) values.get(2), 
						(String) values.get(3),
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6)));
				break;
			case Sockets:
				entries.get(type).add(new Socket(((Integer) values.get(0)).intValue(), 
						(Integer) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4)));
				break;
			default:
				break;
			}
		}
	}

	public void loadEnvironments() {
		environments.clear();
		
		try {
			List<List<Object>> values = main.getMainDBController().getEnvironmentsData();
			
			for (List<Object> record : values) {
				Environment environment = new Environment((Integer) record.get(0), (String) record.get(1));
				if (record.get(2) != null) environment.setCollectorId((Integer) record.get(2));
				
				loadServersFromEnvironment(environment);
				
				environments.add(environment);
			}
		} catch (SQLException e) {
			main.errorHandle(e);
		}
	}
	
	public void loadCollectorComponent(CollectorComponent collectorComponent) {
		for (Entry entry : entries.get(TableType.Collectors)) {
			Collector collector = (Collector) entry;
			
			if (collectorComponent.getId() == collector.getId()) collectorComponent.setCollector(collector);
		}
	}

	public void loadServersFromEnvironment(Environment environment) throws SQLException {
		List<List<Object>> values = main.getMainDBController().getServersByEnvironment(environment.getId());
		
		for (List<Object> record : values) {
			Server server = new Server((Integer) record.get(0), environment);
			
			server.setName((String) record.get(1));
			server.setIp((String) record.get(2));
			server.setFqdn((String) record.get(3));
			server.setType((String) record.get(4));
			if (record.get(5) != null && (Boolean) record.get(5)) {
				server.setHasDB(true);
				server.setDBType((String) record.get(6));
				if (record.get(7) != null && (Boolean) record.get(7)) {
					server.setIsSQL(true);
				} else {
					server.setIsSQL(false);
					
					server.setSysUser((String) record.get(8));
					server.setSysPass((String) record.get(9));
					server.setPort((Integer) record.get(10));
					server.setSID((String) record.get(11));
					
					if (record.get(12) != null && (Boolean) record.get(12))
						server.indicateSID();
					else
						server.indicateServiceName();
				}
			} else {
				server.setHasDB(false);
			}
			
			loadComponentsFromServer(server);
			
			environment.addServer(server);
		}
	}
	
	public void loadComponentsFromServer(Server server) throws SQLException {
		List<List<Object>> componentDetails = main.getMainDBController().getComponentsByServer(server.getId());
		
		for(List<Object> componentRecord : componentDetails) {
			ComponentType type = null;
			switch (((Integer) componentRecord.get(1)).intValue()) {
			case 1:
				type = ComponentType.ABNT;
				break;
			case 2:
				type = ComponentType.ANSI;
				break;
			case 3:
				type = ComponentType.CAPABILTYSERVICES;
				break;
			case 4:
				type = ComponentType.CENTRALSERVICES;
				break;
			case 5:
				type = ComponentType.CM;
				break;
			case 6:
				type = ComponentType.COMMANDCENTER;
				break;
			case 7:
				type = ComponentType.GSIS;
				break;
			case 8:
				type = ComponentType.M2M;
				break;
			case 9:
				type = ComponentType.NMS;
				break;
			case 10:
				type = ComponentType.PANA;
				break;
			case 11:
				type = ComponentType.SBS;
				break;
			case 12:
				type = ComponentType.COLLECTOR;
				break;
			}
			
			Component component = new Component(type, (Integer) componentRecord.get(0), server);
			component.setVersion((String) componentRecord.get(2));
			component.setUser((String) componentRecord.get(3));
			component.setPass((String) componentRecord.get(4));
			
			server.addComponent(component);
		}
	}

	public ObservableList<Entry> getEntries(TableType type) {
		return entries.get(type);
	}
	
	public Entry createDevice(TableType type) {
		switch (type) {
		case Meters:
			return new Meter(-1, null, null, null, null, null, null, null, null);
		case Collectors:
			return new Collector(-1, null, null, null, null, null, null, null);
		case HANDevices:
			return new HANDevice(-1, null, null, null, null, null, null);
		case Routers:
			return new Router(-1, null, null, null, null, null, null);
		case Sockets:
			return new Socket(-1, null, null, null, null);
		default:
			return null;
		}
	}
	
	public void commitDevice(TableType type, Entry entry) {
		entries.get(type).addAll(entry);
	}
	
	public void deleteDevice(TableType type, Entry entry) {
		entries.get(type).remove(entry);
		
		if (deleteDeviceList.get(type) == null) deleteDeviceList.put(type, new ArrayList<Entry>());
		deleteDeviceList.get(type).add(entry);
	}
	
	public void saveTable(TableType type) throws SQLException {
		List<List<Object>> internalNewValues = new ArrayList<List<Object>>();
		List<List<Object>> internalNewAdds = new ArrayList<List<Object>>();
		//Map<Integer, List<List<Object>>> externalMap = new HashMap<Integer, List<List<Object>>>();
		
		for (Entry entry : entries.get(type)) {
			if (entry.isChanged()) {
				List<Object> internalRecord = new ArrayList<Object>();
				//List<Object> externalRecord = new ArrayList<Object>();
				//int crc = -1;
				
				switch (type) {
				case Collectors:
					Collector collector = (Collector) entry;
					
					internalRecord.add(collector.getId());
					internalRecord.add(collector.getIp());
					internalRecord.add(collector.getRadios());
					internalRecord.add(collector.getNetId());
					internalRecord.add(collector.getApp());
					internalRecord.add(collector.getCollectorType());
					internalRecord.add(collector.getLocation());
					internalRecord.add(collector.getNote());
					
					break;
				case HANDevices:
					HANDevice han = (HANDevice) entry;

					internalRecord.add(han.getId());
					internalRecord.add(han.getUnitId());
					internalRecord.add(han.getDeviceName());
					internalRecord.add(han.getInstall());
					internalRecord.add(han.getMAC());
					internalRecord.add(han.getLocation());
					internalRecord.add(han.getNote());
					break;
				case Meters:
					Meter meter = (Meter) entry;

					internalRecord.add(meter.getId());
					internalRecord.add(meter.getLAN());
					internalRecord.add(meter.getLocation());
					internalRecord.add(meter.getSocket());
					internalRecord.add(meter.getNote());
					
					/*System.out.println(internalRecord);
					
					crc = meter.getCRC();
					externalRecord.add(meter.getLAN());
					externalRecord.add(meter.getLocation());
					externalRecord.add(meter.getSocket());*/
					break;
				case Routers:
					Router router = (Router) entry;

					internalRecord.add(router.getId());
					internalRecord.add(router.getLAN());
					internalRecord.add(router.getLocation());
					internalRecord.add(router.getNote());
					
					/*crc = router.getCRC();
					externalRecord.add(router.getLAN());
					externalRecord.add(router.getLocation());*/
					break;
				case Sockets:
					Socket socket = (Socket) entry;
					
					internalRecord.add(socket.getId());
					internalRecord.add(socket.getIdProp());
					internalRecord.add(socket.getForm());
					internalRecord.add(socket.getNLoad());
					internalRecord.add(socket.getLocation());
					break;
				default:
					break;
				}
				
				if ((Integer) internalRecord.get(0) == -1) {
					internalRecord.remove(0);
					
					internalNewAdds.add(internalRecord);
				} else {
					if (type == TableType.Meters || type == TableType.Routers) internalRecord.remove(1);
					internalNewValues.add(internalRecord);
					
					/*if(!externalRecord.isEmpty()) {
						if (!externalMap.containsKey(crc))
							externalMap.put(crc, new ArrayList<List<Object>>());
							
						if (crc != -1) externalMap.get(crc).add(externalRecord);
					}*/
				}
			}
		}
		

		
		List<Integer> deleteIds = new ArrayList<Integer>();
		if (deleteDeviceList.get(type) != null) {
			for (Entry entry : deleteDeviceList.get(type)) {
				deleteIds.add(entry.getId());
			}
			
			main.getMainDBController().delete(type, deleteIds);
		}
		main.getMainDBController().add(type, internalNewAdds);
		main.getMainDBController().updateTableFromTable(type, internalNewValues);
		/*for (int crc : externalMap.keySet()) {
			main.getEnvironmentController(crc).updateData(type, externalMap.get(crc));
		}*/
	}

	public ObservableList<Environment> getEnvironments() {
		return environments;
	}

	public void saveEnvironment(Environment environment) throws SQLException {
		List<Object> record = new ArrayList<Object>();
		
		record.add(environment.getId());
		record.add(environment.getName());
		record.add(environment.getCollectorId());
		
		List<List<Object>> records = new ArrayList<List<Object>>();
		records.add(record);
		main.getMainDBController().updateEnvironmentFromApp(records);
		
		saveServers(environment);
	}
	
	public void saveEnvironments() throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for(Environment environment : environments) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(environment.getId());
			record.add(environment.getName());
			record.add(environment.getCollectorId());
			
			records.add(record);
		}
		main.getMainDBController().updateEnvironmentFromApp(records);
	}
	
	public void saveServer(Server server) throws SQLException {
		List<Object> record = new ArrayList<Object>();
		
		record.add(server.getId());
		record.add(server.getName());
		record.add(server.getIp());
		record.add(server.getFqdn());
		record.add(server.getType());
		record.add(server.getHasDB());
		record.add(server.getDBType());
		record.add(server.getIsSQL());
		record.add(server.getSysUser());
		record.add(server.getSysPass());
		record.add(server.getPort());
		record.add(server.getSID());
		record.add(server.usesSID());
		
		List<List<Object>> records = new ArrayList<List<Object>>();
		records.add(record);
		main.getMainDBController().updateServerFromApp(records);
	}
	
	public void saveServers(Environment environment) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for(Server server : environment.getServers()) {
			saveServer(server);
			
			List<Object> record = new ArrayList<Object>();
			
			record.add(server.getName());
			record.add(server.getIp());
			record.add(server.getFqdn());
			record.add(server.getType());
			record.add(server.getHasDB());
			record.add(server.getDBType());
			record.add(server.getIsSQL());
			record.add(server.getSysUser());
			record.add(server.getSysPass());
			record.add(server.getPort());
			record.add(server.getSID());
			record.add(server.usesSID());
			
			records.add(record);
		}
		main.getMainDBController().updateServerFromApp(records);
	}
	
	public void saveComponent(Component component) throws SQLException {
		List<Object> record = new ArrayList<Object>();
		
		record.add(component.getId());
		if (component.getType() == ComponentType.CENTRALSERVICES)
			record.add(null);
		else
			record.add(component.getVersion());
		
		if (component.getType() == ComponentType.GSIS || component.getType() == ComponentType.PANA || component.getType() == ComponentType.M2M) {
			record.add(null);
			record.add(null);
		} else {
			record.add(component.getUser());
			record.add(component.getPass());
		}
		
		List<List<Object>> records = new ArrayList<List<Object>>();
		records.add(record);
		main.getMainDBController().updateComponentFromApp(records);
	}
	
	public void saveComponents(Server server) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for(Component component : server.getComponents()) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(component.getId());
			if (component.getType() == ComponentType.CENTRALSERVICES)
				record.add(null);
			else
				record.add(component.getVersion());
			
			if (component.getType() == ComponentType.GSIS || component.getType() == ComponentType.PANA || component.getType() == ComponentType.M2M) {
				record.add(null);
				record.add(null);
			} else {
				record.add(component.getUser());
				record.add(component.getPass());
			}
			
			records.add(record);
		}
		main.getMainDBController().updateComponentFromApp(records);
	}

	public void deleteComponent(Component component) {
		Server parent = component.getParent();
		parent.getComponents().remove(component);
		
		if (addComponentLists.get(parent) != null && addComponentLists.get(parent).contains(component)) {
			addComponentLists.get(parent).remove(component);
		} else {
			if (deleteComponentLists.get(parent) == null)
				deleteComponentLists.put(parent, new ArrayList<Component>());
			deleteComponentLists.get(parent).add(component);
		}
	}
	
	public void addComponent(Server server, ComponentType type) {
		Component component = new Component(type, server);
		server.getComponents().add(component);
		
		if (addComponentLists.get(server) == null)
			addComponentLists.put(server, new ArrayList<Component>());
		addComponentLists.get(server).add(component);
	}
	
	public void deleteServer(Server server) {
		Environment parent = server.getParent();
		parent.getServers().remove(server);
		
		for (Component component : server.getComponents())
			deleteComponent(component);
		if (addServerLists.get(parent) != null && addServerLists.get(parent).contains(server)) {
			addServerLists.get(parent).remove(server);
		} else {
			if (deleteServerLists.get(parent) == null)
				deleteServerLists.put(parent, new ArrayList<Server>());
			deleteServerLists.get(parent).add(server);
		}
		
		deleteComponentLists.remove(server);
		addComponentLists.remove(server);
	}
	
	public void addServer(Environment environment) {
		Server server = new Server(environment);
		environment.addServer(server);
		
		if (addServerLists.get(environment) == null)
			addServerLists.put(environment, new ArrayList<Server>());
		addServerLists.get(environment).add(server);
	}
	
	public void deleteEnvironment(Environment environment) {
		environments.remove(environment);
		
		if (addEnvironmentList.contains(environment)) {
			addEnvironmentList.remove(environment);
		} else {
			deleteEnvironmentList.add(environment);
		}
		
		for (Server server : environment.getServers()) 
			deleteServer(server);
		
		deleteServerLists.remove(environment);
		addServerLists.remove(environment);
	}

	public void addEnvironment() {
		Environment environment = new Environment();
		environments.add(environment);
		
		addEnvironmentList.add(environment);
	}
}
