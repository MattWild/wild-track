package application.objects.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environment {
	
	private ObservableList<Server> servers;
	private int id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty collectorId;
	
	public Environment(int id, String name) {
		this.id = id;
		this.name = new SimpleStringProperty(name);
		
		this.collectorId = new SimpleIntegerProperty(-1);
		this.servers = FXCollections.observableArrayList();
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
	
	/*public List<Component> getComponents(int serverId) {
		return components.get(serverId);
	}*/
	
	/*public List<Component> getAllComponents() {
		List<Component> list = new ArrayList<Component>();
		
		for(List<Component> sublist : components.values()) 
			list.addAll(sublist);
		
		return list;
	}*/
	
	/*public void addComponent(Component component) {
		if (components.get(component.getServer().getId()) == null) 
			components.put(component.getServer().getId(), new ArrayList<Component>());
		
		components.get(component.getServer().getId()).add(component);
	}*/

	public SimpleStringProperty name() {
		return name;
	}

	public void setCollectorId(int collectorId) {
		this.collectorId.set(collectorId);
	}
	
	public int getCollectorId() {
		return collectorId.get();
	}
	
	public SimpleIntegerProperty collectorId() {
		return collectorId;
	}

	public void addServer(Server server) {
		servers.add(server);
	}

}
