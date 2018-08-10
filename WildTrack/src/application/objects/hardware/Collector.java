package application.objects.hardware;

import javafx.beans.property.SimpleStringProperty;

public class Collector extends Device {

	private SimpleStringProperty ip;
	private SimpleStringProperty radios;
	private SimpleStringProperty netId;
	private SimpleStringProperty app;
	private SimpleStringProperty type;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;
	private SimpleStringProperty username;
	private SimpleStringProperty password;

	public Collector(int id, String ipString, String radiosString, String netIDString, String appString, String typeString, String locString, String commentString, String userString, String passString) {
		super(id);
		
		ip = new SimpleStringProperty(ipString);
		radios = new SimpleStringProperty(radiosString);
		netId = new SimpleStringProperty(netIDString);
		app = new SimpleStringProperty(appString);
		type = new SimpleStringProperty(typeString);
		loc = new SimpleStringProperty(locString);
		notes = new SimpleStringProperty(commentString);
		username = new SimpleStringProperty(userString);
		password = new SimpleStringProperty(passString);
	}
	
	@Override
	public DeviceType getType() {
		return DeviceType.COLLECTORS;
	}

	@Override
	public String getIdentifier() {
		return ip.get();
	}

	@Override
	public boolean identifierNotNull() {
		return ip.get() != null;
	}

	@Override
	public SimpleStringProperty getFieldProperty(int i) {
		switch (i) {
		case 0:
			return ip;
		case 1:
			return radios;
		case 2:
			return netId;
		case 3:
			return app;
		case 4:
			return type;
		case 5:
			return loc;
		case 6:
			return notes;
		default:
			return null;
		}
	}

	@Override
	public SimpleStringProperty getFilterProperty(int i) {
		switch(i) {
		case 0:
			return ip;
		case 1:
			return netId;
		case 2:
			return app;
		case 3:
			return type;
		case 4:
			return loc;
		default:
			return null;
		}
	}

	public String getIp() {
		return ip.get();
	}
	
	public String getRadios() {
		return radios.get();
	}
	
	public SimpleStringProperty radios() {
		return radios;
	}

	public String getNetId() {
		return netId.get();
	}
	
	public SimpleStringProperty netId() {
		return netId;
	}

	public String getApp() {
		return app.get();
	}
	
	public String getLocation() {
		return loc.get();
	}
	
	public String getNote() {
		return notes.get();
	}
	
	public String getCollectorType() {
		return type.get();
	}
	
	public SimpleStringProperty collectorType() {
		return type;
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public SimpleStringProperty username() {
		return username;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public SimpleStringProperty password() {
		return password;
	}
}