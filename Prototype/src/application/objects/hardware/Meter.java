package application.objects.hardware;

import javafx.beans.property.SimpleStringProperty;

public class Meter extends Device {

	private SimpleStringProperty lan;
	private SimpleStringProperty meterName;
	private SimpleStringProperty meterType;
	private SimpleStringProperty loc;
	private SimpleStringProperty socket;
	private SimpleStringProperty notes;
	
	public Meter(int id, String lanString, String nameString, String typeString, String locString, String socketString, String commentString) {
		super(id);
		
		lan = new SimpleStringProperty(lanString);
		meterName = new SimpleStringProperty(nameString);
		meterType = new SimpleStringProperty(typeString);
		loc = new SimpleStringProperty(locString);
		socket = new SimpleStringProperty(socketString);
		notes = new SimpleStringProperty(commentString);
	}
	
	@Override
	public DeviceType getType() {
		return DeviceType.METERS;
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
			return meterName;		
		case 2:
			return meterType;		
		case 3:
			return crcProp;		
		case 4:
			return otherCRCsProp;		
		case 5:
			return loc;		
		case 6:
			return socket;
		case 7:
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
			return meterName;		
		case 2:
			return meterType;		
		case 3:
			return crcProp;			
		case 4:
			return loc;		
		case 5:
			return socket;
		default:
			return null;
		}
	}


	public String getLAN() {
		return lan.get();
	}


	public String getLocation() {
		return loc.get();
	}


	public String getSocket() {
		return socket.get();
	}


	public String getNote() {
		return notes.get();
	}
}

