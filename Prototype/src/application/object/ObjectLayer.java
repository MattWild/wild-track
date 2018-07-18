package application.object;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.objects.entities.Collector;
import application.objects.entities.Entry;
import application.objects.entities.HANDevice;
import application.objects.entities.Meter;
import application.objects.entities.Router;
import application.objects.entities.Socket;
import application.presentation.logic.TableController.TableType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObjectLayer {
	
	private Map<TableType, ObservableList<Entry>> entries;
	private Map<TableType, List<Entry>> deleteList;
	private Main main;
	
	public ObjectLayer(Main main) {
		this.main = main;
		entries = new HashMap<TableType, ObservableList<Entry>>();
		for (TableType type : TableType.values()) {
			ObservableList<Entry> list = FXCollections.observableArrayList();
			entries.put(type, list);
		}
		deleteList = new HashMap<TableType, List<Entry>>();
	}
	
	public void loadTable(TableType type) throws SQLException {
		entries.get(type).clear();
		for (List<Object> values : main.getMainDBController().getTableData(type)) {
			switch (type) {
			case Meters:
				entries.get(type).add(new Meter(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(Integer) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6), 
						(String) values.get(7), 
						(String) values.get(8)));
				break;
			case Collectors:
				entries.get(type).add(new Collector(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6), 
						(String) values.get(7)));
				break;
			case HANDevices:
				entries.get(type).add(new HANDevice(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6)));
				break;
			case Routers:
				entries.get(type).add(new Router(((Integer) values.get(0)).intValue(), 
						(String) values.get(1), 
						(Integer) values.get(2), 
						(String) values.get(3),
						(String) values.get(4), 
						(String) values.get(5), 
						(String) values.get(6)));
				break;
			case Sockets:
				entries.get(type).add(new Socket(((Integer) values.get(0)).intValue(), 
						(Integer) values.get(1), 
						(String) values.get(2), 
						(String) values.get(3), 
						(String) values.get(4)));
				break;
			default:
				break;
			}
		}
	}

	public ObservableList<Entry> getEntries(TableType type) {
		return entries.get(type);
	}
	
	public Entry createDevice(TableType type) {
		switch (type) {
		case Meters:
			return new Meter(-1, null, null, null, null, null, null, null, null);
		case Collectors:
			return new Collector(-1, null, null, null, null, null, null, null);
		case HANDevices:
			return new HANDevice(-1, null, null, null, null, null, null);
		case Routers:
			return new Router(-1, null, null, null, null, null, null);
		case Sockets:
			return new Socket(-1, null, null, null, null);
		default:
			return null;
		}
	}
	
	public void commitDevice(TableType type, Entry entry) {
		entries.get(type).addAll(entry);
	}
	
	public void deleteDevice(TableType type, Entry entry) {
		entries.get(type).remove(entry);
		
		if (deleteList.get(type) == null) deleteList.put(type, new ArrayList<Entry>());
		deleteList.get(type).add(entry);
	}
	
	public void saveTable(TableType type) throws SQLException {
		List<List<Object>> internalNewValues = new ArrayList<List<Object>>();
		List<List<Object>> internalNewAdds = new ArrayList<List<Object>>();
		//Map<Integer, List<List<Object>>> externalMap = new HashMap<Integer, List<List<Object>>>();
		
		for (Entry entry : entries.get(type)) {
			if (entry.isChanged()) {
				List<Object> internalRecord = new ArrayList<Object>();
				//List<Object> externalRecord = new ArrayList<Object>();
				//int crc = -1;
				
				switch (type) {
				case Collectors:
					Collector collector = (Collector) entry;
					
					internalRecord.add(collector.getId());
					internalRecord.add(collector.getIp());
					internalRecord.add(collector.getRadios());
					internalRecord.add(collector.getNetId());
					internalRecord.add(collector.getApp());
					internalRecord.add(collector.getCollectorType());
					internalRecord.add(collector.getLocation());
					internalRecord.add(collector.getNote());
					
					break;
				case HANDevices:
					HANDevice han = (HANDevice) entry;

					internalRecord.add(han.getId());
					internalRecord.add(han.getUnitId());
					internalRecord.add(han.getDeviceName());
					internalRecord.add(han.getInstall());
					internalRecord.add(han.getMAC());
					internalRecord.add(han.getLocation());
					internalRecord.add(han.getNote());
					break;
				case Meters:
					Meter meter = (Meter) entry;

					internalRecord.add(meter.getId());
					internalRecord.add(meter.getLAN());
					internalRecord.add(meter.getLocation());
					internalRecord.add(meter.getSocket());
					internalRecord.add(meter.getNote());
					
					/*System.out.println(internalRecord);
					
					crc = meter.getCRC();
					externalRecord.add(meter.getLAN());
					externalRecord.add(meter.getLocation());
					externalRecord.add(meter.getSocket());*/
					break;
				case Routers:
					Router router = (Router) entry;

					internalRecord.add(router.getId());
					internalRecord.add(router.getLAN());
					internalRecord.add(router.getLocation());
					internalRecord.add(router.getNote());
					
					/*crc = router.getCRC();
					externalRecord.add(router.getLAN());
					externalRecord.add(router.getLocation());*/
					break;
				case Sockets:
					Socket socket = (Socket) entry;
					
					internalRecord.add(socket.getId());
					internalRecord.add(socket.getIdProp());
					internalRecord.add(socket.getForm());
					internalRecord.add(socket.getNLoad());
					internalRecord.add(socket.getLocation());
					break;
				default:
					break;
				}
				
				if ((Integer) internalRecord.get(0) == -1) {
					internalRecord.remove(0);
					
					internalNewAdds.add(internalRecord);
				} else {
					if (type == TableType.Meters || type == TableType.Routers) internalRecord.remove(1);
					internalNewValues.add(internalRecord);
					
					/*if(!externalRecord.isEmpty()) {
						if (!externalMap.containsKey(crc))
							externalMap.put(crc, new ArrayList<List<Object>>());
							
						if (crc != -1) externalMap.get(crc).add(externalRecord);
					}*/
				}
			}
		}
		

		
		List<Integer> deleteIds = new ArrayList<Integer>();
		if (deleteList.get(type) != null) {
			for (Entry entry : deleteList.get(type)) {
				deleteIds.add(entry.getId());
			}
			
			main.getMainDBController().delete(type, deleteIds);
		}
		main.getMainDBController().add(type, internalNewAdds);
		main.getMainDBController().updateTableFromTable(type, internalNewValues);
		/*for (int crc : externalMap.keySet()) {
			main.getEnvironmentController(crc).updateData(type, externalMap.get(crc));
		}*/
	}
}
