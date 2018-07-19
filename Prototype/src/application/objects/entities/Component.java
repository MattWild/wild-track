package application.objects.entities;

public abstract class Component {
	
	public enum ComponentType {
		ABNT,
		ANSI,
		CapabilityService,
		CentralServices,
		CM,
		Collector,
		CommandCenter,
		GSIS,
		M2M,
		SBS,
		NMS,
		PANA
	}
	
	private Server server;
	private String version;	
	private String user;
	private String pass;
	private final ComponentType type;
	
	public Component(ComponentType type) {
		this.type = type;
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
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	//Only used for collector component
	private int collectorId;
	
	public int getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(int collectorId) {
		this.collectorId = collectorId;
	}
}
