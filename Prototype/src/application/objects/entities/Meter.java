package application.objects.entities;

import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;

public class Meter implements Entry {
	
	private int id;
	private SimpleStringProperty lan;
	private SimpleStringProperty meterName;
	private SimpleStringProperty meterType;
	private SimpleStringProperty netId;
	private SimpleStringProperty otherCRCs;
	private SimpleStringProperty loc;
	private SimpleStringProperty socket;
	private SimpleStringProperty notes;
	
	private boolean changed;
	
	public Meter(int id, String lanString, String nameString, String typeString, Integer netIDvalue, String otherCRCsString, String locString, String socketString, String commentString) {
		this.id = id;
		
		lan = new SimpleStringProperty(lanString);
		meterName = new SimpleStringProperty(nameString);
		meterType = new SimpleStringProperty(typeString);
		netId = new SimpleStringProperty((netIDvalue == null)? null : netIDvalue.intValue() + "");
		otherCRCs = new SimpleStringProperty(otherCRCsString);
		loc = new SimpleStringProperty(locString);
		socket = new SimpleStringProperty(socketString);
		notes = new SimpleStringProperty(commentString);
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
	public TableType getType() {
		return TableType.Meters;
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
			return netId;		
		case 4:
			return otherCRCs;		
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
	public String getFilterValue(int i) {
		switch (i) {
		case 0:
			return lan.get();		
		case 1:
			return meterName.get();		
		case 2:
			return meterType.get();		
		case 3:
			return netId.get();			
		case 4:
			return loc.get();		
		case 5:
			return socket.get();
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


	public String getLAN() {
		return lan.get();
	}


	public String getLocation() {
		return loc.get();
	}


	public String getSocket() {
		return socket.get();
	}


	public int getCRC() {
		return (netId.get() == null)? -1 : Integer.parseInt((netId.get()));
	}


	@Override
	public boolean identifierNotNull() {
		return lan.get() != null;
	}


	public String getNote() {
		return notes.get();
	}
}

