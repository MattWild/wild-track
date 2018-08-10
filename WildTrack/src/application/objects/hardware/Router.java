package application.objects.hardware;

import javafx.beans.property.SimpleStringProperty;

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
