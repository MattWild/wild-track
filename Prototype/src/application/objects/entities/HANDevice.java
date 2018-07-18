package application.objects.entities;

import application.presentation.logic.TableController.TableType;
import javafx.beans.property.SimpleStringProperty;

public class HANDevice implements Entry {
	
	private int id;
	private SimpleStringProperty unitId;
	private SimpleStringProperty name;
	private SimpleStringProperty install;
	private SimpleStringProperty mac;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;
	
	private boolean changed;

	public HANDevice(int id, String unitIDString, String deviceNameString, String installCodeString, String macAddressString,String locString, String commentString) {
		this.id = id;
		
		unitId = new SimpleStringProperty(unitIDString);
		name = new SimpleStringProperty(deviceNameString);
		install = new SimpleStringProperty(installCodeString);
		mac = new SimpleStringProperty(macAddressString);
		loc = new SimpleStringProperty(locString);
		notes = new SimpleStringProperty(commentString);
	}

	@Override
	public TableType getType() {
		return TableType.HANDevices;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getFieldProperty(int i) {
		switch (i) {
		case 0:
			return unitId;
		case 1:
			return name;
		case 2:
			return install;
		case 3:
			return mac;
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
		switch(i) {
		case 0:
			return unitId.get();
		case 1:
			return name.get();
		case 2:
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

	public String getUnitId() {
		return unitId.get();
	}

	public String getDeviceName() {
		return name.get();
	}

	public String getInstall() {
		return install.get();
	}

	public String getMAC() {
		return mac.get();
	}

	public String getLocation() {
		return loc.get();
	}

	public String getNote() {
		return notes.get();
	}

	@Override
	public boolean identifierNotNull() {
		return unitId.get() != null;
	}

	
}
