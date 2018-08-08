package application.objects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.objects.environment.Checkpoint;
import application.objects.environment.Component;
import application.objects.environment.DeviceEnvironmentRelationship;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import application.objects.environment.Component.ComponentType;
import application.objects.hardware.Collector;
import application.objects.hardware.Device;
import application.objects.hardware.HANDevice;
import application.objects.hardware.Meter;
import application.objects.hardware.Router;
import application.objects.hardware.Socket;
import application.objects.hardware.Device.DeviceType;
import application.objects.settings.VersionAlias;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ObjectLayer {

	private Main main;
	private ObservableMap<String, VersionAlias> versionAliases;
	private ObservableList<Environment> environments;
	private Map<DeviceType, ObservableList<Device>> devices;
	private List<VersionAlias> deleteVersionAliases;
	private List<Environment> deleteEnvironmentList;
	private Map<Environment, List<Server>> deleteServerLists;
	private Map<Server, List<Component>> deleteComponentLists;
	private Map<Environment, List<Checkpoint>> deleteCheckpointLists;
	private Map<DeviceType, List<Device>> deleteDeviceLists;
	
	public ObjectLayer(Main main) {
		this.main = main;
		versionAliases = FXCollections.observableHashMap();
		environments = FXCollections.observableArrayList();
		devices = new HashMap<DeviceType, ObservableList<Device>>();
		deleteVersionAliases = new ArrayList<VersionAlias>();
		deleteEnvironmentList = new ArrayList<Environment>();
		deleteServerLists =  new HashMap<Environment, List<Server>>();
		deleteComponentLists = new HashMap<Server, List<Component>>();
		deleteCheckpointLists = new HashMap<Environment, List<Checkpoint>>();
		for (DeviceType type : DeviceType.values()) {
			ObservableList<Device> list = FXCollections.observableArrayList();
			devices.put(type, list);
		}
		deleteDeviceLists = new HashMap<DeviceType, List<Device>>();
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

	public ObservableList<Environment> getEnvironments() {
		return environments;
	}

	public ObservableList<Device> getDevices(DeviceType type) {
		return devices.get(type);
	}
	
	public ObservableMap<String, VersionAlias> getAliasMapping() {
		return versionAliases;
	}
	
	public void loadSettings() {
		versionAliases.clear();
		
		try {
			List<VersionAlias> versionAliasList = main.getDataLayer().loadVersionAliases();
			for (VersionAlias versionAlias : versionAliasList) {				
				versionAlias.raw().addListener(versionAliasListener(versionAlias));
				
				versionAliases.put(versionAlias.getRaw(), versionAlias);
			}
		} catch (Exception e) {
			main.errorHandle(e);
		}
	}
	
	private ChangeListener<String> versionAliasListener(VersionAlias versionAlias) {
		return (arg, oldValue, newValue) -> {
			versionAliases.remove(oldValue, versionAlias);
			versionAliases.put(newValue, versionAlias);
		};
	}
	
	public void loadEnvironmentDetails() {
		environments.clear();
		
		Map<Integer, Environment> environmentIdMap = new HashMap<Integer, Environment>();
		Map<Integer, Server> serverIdMap = new HashMap<Integer, Server>();
		
		try {
			List<Environment> environmentsList = main.getDataLayer().loadEnvironments();
			for (Environment environment : environmentsList) {
				devices.get(DeviceType.COLLECTORS).addListener(collectorListener(environment));
				environmentIdMap.put(environment.getId(), environment);
			}
			
			List<Server> serversList = main.getDataLayer().loadServers();
			for (Server server : serversList) {
				Environment parent = environmentIdMap.get(server.getParent().getId());
				server.setParent(parent);
				parent.addServer(server);
				serverIdMap.put(server.getId(), server);
			}
			
			List<Component> componentsList = main.getDataLayer().loadComponents();
			for (Component component : componentsList) {
				Server parent = serverIdMap.get(component.getParent().getId());
				component.setParent(parent);
				parent.addComponent(component);
			}
			
			List<Checkpoint> checkpointsList = main.getDataLayer().loadCheckpoints();
			for (Checkpoint checkpoint : checkpointsList) {
				Environment parent = environmentIdMap.get(checkpoint.getParent().getId());
				checkpoint.setParent(parent);
				parent.addCheckpoint(checkpoint);
			}
			
			environments.addAll(environmentsList);
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
	
	public void loadDeviceDetails() throws SQLException {
		Map<Integer, Environment> environmentIdMap = new HashMap<Integer, Environment>();
		
		for (Environment environment : environments) {
			for (DeviceEnvironmentRelationship deviceEnvironmentRelationship : environment.getDeviceRelationships()) {
				environments.removeListener(deviceEnvironmentRelationship.getEnvironmentRelationshipListener());
				devices.get(deviceEnvironmentRelationship.getDevice().getType()).removeListener(deviceEnvironmentRelationship.getDeviceRelationshipListener());;
			}
			environment.getDeviceRelationships().clear();
			
			environmentIdMap.put(environment.getId(), environment);
		}
		
		List<DeviceEnvironmentRelationship> deviceEnvironmentRelationships = main.getDataLayer().loadDeviceEnvironmentRelations();
		for (DeviceEnvironmentRelationship deviceEnvironmentRelationship : deviceEnvironmentRelationships) {
			environments.addListener(deviceEnvironmentRelationship.getEnvironmentRelationshipListener());
			DeviceType type = deviceEnvironmentRelationship.getDevice().getType();
			devices.get(type).addListener(deviceEnvironmentRelationship.getDeviceRelationshipListener());
			
			if (environmentIdMap.containsKey(deviceEnvironmentRelationship.getEnvironment().getId())) {
				environmentIdMap.get(deviceEnvironmentRelationship.getEnvironment().getId()).addDeviceRelationship(deviceEnvironmentRelationship);
				deviceEnvironmentRelationship.setEnvironment(environmentIdMap.get(deviceEnvironmentRelationship.getEnvironment().getId()));
			}
		}
		
		for (DeviceType type : DeviceType.values()) {
			devices.get(type).clear();
			List<Device> devicesList = main.getDataLayer().loadDevices(type);
			
			devices.get(type).addAll(devicesList);
		}
	}
	
	public void saveVersionAliases() throws SQLException {
		List<VersionAlias> saveVersionAliasList = new ArrayList<VersionAlias>();
		List<VersionAlias> addVersionAliasList = new ArrayList<VersionAlias>();
		
		for (VersionAlias versionAlias : versionAliases.values()) {
			if (versionAlias.getRaw() == null || versionAlias.getRaw().length() == 0) {
				main.errorHandle("Cannot save or add new Version Alias", "raw version cannot be blank.");
				continue;
			}
			
			if (versionAlias.getAlias() == null || versionAlias.getAlias().length() == 0) {
				main.errorHandle("Cannot save or add new Version Alias", "aliased version cannot be blank.");
				continue;
			}
			
			if (versionAlias.getId() == -1)
				addVersionAliasList.add(versionAlias);
			saveVersionAliasList.add(versionAlias);
		}
		deleteVersionAliases.clear();
		
		main.getDataLayer().deleteVersionAliases(deleteVersionAliases);
		main.getDataLayer().addVersionAliases(addVersionAliasList);
		main.getDataLayer().saveVersionAliases(saveVersionAliasList);
	}

	public void saveAllEnvironments() throws SQLException {
		processSaveEnvironments(environments);
		
		for (Environment environment : deleteEnvironmentList) {
			for(DeviceEnvironmentRelationship deviceEnvironmentRelationship : environment.getDeviceRelationships()) {
				Device device = deviceEnvironmentRelationship.getDevice();
				
				if (device.getType() == DeviceType.METERS) {
					Meter meter = (Meter) device;
					meter.removeEnvironmentRelationship(deviceEnvironmentRelationship);
				} else if (device.getType() == DeviceType.ROUTERS) {
					Router router = (Router) device;
					router.removeEnvironmentRelationship(deviceEnvironmentRelationship);
				}
			}
			
			for (Server server : environment.getServers()) {
				main.getDataLayer().deleteComponents(server.getComponents());
				deleteComponentLists.remove(server);
			}
			main.getDataLayer().deleteServers(environment.getServers());
			deleteServerLists.remove(environment);
		}
		main.getDataLayer().deleteEnvironments(deleteEnvironmentList);
		deleteEnvironmentList.clear();
	}

	public void saveEnvironment(Environment environment) throws SQLException {
		List<Environment> temp = new ArrayList<Environment>();
		temp.add(environment);
		
		processSaveEnvironments(temp);
	}

	private void processSaveEnvironments(List<Environment> environments) throws SQLException {
		List<Environment> saveEnvironmentsList = new ArrayList<Environment>();
		List<Environment> addEnvironmentsList = new ArrayList<Environment>();
		List<Device> saveCollectorsList = new ArrayList<Device>();
		List<Device> addCollectorsList = new ArrayList<Device>();
		List<Server> saveServerList = new ArrayList<Server>();
		List<Server> addServerList = new ArrayList<Server>();
		List<Server> deleteServerList = new ArrayList<Server>();
		List<Component> saveComponentList = new ArrayList<Component>();
		List<Component> addComponentList = new ArrayList<Component>();
		List<Component> deleteComponentList = new ArrayList<Component>();
		List<Checkpoint> saveCheckpointList = new ArrayList<Checkpoint>();
		List<Checkpoint> addCheckpointList = new ArrayList<Checkpoint>();
		List<Checkpoint> deleteCheckpointList = new ArrayList<Checkpoint>();
		
		for (Environment environment : environments) {
			if (environment.getName() == null || environment.getName().length() == 0) {
				main.errorHandle("Cannot save or add new environment", "Environment name cannot be blank.");
				continue;
			}
			
			if (environment.getId() == -1) 
				addEnvironmentsList.add(environment);
			saveEnvironmentsList.add(environment);
			
			if (environment.getCollector() != null) {
				Collector collector = environment.getCollector();

				if (collector.getId() == -1)
					addCollectorsList.add(collector);
				saveCollectorsList.add(collector);
			}
			
			for (Server server : environment.getServers()) {
				if (server.getName() == null || server.getName().length() == 0) {
					main.errorHandle("Cannot save or add new server", "Server name cannot be blank.");
					continue;
				}

				if (server.getId() == -1)
					addServerList.add(server);
				saveServerList.add(server);

				for (Component component : server.getComponents()) {
					if (component.getId() == -1)
						addComponentList.add(component);
					saveComponentList.add(component);
				}

				if (deleteComponentLists.containsKey(server)) {
					deleteComponentList.addAll(deleteComponentLists.get(server));
					deleteComponentLists.remove(server);
				}
			}
			
			if(deleteServerLists.containsKey(environment)) {
				deleteServerList.addAll(deleteServerLists.get(environment));
				deleteServerLists.remove(environment);
			}
			
			for (Checkpoint checkpoint : environment.getCheckpoints()) {
				if (checkpoint.getVersion() == null || checkpoint.getVersion().length() == 0) {
					main.errorHandle("Cannot save or add new checkpoint", "Checkpoint version cannot be blank");
					continue;
				}

				if (checkpoint.getId() == -1) {
					addCheckpointList.add(checkpoint);
				}
				saveCheckpointList.add(checkpoint);
			}
			if (deleteCheckpointLists.containsKey(environment)) {
				deleteCheckpointList.addAll(deleteCheckpointLists.get(environment));
				deleteCheckpointLists.remove(environment);
			}
			
			main.getDataLayer().addEnvironments(addEnvironmentsList);
			main.getDataLayer().saveEnvironments(saveEnvironmentsList);
			
			main.getDataLayer().addDevices(addCollectorsList);
			main.getDataLayer().saveDevices(DeviceType.COLLECTORS, saveCollectorsList);
			
			main.getDataLayer().addServers(addServerList);
			main.getDataLayer().saveServers(saveServerList);
			
			main.getDataLayer().addComponents(addComponentList);
			main.getDataLayer().saveComponents(saveComponentList);
			
			main.getDataLayer().addCheckpoints(addCheckpointList);
			main.getDataLayer().saveCheckpoints(saveCheckpointList);
			
			main.getDataLayer().deleteComponents(deleteComponentList);
			main.getDataLayer().deleteServers(deleteServerList);
			main.getDataLayer().deleteCheckpoints(deleteCheckpointList);
		}
	}

	public void saveDevices(DeviceType type) throws SQLException {
		List<Device> saveDevicesList = new ArrayList<Device>();
		List<Device> addDevicesList = new ArrayList<Device>();
		List<Device> deleteDevicesList = new ArrayList<Device>();
		
		for (Device device : devices.get(type)) {
			if (device.getId() == -1)
				addDevicesList.add(device);
			
			if (device.isChanged())
				saveDevicesList.add(device);
		}
		
		main.getDataLayer().addDevices(addDevicesList);
		main.getDataLayer().saveDevices(type, saveDevicesList);
		
		if (deleteDeviceLists.get(type) != null) {
			for (Device device : deleteDeviceLists.get(type)) {
				for (DeviceEnvironmentRelationship deviceEnvironmentRelationship : device.getEnvironmentRelationships()) {
					deviceEnvironmentRelationship.getEnvironment().removeDeviceRelationship(deviceEnvironmentRelationship);
				}
				
				deleteDevicesList.add(device);
			}
			
			main.getDataLayer().deleteDevices(type, deleteDevicesList);
			deleteDeviceLists.get(type).clear();
		}
	}
	
	public void addVersionAlias() {
		VersionAlias versionAlias = new VersionAlias();
		versionAlias.raw().addListener((arg, oldValue, newValue) -> {
			versionAliases.remove(oldValue, versionAlias);
			versionAliases.put(newValue, versionAlias);
		});
		
		versionAliases.put(versionAlias.getRaw(), versionAlias);
	}

	public void addEnvironment() {
		Environment environment = new Environment();
		environments.add(environment);
		
		devices.get(DeviceType.COLLECTORS).addListener(collectorListener(environment));
	}

	public void addServer(Environment environment) {
		Server server = new Server(environment);
		environment.addServer(server);
	}

	public void addComponent(Server server, ComponentType type) {
		Component component = new Component(type, server);
		server.getComponents().add(component);
	}
	
	public void addCheckpoint(Environment environment) {
		Checkpoint checkpoint = new Checkpoint(environment);
		environment.addCheckpoint(checkpoint);
	}

	public Device createDevice(DeviceType type) {
		switch (type) {
		case METERS:
			return new Meter(-1, null, null, null, null, null, null);
		case COLLECTORS:
			return new Collector(-1, null, null, null, null, null, null, null, null, null);
		case HAN_DEVICES:
			return new HANDevice(-1, null, null, null, null, null, null);
		case ROUTERS:
			return new Router(-1, null, null, null, null);
		case SOCKETS:
			return new Socket(-1, null, null, null, null);
		default:
			return null;
		}
	}

	public void addDevice(DeviceType type, Device device) {
		devices.get(type).addAll(device);
	}
	
	public void deleteVersionAlias(VersionAlias versionAlias) {
		versionAliases.remove(versionAlias.getRaw(), versionAlias);
		if (versionAlias.getId() != -1) deleteVersionAliases.add(versionAlias);
	}

	public void deleteEnvironment(Environment environment) {
		environments.remove(environment);
		deleteEnvironmentList.add(environment);
		
		List<Server> serversCopy = new ArrayList<Server>(environment.getServers());
		for (Server server : serversCopy) 
			deleteServer(server);
		
		for (Server server : environment.getServers()) {
			deleteComponentLists.remove(server);
		}
		
		deleteServerLists.remove(environment);
		
		deleteCheckpointLists.remove(environment);
	}

	public void deleteServer(Server server) {
		Environment parent = server.getParent();
		parent.removeServer(server);
		
		List<Component> componentsCopy = new ArrayList<Component>(server.getComponents());
		for (Component component : componentsCopy)
			deleteComponent(component);
		
		if (deleteServerLists.get(parent) == null)
			deleteServerLists.put(parent, new ArrayList<Server>());
		deleteServerLists.get(parent).add(server);
		
		deleteComponentLists.remove(server);
	}

	public void deleteComponent(Component component) {
		Server parent = component.getParent();
		parent.removeComponent(component);
		
		if (deleteComponentLists.get(parent) == null)
			deleteComponentLists.put(parent, new ArrayList<Component>());
		deleteComponentLists.get(parent).add(component);
	}
	
	public void deleteCheckpoint(Checkpoint checkpoint) {
		Environment parent = checkpoint.getParent();
		parent.removeCheckpoint(checkpoint);
		if (deleteCheckpointLists.get(parent) == null)
			deleteCheckpointLists.put(parent, new ArrayList<Checkpoint>());
		deleteCheckpointLists.get(parent).add(checkpoint);
	}

	public void deleteDevice(DeviceType type, Device device) {
		devices.get(type).remove(device);
		
		if (device.getId() != -1) {
			if (deleteDeviceLists.get(type) == null) deleteDeviceLists.put(type, new ArrayList<Device>());
			deleteDeviceLists.get(type).add(device);
		}
	}
}
