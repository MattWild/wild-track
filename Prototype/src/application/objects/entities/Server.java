package application.objects.entities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import application.database.DataController;
import application.objects.entities.Component.ComponentType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server {
	private int id;
	private Environment parentEnvironment;
	private ObservableList<Component> components;
	
	private SimpleStringProperty name;
	private SimpleStringProperty ip;
	private SimpleStringProperty fqdn;
	private SimpleStringProperty type;
	private SimpleBooleanProperty hasDB;
	private SimpleStringProperty dbType;
	private SimpleBooleanProperty isSQL;
	private SimpleStringProperty sysUser;
	private SimpleStringProperty sysPass;
	private SimpleStringProperty sid;
	private SimpleBooleanProperty usesSID;
	private SimpleIntegerProperty port;
	private DataController dbController;
	
	
	public Server(Environment environment) {
		this(-1, environment);
	}
	
	public Server(int id, Environment environment) {
		this.id = id;
		this.parentEnvironment = environment;
		this.components = FXCollections.observableArrayList();
		
		name = new SimpleStringProperty();
		ip = new SimpleStringProperty();
		fqdn = new SimpleStringProperty();
		type = new SimpleStringProperty();
		hasDB = new SimpleBooleanProperty();
		dbType = new SimpleStringProperty();
		isSQL = new SimpleBooleanProperty();
		sysUser = new SimpleStringProperty();
		sysPass = new SimpleStringProperty();
		sid = new SimpleStringProperty();
		usesSID = new SimpleBooleanProperty(true);
		port = new SimpleIntegerProperty();
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
	
	public SimpleStringProperty ip() {
		return ip;
	}
	
	public String getIp() {
		return ip.get();
	}
	
	public void setIp(String ip) {
		this.ip.set(ip);
	}
	
	public SimpleStringProperty name() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public SimpleStringProperty fqdn() {
		return fqdn;
	}
	
	public String getFqdn() {
		return fqdn.get();
	}
	
	public void setFqdn(String fqdn) {
		this.fqdn.set(fqdn);
	}
	
	public SimpleStringProperty type() {
		return type;
	}
	
	public String getType() {
		return type.get();
	}
	
	public void setType(String type) {
		this.type.set(type);
	}
	
	public SimpleBooleanProperty hasDB() {
		return hasDB;
	}
	
	public boolean getHasDB() {
		return hasDB.get();
	}
	
	public void setHasDB(boolean value) {
		this.hasDB.set(value);
	}
	
	public SimpleStringProperty dbType() {
		return dbType;
	}
	
	public String getDBType() {
		return dbType.get();
	}
	
	public void setDBType(String dbType) {
		this.dbType.set(dbType);
	}
	
	public SimpleStringProperty sysUser() {
		return sysUser;
	}
	
	public String getSysUser() {
		return sysUser.get();
	}
	
	public void setSysUser(String sysUser) {
		this.sysUser.set(sysUser);
	}
	
	public SimpleStringProperty sysPass() {
		return sysPass;
	}
	
	public String getSysPass() {
		return sysPass.get();
	}
	
	public void setSysPass(String sysPass) {
		this.sysPass.set(sysPass);
	}
	
	public SimpleIntegerProperty port( ) {
		return port;
	}
	
	public Integer getPort() {
		return port.get();
	}
	
	public void setPort(Integer port) {
		this.port.set((port == null)? -1 : port);
	}
	
	public SimpleStringProperty sid() {
		return sid;
	}
	
	public String getSID() {
		return sid.get();
	}
	
	public void setSID(String sid) {
		this.sid.set(sid);
	}
	
	public SimpleBooleanProperty isSQL() {
		return isSQL;
	}
	
	public Boolean getIsSQL() {
		return isSQL.get();
	}
	
	public void setIsSQL(boolean value) {
		this.isSQL.set(value);
	}
	
	public SimpleBooleanProperty usesSid() {
		return usesSID;
	}
	
	public Boolean usesSID() {
		return usesSID.get();
	}
	
	public void indicateSID() {
		this.usesSID.set(true);
	}
	
	public void indicateServiceName() {
		this.usesSID.set(false);
	}
	
	public DataController getDbController() {
		return dbController;
	}
	
	public void setDbController(DataController dbController) {
		this.dbController = dbController;
	}

	public ObservableList<Component> getComponents() {
		return components;
	}

	public void addComponent(Component component) throws Exception {
		if (component.getType() == ComponentType.CENTRALSERVICES) {
			if (parentEnvironment.getCentralServices() == null)
				parentEnvironment.setCentralServices(component);
			else
				throw new Exception("Cannot add more than one central service to environment.");
		} else if (component.getType() == ComponentType.COMMANDCENTER) {
			if (parentEnvironment.getCommandCenter() == null)
				parentEnvironment.setCommandCenter(component);
			else
				throw new Exception("Cannot add more than one command center to environment.");
		}
		
		components.add(component);
	}

	public void removeComponent(Component component) {
		if (component.getType() == ComponentType.CENTRALSERVICES) {
			parentEnvironment.setCentralServices(null);
		} else if (component.getType() == ComponentType.COMMANDCENTER) {
			parentEnvironment.setCommandCenter(null);
		}
		
		components.remove(component);
	}
	
}
