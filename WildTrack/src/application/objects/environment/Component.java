package application.objects.environment;

import javafx.beans.property.SimpleStringProperty;

public class Component {
	
	public enum ComponentType {
		ABNT,
		ANSI,
		CAPABILTYSERVICES,
		CENTRALSERVICES,
		CM,
		AMS,
		COMMANDCENTER,
		GSIS,
		M2M,
		SBS,
		NMS,
		PANA
	}
	
	private int id;
	private Server parentServer;
	private SimpleStringProperty version;	
	private SimpleStringProperty user;
	private SimpleStringProperty pass;
	private final ComponentType type;
	
	public Component(ComponentType type, Server server) {
		this(type, -1, server);
	}
	
	public Component(ComponentType type, int id, Server server) {
		this.type = type;
		this.parentServer = server;
		this.id = id;
	
		version = new SimpleStringProperty();
		user = new SimpleStringProperty();
		pass = new SimpleStringProperty();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Server getParent() {
		return parentServer;
	}
	
	public void setParent(Server server) {
		this.parentServer = server;
	}
	
	public ComponentType getType() {
		return type;
	}

	public String getVersion() {
		return version.get();
	}

	public void setVersion(String version) {
		this.version.set(version);
	}

	public SimpleStringProperty version() {
		return version;
	}

	public String getUser() {
		return user.get();
	}

	public void setUser(String user) {
		this.user.set(user);
	}

	public SimpleStringProperty user() {
		return user;
	}

	public String getPass() {
		return pass.get();
	}

	public void setPass(String pass) {
		this.pass.set(pass);
	}

	public SimpleStringProperty pass() {
		return pass;
	}
}
