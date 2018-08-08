package application.data;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.objects.environment.Checkpoint;
import application.objects.environment.Component;
import application.objects.environment.DeviceEnvironmentRelationship;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import application.objects.environment.Component.ComponentType;
import application.objects.hardware.Collector;
import application.objects.hardware.Device;
import application.objects.hardware.HANDevice;
import application.objects.hardware.Meter;
import application.objects.hardware.Router;
import application.objects.hardware.Socket;
import application.objects.hardware.Device.DeviceType;
import application.objects.settings.VersionAlias;
import application.data.framework.DataController;

public class DataLayer extends DataController {
	
	private static final String IP_ADDRESS = "10.1.220.223";
	private static final String USERNAME = "EscalationsTeam";
	private static final String PASSWORD = "1Esc2ala!tions";
	private static final String DATABASE_NAME  = "EnvironmentManagement";
	
	public DataLayer() throws SQLException, ClassNotFoundException {
		initSQLDB(IP_ADDRESS, USERNAME, PASSWORD, DATABASE_NAME);
	}
	
	public List<VersionAlias> loadVersionAliases() throws SQLException {
		List<VersionAlias> result = new ArrayList<VersionAlias>();
		
		List<List<Object>> values = executeQuery("EXEC GetVersionAliases");
		
		for (List<Object> record : values) {
			VersionAlias versionAlias = new VersionAlias((Integer) record.get(0), (String) record.get(1), (String) record.get(2));
			
			result.add(versionAlias);
		}
		return result;
	}
	
	public List<Environment> loadEnvironments() throws SQLException {
		List<Environment> result = new ArrayList<Environment>();
		
		List<List<Object>> records = executeQuery("EXEC GetEnvironments");
		for (List<Object> record : records) {
			Environment environment = new Environment((Integer) record.get(0), (String) record.get(1));
			if (record.get(2) != null) {
				//set dummy collector
				environment.setCollector(new Collector((Integer) record.get(2), null, null, null, null, null, null, null, null, null));
			}
			environment.setCRC((record.get(3) == null)? 0 : (Integer) record.get(3));
			environment.setNotes((String) record.get(4));
			result.add(environment);
		}
		
		return result;
	}
	
	public List<Server> loadServers() throws SQLException {
		List<Server> result = new ArrayList<Server>();
		
		List<List<Object>> values = executeQuery("EXEC GetServers");
		
		for (List<Object> record : values) {
			//set dummy parent environment
			Server server = new Server((Integer) record.get(0), new Environment((Integer) record.get(1), null));
			
			server.setName((String) record.get(2));
			server.setIp((String) record.get(3));
			server.setFqdn((String) record.get(4));
			server.setType((String) record.get(5));
			if (record.get(6) != null && (Boolean) record.get(6)) {
				server.setHasDB(true);
				server.setDBType((String) record.get(7));
				if (record.get(8) != null && (Boolean) record.get(8)) {
					server.setIsSQL(true);
				} else {
					server.setIsSQL(false);
					
					server.setSysUser((String) record.get(9));
					server.setSysPass((String) record.get(10));
					server.setPort((Integer) record.get(11));
					server.setSID((String) record.get(12));
					
					if (record.get(13) != null && (Boolean) record.get(13))
						server.indicateSID();
					else
						server.indicateServiceName();
				}
			} else {
				server.setHasDB(false);
			}
			result.add(server);
		}
		
		return result;
	}
	
	public List<Component> loadComponents() throws SQLException {
		List<Component> result = new ArrayList<Component>();
		
		List<List<Object>> componentDetails = executeQuery("EXEC GetComponents");
		
		for(List<Object> componentRecord : componentDetails) {
			ComponentType type = null;
			switch (((Integer) componentRecord.get(2)).intValue()) {
			case 1:
				type = ComponentType.ABNT;
				break;
			case 2:
				type = ComponentType.ANSI;
				break;
			case 3:
				type = ComponentType.CAPABILTYSERVICES;
				break;
			case 4:
				type = ComponentType.CENTRALSERVICES;
				break;
			case 5:
				type = ComponentType.CM;
				break;
			case 6:
				type = ComponentType.COMMANDCENTER;
				break;
			case 7:
				type = ComponentType.GSIS;
				break;
			case 8:
				type = ComponentType.M2M;
				break;
			case 9:
				type = ComponentType.NMS;
				break;
			case 10:
				type = ComponentType.PANA;
				break;
			case 11:
				type = ComponentType.SBS;
				break;
			case 12:
				type = ComponentType.AMS;
				break;
			}
			
			//set dummy parent server
			Component component = new Component(type, (Integer) componentRecord.get(0), new Server((Integer) componentRecord.get(1), null));
			component.setVersion((String) componentRecord.get(3));
			component.setUser((String) componentRecord.get(4));
			component.setPass((String) componentRecord.get(5));
			
			result.add(component);
		}
		return result;
	}
	
	public List<Checkpoint> loadCheckpoints() throws SQLException {
		List<Checkpoint> result = new ArrayList<Checkpoint>();
		
		List<List<Object>> values = executeQuery("EXEC GetCheckpoints");
		
		for (List<Object> record : values) {
			Checkpoint checkpoint = new Checkpoint((Integer) record.get(0), new Environment((Integer) record.get(1), null));
			
			checkpoint.setVersion((String) record.get(2));
			result.add(checkpoint);
		}
		return result;
	}
	
	public List<Device> loadDevices(DeviceType type) throws SQLException {
		List<Device> result = new ArrayList<Device>();
		
		List<List<Object>> values = null;
		switch (type) {
		case COLLECTORS:
			values = executeQuery("GetCollectors");
			break;
		case HAN_DEVICES:
			values = executeQuery("GetHANDevices");
			break;
		case METERS:
			values = executeQuery("GetMeters");
			break;
		case ROUTERS:
			values = executeQuery("GetRouters");
			break;
		case SOCKETS:
			values = executeQuery("GetSockets");
			break;
		default:
			break;
		}
		
		for (List<Object> record : values) {
			switch (type) {
			case METERS:
				result.add(new Meter(((Integer) record.get(0)).intValue(), 
						(String) record.get(1), 
						(String) record.get(2), 
						(String) record.get(3), 
						(String) record.get(4), 
						(String) record.get(5), 
						(String) record.get(6)));
				break;
			case COLLECTORS:
				result.add(new Collector(((Integer) record.get(0)).intValue(), 
						(String) record.get(1), 
						(String) record.get(2), 
						(String) ((record.get(3) == null)? record.get(3) : record.get(3).toString()), 
						(String) record.get(4), 
						(String) record.get(5), 
						(String) record.get(6), 
						(String) record.get(7),
						(String) record.get(8),
						(String) record.get(9)));
				break;
			case HAN_DEVICES:
				result.add(new HANDevice(((Integer) record.get(0)).intValue(), 
						(String) record.get(1), 
						(String) record.get(2), 
						(String) record.get(3), 
						(String) record.get(4), 
						(String) record.get(5), 
						(String) record.get(6)));
				break;
			case ROUTERS:
				result.add(new Router(((Integer) record.get(0)).intValue(), 
						(String) record.get(1),
						(String) record.get(2), 
						(String) record.get(3), 
						(String) record.get(4)));
				break;
			case SOCKETS:
				result.add(new Socket(((Integer) record.get(0)).intValue(), 
						(Integer) record.get(1), 
						(String) record.get(2), 
						(String) record.get(3), 
						(String) record.get(4)));
				break;
			default:
				break;
			}
		}
		
		/*if (type == DeviceType.Meters || type == DeviceType.Routers) {
			for (Device device : result) {
				List<Object> temp = new ArrayList<Object>();
				temp.add(device.getId());
				
				switch (type) {
				case Meters:
					values = executeQueryWithParam(temp, "EXEC GetMeterEnvironmentRelationships ?");
					break;
				case Routers:
					values = executeQueryWithParam(temp, "EXEC GetRouterEnvironmentRelationships ?");
					break;
				}
				
				for (List<Object> record : values) {
					//Set dummy environment
					Environment environment = new Environment((Integer) record.get(0), null);
					device.addEnvironmentRelationship(new DeviceEnvironmentRelationship(environment, device, (Timestamp) record.get(1)));
				}
			}
		}*/
		return result;
	}
	


	public List<DeviceEnvironmentRelationship> loadDeviceEnvironmentRelations() throws SQLException {
		List<DeviceEnvironmentRelationship> result = new ArrayList<DeviceEnvironmentRelationship>();
		
		List<List<Object>> values = executeQuery("EXEC GetDeviceEnvironmentRelations");
		
		for (List<Object> record : values) {
			//set dummy environment and device
			Environment environment = new Environment((Integer) record.get(2), null);
			Device device = null;
			switch((Integer) record.get(1)) {
			case 1:
				device = new Meter((Integer) record.get(0), null, null, null, null, null, null);
				break;
			case 3:
				device = new Router((Integer) record.get(0), null, null, null, null);
				break;
			}
			result.add(new DeviceEnvironmentRelationship(environment, device, (Timestamp) record.get(3)));
		}
		return result;
	}
	
	public void saveVersionAliases(List<VersionAlias> versionAliases) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for(VersionAlias versionAlias : versionAliases) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(versionAlias.getId());
			record.add(versionAlias.getRaw());
			record.add(versionAlias.getAlias());
			
			records.add(record);
		}
		
		executeBatchUpdate(records, "EXEC UpdateVersionAlias ?,?,?");
	}
	
	public void saveEnvironments(List<Environment> environments) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Environment environment : environments) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(environment.getId());
			record.add(environment.getName());
			record.add((environment.getCollector() == null)? null : environment.getCollector().getId());
			record.add((environment.getCRC() == 0)? null : environment.getCRC());
			record.add(environment.getNotes());
			
			records.add(record);
		}
		
		executeBatchUpdate(records, "EXEC UpdateEnvironment ?,?,?,?,?");
	}
	
	public void saveServers(List<Server> servers) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>(); 
		
		for (Server server : servers) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(server.getId());
			record.add(server.getName());
			record.add(server.getIp());
			record.add(server.getFqdn());
			record.add(server.getType());
			record.add(server.getHasDB());
			record.add(server.getDBType());
			record.add(server.getIsSQL());
			record.add(server.getSysUser());
			record.add(server.getSysPass());
			record.add(server.getPort());
			record.add(server.getSID());
			record.add(server.getUsesSid());
			
			records.add(record);
		}
		
		executeBatchUpdate(records, "EXEC UpdateServer ?,?,?,?,?,?,?,?,?,?,?,?,?");
	}
	
	public void saveComponents(List<Component> components) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Component component : components) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(component.getId());
			record.add(component.getVersion());
			record.add(component.getUser());
			record.add(component.getPass());
			
			records.add(record);
		}
		
		executeBatchUpdate(records, "Exec UpdateComponent ?,?,?,?");
	}
	
	public void saveCheckpoints(List<Checkpoint> checkpoints) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Checkpoint checkpoint : checkpoints) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(checkpoint.getId());
			record.add(checkpoint.getVersion());
			
			records.add(record);
		}
		
		executeBatchUpdate(records, "Exec UpdateCheckpoint ?,?");
	}
	
	public void saveDevices(DeviceType type, List<Device> devices) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Device device : devices) {
			List<Object> record = new ArrayList<Object>();
			
			switch (type) {
			case COLLECTORS:
				Collector collector = (Collector) device;
				
				record.add(collector.getId());
				record.add(collector.getIp());
				record.add(collector.getRadios());
				record.add(collector.getNetId());
				record.add(collector.getApp());
				record.add(collector.getCollectorType());
				record.add(collector.getLocation());
				record.add(collector.getNote());
				record.add(collector.getUsername());
				record.add(collector.getPassword());
				break;
			case HAN_DEVICES:
				HANDevice han = (HANDevice) device;
				
				record.add(han.getId());
				record.add(han.getUnitId());
				record.add(han.getDeviceName());
				record.add(han.getInstall());
				record.add(han.getMAC());
				record.add(han.getLocation());
				record.add(han.getNote());
				break;
			case METERS:
				Meter meter = (Meter) device;
				
				record.add(meter.getId());
				record.add(meter.getLocation());
				record.add(meter.getSocket());
				record.add(meter.getNote());
				break;
			case ROUTERS:
				Router router = (Router) device;
				
				record.add(router.getId());
				record.add(router.getLocation());
				record.add(router.getNote());
				break;
			case SOCKETS:
				Socket socket = (Socket) device;
				
				record.add(socket.getId());
				record.add(socket.getIdProp());
				record.add(socket.getForm());
				record.add(socket.getNLoad());
				record.add(socket.getLocation());
				break;
			default:
				break;
			}
			
			records.add(record);
		}
		
		switch(type) {
		case COLLECTORS:
			executeBatchUpdate(records, "EXEC UpdateCollector ?,?,?,?,?,?,?,?,?,?");
			break;
		case HAN_DEVICES:
			executeBatchUpdate(records, "EXEC UpdateHANDevice ?,?,?,?,?,?,?");
			break;
		case METERS:
			executeBatchUpdate(records, "EXEC UpdateMeter ?,?,?,?");
			break;
		case ROUTERS:
			executeBatchUpdate(records, "EXEC UpdateRouter ?,?,?");
			break;
		case SOCKETS:
			executeBatchUpdate(records, "EXEC UpdateSocket ?,?,?,?,?");
			break;
		default:
			break;
		}
	}
	
	public void addVersionAliases(List<VersionAlias> versionAliases) throws SQLException {
		for (VersionAlias versionAlias : versionAliases) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(versionAlias.getRaw());
			record.add(versionAlias.getAlias());
			
			versionAlias.setId(executeAddUpdate(record, "EXEC AddVersionAlias ?,?"));
		}
	}
	
	public void addEnvironments(List<Environment> environments) throws SQLException {
		for (Environment environment : environments) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(environment.getName());
			
			environment.setId(executeAddUpdate(record, "EXEC AddEnvironment ?"));
		}
	}
	
	public void addServers(List<Server> servers) throws SQLException {
		for (Server server : servers) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(server.getName());
			record.add(server.getParent().getId());
			
			server.setId(executeAddUpdate(record, "EXEC AddServer ?,?"));
		}
	}
	
	public void addComponents(List<Component> components) throws SQLException {
		for (Component component : components) {
			List<Object> record = new ArrayList<Object>();
			record.add(component.getParent().getId());
			switch (component.getType()) {
			case ABNT:
				record.add(1);
				break;
			case ANSI:
				record.add(2);
				break;
			case CAPABILTYSERVICES:
				record.add(3);
				break;
			case CENTRALSERVICES:
				record.add(4);
				break;
			case CM:
				record.add(5);
				break;
			case AMS:
				record.add(12);
				break;
			case COMMANDCENTER:
				record.add(6);
				break;
			case GSIS:
				record.add(7);
				break;
			case M2M:
				record.add(8);
				break;
			case NMS:
				record.add(9);
				break;
			case PANA:
				record.add(10);
				break;
			case SBS:
				record.add(11);
				break;
			default:
				break;
			}
			
			component.setId(executeAddUpdate(record, "EXEC AddComponent ?,?"));
		}
	}
	
	public void addCheckpoints(List<Checkpoint> checkpoints) throws SQLException {
		for (Checkpoint checkpoint : checkpoints) {
			List<Object> record = new ArrayList<Object>();
			
			record.add(checkpoint.getVersion());
			record.add(checkpoint.getParent().getId());
			
			checkpoint.setId(executeAddUpdate(record, "EXEC AddCheckpoint ?,?"));
		}
	}
	
	public void addDevices(List<Device> devices) throws SQLException {
		for (Device device : devices) {
			List<Object> record = new ArrayList<Object>();
			
			DeviceType type = device.getType();
			switch (type) {
			case COLLECTORS:
				Collector collector = (Collector) device;
				
				record.add(collector.getIp());
				record.add(collector.getRadios());
				record.add(collector.getNetId());
				record.add(collector.getApp());
				record.add(collector.getCollectorType());
				record.add(collector.getLocation());
				record.add(collector.getNote());
				record.add(collector.getUsername());
				record.add(collector.getPassword());
				break;
			case HAN_DEVICES:
				HANDevice han = (HANDevice) device;
				
				record.add(han.getUnitId());
				record.add(han.getDeviceName());
				record.add(han.getInstall());
				record.add(han.getMAC());
				record.add(han.getLocation());
				record.add(han.getNote());
				break;
			case METERS:
				Meter meter = (Meter) device;
				
				record.add(meter.getLAN());
				record.add(meter.getLocation());
				record.add(meter.getSocket());
				record.add(meter.getNote());
				break;
			case ROUTERS:
				Router router = (Router) device;
				
				record.add(router.getLAN());
				record.add(router.getLocation());
				record.add(router.getNote());
				break;
			case SOCKETS:
				Socket socket = (Socket) device;
				
				record.add(socket.getIdProp());
				record.add(socket.getForm());
				record.add(socket.getNLoad());
				record.add(socket.getLocation());
				break;
			default:
				break;
			}
			
			switch(type) {
			case COLLECTORS:
				device.setId(executeAddUpdate(record, "EXEC AddCollector ?,?,?,?,?,?,?,?,?"));
				break;
			case HAN_DEVICES:
				device.setId(executeAddUpdate(record, "EXEC AddHANDevice ?,?,?,?,?,?"));
				break;
			case METERS:
				device.setId(executeAddUpdate(record, "EXEC AddMeter ?,?,?,?"));
				break;
			case ROUTERS:
				device.setId(executeAddUpdate(record, "EXEC AddRouter ?,?,?"));
				break;
			case SOCKETS:
				device.setId(executeAddUpdate(record, "EXEC AddSocket ?,?,?,?"));
				break;
			default:
				break;
			}
		}
	}
	
	public void deleteVersionAliases(List<VersionAlias> versionAliases) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (VersionAlias versionAlias : versionAliases) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(versionAlias.getId());
			records.add(idRecord);
		}
		
		executeBatchUpdate(records, "EXEC DeleteVersionAlias ?");
	}
	
	public void deleteEnvironments(List<Environment> environments) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Environment environment : environments) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(environment.getId());
			records.add(idRecord);
		}
		
		executeBatchUpdate(records, "EXEC DeleteEnvironment ?");
	}
	
	public void deleteServers(List<Server> servers) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Server server : servers) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(server.getId());
			records.add(idRecord);
		}
		
		executeBatchUpdate(records, "EXEC DeleteServer ?");
	}
	
	public void deleteComponents(List<Component> components) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Component component : components) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(component.getId());
			records.add(idRecord);
		}
		
		executeBatchUpdate(records, "EXEC DeleteComponent ?");
	}
	
	public void deleteCheckpoints(List<Checkpoint> checkpoints) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Checkpoint checkpoint : checkpoints) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(checkpoint.getId());
			records.add(idRecord);
		}
		
		executeBatchUpdate(records, "EXEC DeleteCheckpoint ?");
	}
	
	public void deleteDevices(DeviceType type, List<Device> devices) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		
		for (Device device : devices) {
			List<Object> idRecord = new ArrayList<Object>();
			idRecord.add(device.getId());
			records.add(idRecord);
		}
		
		switch(type) {
		case COLLECTORS:
			executeBatchUpdate(records, "EXEC DeleteCollector ?");
			break;
		case HAN_DEVICES:
			executeBatchUpdate(records, "EXEC DeleteHANDevice ?");
			break;
		case METERS:
			executeBatchUpdate(records, "EXEC DeleteMeter ?");
			break;
		case ROUTERS:
			executeBatchUpdate(records, "EXEC DeleteRouter ?");
			break;
		case SOCKETS:
			executeBatchUpdate(records, "EXEC DeleteSocket ?");
			break;
		default:
			break;
		}
	}
	
	public void updateComponentsFromService(List<List<Object>> records) throws SQLException {
		executeBatchUpdate(records, "EXEC UpdateComponentFromService ?,?,?,?");
	}

	public void updateDevicesFromService(DeviceType type, List<List<Object>> records) throws SQLException {
		switch (type) {
		case METERS:
			executeBatchUpdate(records, "EXEC UpdateMeterFromService ?,?,?,?,?");
			break;
			
		case ROUTERS:
			executeBatchUpdate(records, "EXEC UpdateRouterFromService ?,?,?,?");
			break;
			
		default:
			break;
		}
	}
	
	public void clearEnvironRelationships(Environment environment) throws SQLException {
		List<List<Object>> records = new ArrayList<List<Object>>();
		List<Object> record = new ArrayList<Object>();
		record.add(environment.getId());
		records.add(record);
		
		executeBatchUpdate(records, "EXEC ClearEnvironmentDeviceRelations ?");
	}
	
	@Override
	protected SQLException generateConnectionError() {
		return new SQLException("Main Database Connection was broken.");
	}

}
