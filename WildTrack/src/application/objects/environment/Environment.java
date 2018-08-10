package application.objects.environment;

import application.objects.environment.DeviceEnvironmentRelationship;
import application.objects.hardware.Collector;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environment {

	private int id;
	private ObservableList<Server> servers;
	private ObservableList<Checkpoint> checkpoints;
	private ObservableList<DeviceEnvironmentRelationship> deviceRelationships;
	
	private SimpleStringProperty name;
	private SimpleIntegerProperty crc;
	private SimpleStringProperty notes;
	private SimpleObjectProperty<Collector> collector;
	private SimpleObjectProperty<Component> centralServices;
	private SimpleObjectProperty<Component> commandCenter;
	
	public Environment(int id, String name) {
		this.id = id;
		this.name = new SimpleStringProperty(name);
		
		this.crc = new SimpleIntegerProperty(0);
		this.notes = new SimpleStringProperty();
		this.collector = new SimpleObjectProperty<Collector>();
		this.centralServices = new SimpleObjectProperty<Component>();
		this.commandCenter = new SimpleObjectProperty<Component>();
		this.servers = FXCollections.observableArrayList();
		this.checkpoints = FXCollections.observableArrayList();
		this.deviceRelationships = FXCollections.observableArrayList();
	}

	public Environment() {
		this(-1, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ObservableList<Server> getServers() {
		return servers;
	}
	
	public void addServer(Server server) {
		servers.add(server);
	}

	public void removeServer(Server server) {
		servers.remove(server);
	}

	public ObservableList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public void addCheckpoint(Checkpoint checkpoint) {
		checkpoints.add(checkpoint);
	}
	
	public void removeCheckpoint(Checkpoint checkpoint) {
		checkpoints.remove(checkpoint);
	}

	public ObservableList<DeviceEnvironmentRelationship> getDeviceRelationships() {
		return deviceRelationships;
	}

	public void addDeviceRelationship(DeviceEnvironmentRelationship deviceRelationship) {
		deviceRelationships.add(deviceRelationship);
	}

	public void removeDeviceRelationship(DeviceEnvironmentRelationship deviceRelationship) {
		deviceRelationships.remove(deviceRelationship);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public SimpleStringProperty name() {
		return name;
	}
	
	public int getCRC() {
		return crc.get();
	}
	
	public void setCRC(int crc) {
		this.crc.set(crc);
	}
	
	public SimpleIntegerProperty crc() {
		return crc;
	}
	
	public String getNotes() {
		return notes.get();
	}
	
	public void setNotes(String notes) {
		this.notes.set(notes);
	}
	
	public SimpleStringProperty notes() {
		return notes;
	}

	public Collector getCollector() {
		return collector.get();
	}

	public void setCollector(Collector collector) {
		this.collector.set(collector);
	}
	
	public SimpleObjectProperty<Collector> collector() {
		return collector;
	}
	
	public Component getCentralServices() {
		return centralServices.get();
	}

	public void setCentralServices(Component centralServices) {
		this.centralServices.set(centralServices);
	}
	
	public SimpleObjectProperty<Component> centralServices() {
		return centralServices;
	}
	
	public Component getCommandCenter() {
		return commandCenter.get();
	}

	public void setCommandCenter(Component commandCenter) {
		this.commandCenter.set(commandCenter);
	}
	
	public SimpleObjectProperty<Component> commandCenter() {
		return commandCenter;
	}
}
