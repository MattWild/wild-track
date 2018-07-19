package application.objects.entities;
import application.database.DataController;

public class Server {
	private int id;
	private String ip;
	private String fqdn;
	private String type;
	private DataController dbController;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getFqdn() {
		return fqdn;
	}
	
	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public DataController getDbController() {
		return dbController;
	}
	
	public void setDbController(DataController dbController) {
		this.dbController = dbController;
	}
	
}
