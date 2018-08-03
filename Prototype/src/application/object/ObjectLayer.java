package application.object;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import application.Main;
import application.database.DataController;
import application.objects.entities.Checkpoint;
import application.objects.entities.Collector;
import application.objects.entities.CollectorComponent;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Device;
import application.objects.entities.Environment;
import application.objects.entities.HANDevice;
import application.objects.entities.Meter;
import application.objects.entities.Router;
import application.objects.entities.Server;
import application.objects.entities.Socket;
import application.objects.entities.VersionAlias;
import application.presentation.logic.DeviceGridController.TableType;
import application.services.EnvironmentDatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ObjectLayer {
	
	private Map<TableType, ObservableList<Device>> entries;
	private Map<TableType, List<Device>> deleteDeviceList;
	private ObservableList<Environment> environments;
	private List<Environment> deleteEnvironmentList;
	private List<Environment> addEnvironmentList;
	private Map<Environment, List<Server>> deleteServerLists;
	private	Map<Environment, List<Server>> addServerLists;
	private Map<Server, List<Component>> deleteComponentLists;
	private Map<Server, List<Component>> addComponentLists;
	private ObservableMap<String, VersionAlias> versionAliases;
	private List<VersionAlias> addVersionAliases;
	private List<VersionAlias> deleteVersionAliases;
	private Map<Environment, List<Checkpoint>> deleteCheckpointLists;
	private Map<Environment, List<Checkpoint>> addCheckpointLists;
	private Main main;
	
	public ObjectLayer(Main main) {
		this.main = main;
		entries = new HashMap<TableType, ObservableList<Device>>();
		environments = FXCollections.observableArrayList();
		deleteEnvironmentList = new ArrayList<Environment>();
		addEnvironmentList = new ArrayList<Environment>();
		deleteServerLists =  new HashMap<Environment, List<Server>>();
		addServerLists =  new HashMap<Environment, List<Server>>();
		deleteComponentLists = new HashMap<Server, List<Component>>();
		versionAliases = FXCollections.observableHashMap();
		addVersionAliases = new ArrayList<VersionAlias>();
		deleteVersionAliases = new ArrayList<VersionAlias>();
		addComponentLists = new HashMap<Server, List<Component>>();
		deleteCheckpointLists = new HashMap<Environment, List<Checkpoint>>();
		addCheckpointLists = new HashMap<Environment, List<Checkpoint>>();
		
		for (TableType type : TableType.values()) {
			ObservableList<Device> list = FXCollections.observableArrayList();
			entries.put(type, list);
		}
		deleteDeviceList = new HashMap<TableType, List<Device>>();
	}
	
	public ObservableList<Environment> getEnvironments() {
		return environments;
	}

	public ObservableList<Device> getDevices(TableType type) {
		return entries.get(type);
	}
	
	public ObservableList<VersionAlias> getVersionAliases() {
		ObservableList<VersionAlias> list = FXCollections.observableArrayList();
		
		list.addAll(versionAliases.values());
		
		versionAliases.addListener((MapChangeListener.Change<? extends String, ? extends VersionAlias> change) -> {
			System.out.println(list);
			if (change.wasAdded()) {
				list.addAll(change.getValueAdded());
			} else if (change.wasRemoved()) {
				list.remove(change.getValueRemoved());
			}
			
			System.out.println(list);
		});
		
		return list;
	}
	
	public ObservableMap<String, VersionAlias> getAliasMapping() {
		return versionAliases;
	}
	
	public void loadVersionAliases() {
		versionAliases.clear();
		
		try {
			List<List<Object>> values = main.getMainDBController().getVersionAliases();
			
			for (List<Object> record : values) {
				VersionAlias versionAlias = new VersionAlias((Integer) record.get(0), (String) record.get(1), (String) record.get(2));
				
				versionAlias.raw().addListener((arg, oldValue, newValue) -> {
					versionAliases.remove(oldValue, versionAlias);
					versionAliases.put(newValue, versionAlias);
				});
				
				versionAliases.put(versionAlias.getRaw(), versionAlias);
			}
			
			System.out.println(versionAliases);
		} catch (Exception e) {
			main.errorHandle(e);
		}
	}

	public void loadEnvironments() {
		environments.clear();
		
		try {
			environments.addAll(main.getMainDBController().loadEnvironments());
			
			for (Environment environment : environments) {
				entries.get(TableType.Collectors).addListener(collectorListener(environment));
				loadServersFromEnvironment(environment);
				loadCheckpointsFromEnvironments(environment);
			}
		} catch (Exception e) {
			main.errorHandle(e);
		}
	}

	private ListChangeListener<Device> collectorListener(Environment environment) {
		return (ListChangeListener.Change<? extends Device> change) -> {
			if (environment.getCollector() != null && environment.getCollector().getId() != -1) 
				while (change.next())
					if (change.wasAdded()) {
						for (Device device : change.getAddedSubList()) {
							if (device.getId() == environment.getCollector().getId()) {
								environment.setCollector((Collector) device);
							}
						}
					} else if (change.wasRemoved()) {
						for (Device device : change.getRemoved())
							if (environment.getCollector() != null && device.getId() == environment.getCollector().getId())
								environment.setCollector(new Collector(environment.getCollector().getId(), null, null, null, null, null, null, null, null, null));
					}
		};
	}

	private void loadServersFromEnvironment(Environment environment) throws SQLException, Exception {
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

	private void loadCheckpointsFromEnvironments(Environment environment) throws SQLException {
		List<List<Object>> values = main.getMainDBController().getCheckpointsByEnvironment(environment.getId());
		
		for (List<Object> record : values) {
			Checkpoint checkpoint = new Checkpoint((Integer) record.get(0), environment);
			
			checkpoint.setVersion((String) record.get(1));
			environment.addCheckpoint(checkpoint);
		}
	}

	public void loadComponentsFromServer(Server server) throws SQLException, Exception {
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
				type = ComponentType.AMS;
				break;
			}
			
			Component component = new Component(type, (Integer) componentRecord.get(0), server);
			component.setVersion((String) componentRecord.get(2));
			component.setUser((String) componentRecord.get(3));
			component.setPass((String) componentRecord.get(4));
			
			server.addComponent(component);
		}
	}

	public void loadCollectorComponent(CollectorComponent collectorComponent) {
		for (Device device : entries.get(TableType.Collectors)) {
			Collector collector = (Collector) device;
			
			if (collectorComponent.getId() == collector.getId()) collectorComponent.setCollector(collector);
		}
	}

	public void loadDevices(TableType type) throws SQLException {
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
						(String) ((values.get(3) == null)? values.get(3) : values.get(3).toString()), 
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6), 
						(String) values.get(7),
						(String) values.get(8),
						(String) values.get(9)));
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
	
	public void saveVersionAliases() throws SQLException {
		List<List<Object>> values = new ArrayList<List<Object>>();
		
		for (VersionAlias versionAlias : versionAliases.values()) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(versionAlias.getId());
			record.add(versionAlias.getRaw());
			record.add(versionAlias.getAlias());
			
			if (versionAlias.getId() != -1) {
				values.add(record);
			} else {
				record.remove(0);
				
				versionAlias.setId(main.getMainDBController().addVersionAlias(record));
			}
		}
		
		List<Integer> deleteIds = new ArrayList<Integer>();
		if (deleteVersionAliases != null) {
			for (VersionAlias versionAlias : deleteVersionAliases) {
				deleteIds.add(versionAlias.getId());
			}
			
			main.getMainDBController().deleteVersionAliases(deleteIds);
		}
		deleteVersionAliases.clear();
		
		main.getMainDBController().updateVersionAliases(values);
	}

	public void saveAllEnvironments() throws SQLException {
		Iterator<Environment> iter = addEnvironmentList.iterator();
		while (iter.hasNext()) {
			Environment addEnvironment = iter.next();
			List<Object> addRecord = new ArrayList<Object>();
			addRecord.add(addEnvironment.getName());
	
			addEnvironment.setId(main.getMainDBController().addEnvironment(addRecord));
			iter.remove();
		}
		
		iter = deleteEnvironmentList.iterator();
		List<Integer> deleteIds = new ArrayList<Integer>();
		while (iter.hasNext()) {
			deleteIds.add(iter.next().getId());
			iter.remove();
		}
		main.getMainDBController().deleteEnvironments(deleteIds);
		
		for (Environment environment : environments)
			processSaveEnvironment(environment);
	}

	public void saveEnvironment(Environment environment) throws SQLException {
		if (addEnvironmentList.contains(environment)) {
			List<Object> addRecord = new ArrayList<Object>();
			addRecord.add(environment.getName());
	
			System.out.println(addRecord);
			environment.setId(main.getMainDBController().addEnvironment(addRecord));
		}
		
		processSaveEnvironment(environment);
	}

	private void processSaveEnvironment(Environment environment) throws SQLException {
		if (environment.getName() == null || environment.getName().length() == 0) {
			main.errorHandle("Cannot save environment", "Environment name cannot be blank.");
			return;
		}
		
		List<Object> record = new ArrayList<Object>();
		
		record.add(environment.getId());
		record.add(environment.getName());
		record.add((environment.getCollector() == null)? null : environment.getCollector().getId());
		
		List<List<Object>> records = new ArrayList<List<Object>>();
		records.add(record);
		main.getMainDBController().updateEnvironments(records);
		
		if (environment.getCollector() != null) {
			List<Object> collectorRecord = new ArrayList<Object>();
			Collector collector = environment.getCollector();
			
			collectorRecord.add(collector.getId());
			collectorRecord.add(collector.getIp());
			collectorRecord.add(collector.getRadios());
			collectorRecord.add(collector.getNetId());
			collectorRecord.add(collector.getApp());
			collectorRecord.add(collector.getCollectorType());
			collectorRecord.add(collector.getLocation());
			collectorRecord.add(collector.getNote());
			collectorRecord.add(collector.getUsername());
			collectorRecord.add(collector.getPassword());
			
			List<List<Object>> colList = new ArrayList<List<Object>>();
			colList.add(collectorRecord);
			main.getMainDBController().updateTableFromTable(TableType.Collectors, colList);
		}
		
		processAddServers(environment);
		processSaveServers(environment);
		
		processAddComponent(environment);
		processSaveComponents(environment);
		
		processAddCheckpoints(environment);
		processSaveCheckpoints(environment);
		
		processDeleteComponents(environment);
		processDeleteServers(environment);
		processDeleteCheckpoints(environment);
	}

	private void processSaveServers(Environment environment) throws SQLException {
		
		List<List<Object>> records = new ArrayList<List<Object>>();
		for(Server server : environment.getServers()) {
			if (environment.getName() == null || environment.getName().length() == 0) {
				main.errorHandle("Cannot save server", "Server name cannot be blank.");
				continue;
			}
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
			
			records.add(record);
		}
		main.getMainDBController().updateServers(records);
	}

	private void processSaveComponents(Environment environment) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		for (Server server : environment.getServers()) {
			for (Component component : server.getComponents()) {
				if (addComponentLists.get(server) != null && addComponentLists.get(server).contains(component)) {
					
				}
				
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
		}
		main.getMainDBController().updateComponents(records);
	}

	private void processSaveCheckpoints(Environment environment) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		for(Checkpoint checkpoint : environment.getCheckpoints()) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(checkpoint.getId());
			record.add(checkpoint.getVersion());
			
			records.add(record);
		}
		main.getMainDBController().updateCheckpoints(records);
	}

	public void saveDevices(TableType type) throws SQLException {
		List<List<Object>> internalNewValues = new ArrayList<List<Object>>();
		
		for (Device device : entries.get(type)) {
			if (device.isChanged()) {
				List<Object> internalRecord = new ArrayList<Object>();
				
				switch (type) {
				case Collectors:
					Collector collector = (Collector) device;
					
					if (collector.getNetId() != null) {
						try {
							Integer.parseInt(collector.getNetId());
						} catch (NumberFormatException e) {
							main.errorHandle("Cannot save collector", "Network ID must be valid integer or blank.");
						}
					}
					
					internalRecord.add(collector.getId());
					internalRecord.add(collector.getIp());
					internalRecord.add(collector.getRadios());
					internalRecord.add(collector.getNetId());
					internalRecord.add(collector.getApp());
					internalRecord.add(collector.getCollectorType());
					internalRecord.add(collector.getLocation());
					internalRecord.add(collector.getNote());
					internalRecord.add(collector.getUsername());
					internalRecord.add(collector.getPassword());
					break;
				case HANDevices:
					HANDevice han = (HANDevice) device;
	
					internalRecord.add(han.getId());
					internalRecord.add(han.getUnitId());
					internalRecord.add(han.getDeviceName());
					internalRecord.add(han.getInstall());
					internalRecord.add(han.getMAC());
					internalRecord.add(han.getLocation());
					internalRecord.add(han.getNote());
					break;
				case Meters:
					Meter meter = (Meter) device;
	
					internalRecord.add(meter.getId());
					internalRecord.add(meter.getLAN());
					internalRecord.add(meter.getLocation());
					internalRecord.add(meter.getSocket());
					internalRecord.add(meter.getNote());
					break;
				case Routers:
					Router router = (Router) device;
	
					internalRecord.add(router.getId());
					internalRecord.add(router.getLAN());
					internalRecord.add(router.getLocation());
					internalRecord.add(router.getNote());
					break;
				case Sockets:
					Socket socket = (Socket) device;
					
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
					
					device.setId(main.getMainDBController().addDevices(type, internalRecord));
				} else {
					if (type == TableType.Meters || type == TableType.Routers) internalRecord.remove(1);
					internalNewValues.add(internalRecord);
				}
			}
		}
		
		List<Integer> deleteIds = new ArrayList<Integer>();
		if (deleteDeviceList.get(type) != null) {
			for (Device device : deleteDeviceList.get(type)) {
				deleteIds.add(device.getId());
			}
			
			main.getMainDBController().delete(type, deleteIds);
			deleteDeviceList.get(type).clear();
		}
		main.getMainDBController().updateTableFromTable(type, internalNewValues);
	}
	
	public void addVersionAlias() {
		VersionAlias versionAlias = new VersionAlias();
		versionAlias.raw().addListener((arg, oldValue, newValue) -> {
			versionAliases.remove(oldValue, versionAlias);
			versionAliases.put(newValue, versionAlias);
		});
		
		addVersionAliases.add(versionAlias);
		
		versionAliases.put(versionAlias.getRaw(), versionAlias);
	}

	public void addEnvironment() {
		Environment environment = new Environment();
		environments.add(environment);
		
		entries.get(TableType.Collectors).addListener(collectorListener(environment));
		addEnvironmentList.add(environment);
	}

	public void addServer(Environment environment) {
		Server server = new Server(environment);
		environment.addServer(server);
		
		if (addServerLists.get(environment) == null)
			addServerLists.put(environment, new ArrayList<Server>());
		addServerLists.get(environment).add(server);
	}

	private void processAddServers(Environment environment) throws SQLException {
		if (addServerLists.get(environment) != null) {
			Iterator<Server> iter = addServerLists.get(environment).iterator();
			while (iter.hasNext()) {
				Server addServer = iter.next();
				List<Object> addRecord = new ArrayList<Object>();
				addRecord.add(addServer.getName());
				addRecord.add(environment.getId());
	
				System.out.println(addRecord);
				addServer.setId(main.getMainDBController().addServer(addRecord));
				iter.remove();
			} 
		}
	}

	public void addComponent(Server server, ComponentType type) {
		Component component = new Component(type, server);
		server.getComponents().add(component);
		
		if (addComponentLists.get(server) == null)
			addComponentLists.put(server, new ArrayList<Component>());
		addComponentLists.get(server).add(component);
	}

	private void processAddComponent(Environment environment) throws SQLException {
		for (Server server : environment.getServers()) {
			if (addComponentLists.get(server) != null) {
				Iterator<Component> iter = addComponentLists.get(server).iterator();
				while (iter.hasNext()) {
					Component addComponent = iter.next();
					List<Object> addRecord = new ArrayList<Object>();
					addRecord.add(server.getId());
	
					switch (addComponent.getType()) {
					case ABNT:
						addRecord.add(1);
						break;
					case ANSI:
						addRecord.add(2);
						break;
					case CAPABILTYSERVICES:
						addRecord.add(3);
						break;
					case CENTRALSERVICES:
						addRecord.add(4);
						break;
					case CM:
						addRecord.add(5);
						break;
					case AMS:
						addRecord.add(12);
						break;
					case COMMANDCENTER:
						addRecord.add(6);
						break;
					case GSIS:
						addRecord.add(7);
						break;
					case M2M:
						addRecord.add(8);
						break;
					case NMS:
						addRecord.add(9);
						break;
					case PANA:
						addRecord.add(10);
						break;
					case SBS:
						addRecord.add(11);
						break;
					default:
						break;
					}
	
					addComponent.setId(main.getMainDBController().addComponent(addRecord));
					iter.remove();
				} 
			}
		}
	}
	
	public void addCheckpoint(Environment environment) {
		Checkpoint checkpoint = new Checkpoint(environment);
		environment.addCheckpoint(checkpoint);
		
		if (addCheckpointLists.get(environment) == null)
			addCheckpointLists.put(environment, new ArrayList<Checkpoint>());
		addCheckpointLists.get(environment).add(checkpoint);
	}

	private void processAddCheckpoints(Environment environment) throws SQLException {
		if (addCheckpointLists.get(environment) != null) {
			Iterator<Checkpoint> iter = addCheckpointLists.get(environment).iterator();
			while (iter.hasNext()) {
				Checkpoint addCheckpoint = iter.next();
				List<Object> addRecord = new ArrayList<Object>();
				addRecord.add(addCheckpoint.getVersion());
				addRecord.add(environment.getId());
	
				System.out.println(addRecord);
				addCheckpoint.setId(main.getMainDBController().addCheckpoint(addRecord));
				iter.remove();
			} 
		}
	}

	public Device createDevice(TableType type) {
		switch (type) {
		case Meters:
			return new Meter(-1, null, null, null, null, null, null, null, null);
		case Collectors:
			return new Collector(-1, null, null, null, null, null, null, null, null, null);
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

	public void addDevice(TableType type, Device device) {
		entries.get(type).addAll(device);
	}
	
	public void deleteVersionAlias(VersionAlias versionAlias) {
		versionAliases.remove(versionAlias.getRaw(), versionAlias);
		if (versionAlias.getId() != -1) deleteVersionAliases.add(versionAlias);
	}

	public void deleteEnvironment(Environment environment) {
		environments.remove(environment);
		
		if (addEnvironmentList.contains(environment)) {
			addEnvironmentList.remove(environment);
		} else {
			deleteEnvironmentList.add(environment);
		}
		
		List<Server> serversCopy = new ArrayList<Server>(environment.getServers());
		for (Server server : serversCopy) 
			deleteServer(server);
		
		for (Server server : environment.getServers()) {
			addComponentLists.remove(server);
			deleteComponentLists.remove(server);
		}
		
		addServerLists.remove(environment);
		deleteServerLists.remove(environment);
		
		addCheckpointLists.remove(environment);
		deleteCheckpointLists.remove(environment);
	}

	public void deleteServer(Server server) {
		Environment parent = server.getParent();
		parent.removeServer(server);
		
		List<Component> componentsCopy = new ArrayList<Component>(server.getComponents());
		for (Component component : componentsCopy)
			deleteComponent(component);
		if (addServerLists.get(parent) != null && addServerLists.get(parent).contains(server)) {
			addServerLists.get(parent).remove(server);
		} else {
			if (deleteServerLists.get(parent) == null)
				deleteServerLists.put(parent, new ArrayList<Server>());
			deleteServerLists.get(parent).add(server);
		}
		
		addComponentLists.remove(server);
		deleteComponentLists.remove(server);
	}

	private void processDeleteServers(Environment environment) throws SQLException {
		if (deleteServerLists.get(environment) != null) {
			Iterator<Server> iter = deleteServerLists.get(environment).iterator();
			List<Integer> deleteIds = new ArrayList<Integer>();
			while (iter.hasNext()) {
				deleteIds.add(iter.next().getId());
				iter.remove();
			}
			main.getMainDBController().deleteServers(deleteIds);
		}
	}

	public void deleteComponent(Component component) {
		Server parent = component.getParent();
		parent.removeComponent(component);
		
		if (addComponentLists.get(parent) != null && addComponentLists.get(parent).contains(component)) {
			addComponentLists.get(parent).remove(component);
		} else {
			if (deleteComponentLists.get(parent) == null)
				deleteComponentLists.put(parent, new ArrayList<Component>());
			deleteComponentLists.get(parent).add(component);
		}
	}

	private void processDeleteComponents(Environment environment) throws SQLException {
		for (Server server : environment.getServers()) {
			if (deleteComponentLists.get(server) != null) {
				Iterator<Component> iter = deleteComponentLists.get(server).iterator();
				List<Integer> deleteIds = new ArrayList<Integer>();
				while (iter.hasNext()) {
					deleteIds.add(iter.next().getId());
					iter.remove();
				}
				main.getMainDBController().deleteComponents(deleteIds);
			}
		}
	}
	
	public void deleteCheckpoint(Checkpoint checkpoint) {
		Environment parent = checkpoint.getParent();
		parent.getCheckpoints().remove(checkpoint);
		
		if (addCheckpointLists.get(parent) != null && addCheckpointLists.get(parent).contains(checkpoint)) {
			addCheckpointLists.get(parent).remove(checkpoint);
		} else {
			if (deleteCheckpointLists.get(parent) == null)
				deleteCheckpointLists.put(parent, new ArrayList<Checkpoint>());
			deleteCheckpointLists.get(parent).add(checkpoint);
		}
	}

	private void processDeleteCheckpoints(Environment environment) throws SQLException {
		if (deleteCheckpointLists.get(environment) != null) {
			Iterator<Checkpoint> iter = deleteCheckpointLists.get(environment).iterator();
			List<Integer> deleteIds = new ArrayList<Integer>();
			while (iter.hasNext()) {
				deleteIds.add(iter.next().getId());
				iter.remove();
			}
			main.getMainDBController().deleteCheckpoints(deleteIds);
		}
	}

	public void deleteDevice(TableType type, Device device) {
		entries.get(type).remove(device);
		
		if (device.getId() != -1) {
			if (deleteDeviceList.get(type) == null) deleteDeviceList.put(type, new ArrayList<Device>());
			deleteDeviceList.get(type).add(device);
		}
	}

	public void saveCheckpoints() throws SQLException {
		for (Environment environment : environments) {
			processAddCheckpoints(environment);
			processSaveCheckpoints(environment);
			processDeleteCheckpoints(environment);
		}
	}
}
