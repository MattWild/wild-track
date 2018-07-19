package application.objects.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.objects.entities.Component.ComponentType;

public class Environment {
	
	private Map<Integer, List<Component>> components;
	private Server mainServer;
	private String name;
	
	public Environment(String name) {
		this.name = name;
		components = new HashMap<Integer, List<Component>>();)
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Server> getServers() {
		return null;
	}
	
	public List<Component> getComponents(Server server) {
		return components.get(server.getId());
	}
	
	public void addComponent(Component component) {
		if (component.getType() == ComponentType.CentralServices) mainServer = component.getServer();
		
		if (component.getServer() == null) component.setServer(mainServer);
		
		components.get(component.getServer().getId())
	}

}
