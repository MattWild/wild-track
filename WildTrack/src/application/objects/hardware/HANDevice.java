package application.objects.hardware;

import javafx.beans.property.SimpleStringProperty;

public class HANDevice extends Device {

	private SimpleStringProperty unitId;
	private SimpleStringProperty name;
	private SimpleStringProperty install;
	private SimpleStringProperty mac;
	private SimpleStringProperty loc;
	private SimpleStringProperty notes;

	public HANDevice(int id, String unitIDString, String deviceNameString, String installCodeString, String macAddressString,String locString, String commentString) {
		super(id);
		
		unitId = new SimpleStringProperty(unitIDString);
		name = new SimpleStringProperty(deviceNameString);
		install = new SimpleStringProperty(installCodeString);
		mac = new SimpleStringProperty(macAddressString);
		loc = new SimpleStringProperty(locString);
		notes = new SimpleStringProperty(commentString);
	}

	@Override
	public DeviceType getType() {
		return DeviceType.HAN_DEVICES;
	}
	
	@Override
	public String getIdentifier() {
		return unitId.get();
	}

	@Override
	public boolean identifierNotNull() {
		return unitId.get() != null;
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
	public SimpleStringProperty getFilterProperty(int i) {
		switch(i) {
		case 0:
			return unitId;
		case 1:
			return name;
		case 2:
			return loc;
		default:
			return null;
		}
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
}
