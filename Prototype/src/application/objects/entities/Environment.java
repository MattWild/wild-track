package application.objects.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environment {
	
	private ObservableList<Server> servers;
	private ObservableList<Checkpoint> checkpoints;
	private int id;
	private SimpleStringProperty name;
	private SimpleObjectProperty<Collector> collector;
	private SimpleObjectProperty<Component> centralServices;
	private SimpleObjectProperty<Component> commandCenter;
	
	public Environment(int id, String name) {
		this.id = id;
		this.name = new SimpleStringProperty(name);
		
		this.collector = new SimpleObjectProperty<Collector>();
		this.centralServices = new SimpleObjectProperty<Component>();
		this.commandCenter = new SimpleObjectProperty<Component>();
		this.servers = FXCollections.observableArrayList();
		this.checkpoints = FXCollections.observableArrayList();
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
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public ObservableList<Server> getServers() {
		return servers;
	}
	
	public ObservableList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public SimpleStringProperty name() {
		return name;
	}

	public void setCollector(Collector collector) {
		this.collector.set(collector);
	}
	
	public Collector getCollector() {
		return collector.get();
	}
	
	public SimpleObjectProperty<Collector> collector() {
		return collector;
	}
	
	public void setCentralServices(Component centralServices) {
		this.centralServices.set(centralServices);
	}
	
	public Component getCentralServices() {
		return centralServices.get();
	}
	
	public SimpleObjectProperty<Component> centralServices() {
		return centralServices;
	}
	
	public void setCommandCenter(Component commandCenter) {
		this.commandCenter.set(commandCenter);
	}
	
	public Component getCommandCenter() {
		return commandCenter.get();
	}
	
	public SimpleObjectProperty<Component> commandCenter() {
		return commandCenter;
	}

	public void addServer(Server server) {
		servers.add(server);
	}
	
	public void addCheckpoint(Checkpoint checkpoint) {
		checkpoints.add(checkpoint);
	}

	public void removeServer(Server server) {
		servers.remove(server);
	}

}
