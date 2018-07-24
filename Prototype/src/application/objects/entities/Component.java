package application.objects.entities;

import javafx.beans.property.SimpleStringProperty;

public class Component {
	
	public enum ComponentType {
		ABNT,
		ANSI,
		CAPABILTYSERVICES,
		CENTRALSERVICES,
		CM,
		COLLECTOR,
		COMMANDCENTER,
		GSIS,
		M2M,
		SBS,
		NMS,
		PANA;
		
		private int id;
		
		public void setId(int id) {
			this.id = id;
		}
		
		public int getValue() {
			return id;
		}
	}
	
	private int id;
	private Server server;
	private SimpleStringProperty version;	
	private SimpleStringProperty user;
	private SimpleStringProperty pass;
	private final ComponentType type;
	
	public Component(ComponentType type, int id) {
		this.type = type;
		this.id= id;
	
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
	
	public ComponentType getType() {
		return type;
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getVersion() {
		return version.get();
	}

	public void setVersion(String version) {
		this.version.set(version);
	}

	public String getUser() {
		return user.get();
	}

	public void setUser(String user) {
		this.user.set(user);
	}

	public String getPass() {
		return pass.get();
	}

	public void setPass(String pass) {
		this.pass.set(pass);
	}

	public SimpleStringProperty version() {
		return version;
	}
	
	public SimpleStringProperty user() {
		return user;
	}
	
	public SimpleStringProperty pass() {
		return pass;
	}
}
