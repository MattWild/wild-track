package application.objects.entities;

import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Collector implements Entry {
	
	private int id;
	private SimpleStringProperty ip;
	private SimpleStringProperty radios;
	private SimpleStringProperty netId;
	private SimpleStringProperty app;
	private SimpleStringProperty type;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	
	private boolean changed;

	public Collector(int id, String ipString, String radiosString, String netIDString, String appString, String typeString, String locString, String commentString, String userString, String passString) {
		this.id = id;
		
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
	
	public SimpleStringProperty username() {
		return username;
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public SimpleStringProperty password() {
		return password;
	}
	
	public String getPassword() {
		return password.get();
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	@Override
	public TableType getType() {
		return TableType.Collectors;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
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
	public String getFilterValue(int i) {
		switch(i) {
		case 0:
			return ip.get();
		case 1:
			return netId.get();
		case 2:
			return app.get();
		case 3:
			return type.get();
		case 4:
			return loc.get();
		default:
			return null;
		}
	}
	@Override
	public void indicateChanged() {
		changed = true;
	}
	
	@Override
	public boolean isChanged() {
		return changed;
	}
	
	public String getIp() {
		return ip.get();
	}
	
	public String getRadios() {
		return radios.get();
	}
	
	public String getNetId() {
		return netId.get();
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
	@Override
	public boolean identifierNotNull() {
		return ip.get() != null;
	}

	public SimpleStringProperty netId() {
		return netId;
	}

	public SimpleStringProperty type() {
		return type;
	}

	public SimpleStringProperty radios() {
		return radios;
	}
}