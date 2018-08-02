package application.objects.entities;

import javafx.beans.property.SimpleStringProperty;

public class Checkpoint {
	
	private int id;
	private Environment parentEnvironment;
	private SimpleStringProperty version;
	
	public Checkpoint(int id, Environment environment) {
		this.id = id;
		this.parentEnvironment = environment;
		
		version = new SimpleStringProperty();
	}
	
	public Checkpoint(Environment environment) {
		this(-1, environment);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Environment getParent() {
		return parentEnvironment;
	}
	
	public void setParent(Environment environment) {
		this.parentEnvironment = environment;
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
}
