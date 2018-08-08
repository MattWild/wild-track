package application.objects.hardware;

import java.util.Comparator;

import application.objects.environment.DeviceEnvironmentRelationship;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;

public class Router extends Device {
	
	private SimpleStringProperty lan;
	private SimpleStringProperty type;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;
	
	public Router(int id, String lanString, String typeString, String locString, String commentString) {
		super(id);
		
		lan = new SimpleStringProperty(lanString);
		type = new SimpleStringProperty(typeString);
		loc = new SimpleStringProperty(locString);
		notes = new SimpleStringProperty(commentString);
		
		environmentRelationships = new SortedList<DeviceEnvironmentRelationship>(FXCollections.observableArrayList(), new Comparator<DeviceEnvironmentRelationship>() {
			@Override
			public int compare(DeviceEnvironmentRelationship o1, DeviceEnvironmentRelationship o2) {
				
				if (o1 == null || o1.getLatestReadDate() == null) 
					if (o2  == null || o2.getLatestReadDate() == null)
						return 0;
					else
						return -1;
				else
					if (o2 == null || o2.getLatestReadDate() == null)
						return 1;
					else {
						int value = o1.getLatestReadDate().compareTo(o2.getLatestReadDate());
				
						if (value == 0)
							return 0;
						else if (value < 0)
							return -1;
						else
							return 1;
					}
			}
		});
	}

	@Override
	public DeviceType getType() {
		return DeviceType.ROUTERS;
	}
	
	@Override
	public String getIdentifier() {
		return lan.get();
	}

	@Override
	public boolean identifierNotNull() {
		return lan.get() != null;
	}

	@Override
	public SimpleStringProperty getFieldProperty(int i) {
		switch (i) {
		case 0:
			return lan;		
		case 1:
			return crcProp;		
		case 2:
			return otherCRCsProp;		
		case 3:
			return type;		
		case 4:
			return loc;
		case 5:
			return notes;
		default:
			return null;
		}
	}

	@Override
	public SimpleStringProperty getFilterProperty(int i) {
		switch (i) {
		case 0:
			return lan;		
		case 1:
			return crcProp;		
		case 2:
			return type;			
		case 3:
			return loc;	
		default:
			return null;
		}
	}

	public String getLocation() {
		return loc.get();
	}

	public String getLAN() {
		return lan.get();
	}

	public String getNote() {
		return notes.get();
	}
}
