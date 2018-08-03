package application.objects.entities;

import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;

public class Router implements Device {
	
	private int id;
	private SimpleStringProperty lan;
	private SimpleStringProperty type;
	private SimpleStringProperty netId;
	private SimpleStringProperty otherCRCs;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;
	
	private boolean changed;
	
	public Router(int id, String lanString, Integer netIDvalue, String otherCRCsString, String typeString, String locString, String commentString) {
		this.id = id;
		
		lan = new SimpleStringProperty(lanString);
		netId = new SimpleStringProperty((netIDvalue == null)? null : netIDvalue.intValue() + "");
		otherCRCs = new SimpleStringProperty(otherCRCsString);
		type = new SimpleStringProperty(typeString);
		loc = new SimpleStringProperty(locString);
		notes = new SimpleStringProperty(commentString);
	}

	@Override
	public TableType getType() {
		return TableType.Routers;
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
	public SimpleStringProperty getFieldProperty(int i) {
		switch (i) {
		case 0:
			return lan;		
		case 1:
			return netId;		
		case 2:
			return otherCRCs;		
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
	public String getFilterValue(int i) {
		switch (i) {
		case 0:
			return lan.get();		
		case 1:
			return netId.get();		
		case 2:
			return type.get();			
		case 3:
			return loc.get();	
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

	public String getLocation() {
		return loc.get();
	}

	public String getLAN() {
		return lan.get();
	}

	public int getCRC() {
		return (netId.get() == null)? -1 : Integer.parseInt(netId.get());
	}

	@Override
	public boolean identifierNotNull() {
		return lan.get() != null;
	}

	public String getNote() {
		return notes.get();
	}
}
