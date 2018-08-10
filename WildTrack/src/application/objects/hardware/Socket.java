package application.objects.hardware;

import javafx.beans.property.SimpleStringProperty;

public class Socket extends Device {

	private SimpleStringProperty idProp;
	private SimpleStringProperty form;
	private SimpleStringProperty nload;
	private SimpleStringProperty loc;

	public Socket(int id, Integer idNum, String formString, String nloadString, String locString) {
		super(id);
		
		idProp = new SimpleStringProperty(idNum.toString());
		form = new SimpleStringProperty(formString);
		nload = new SimpleStringProperty(nloadString);
		loc = new SimpleStringProperty(locString);
	}

	@Override
	public DeviceType getType() {
		return DeviceType.SOCKETS;
	}
	
	@Override
	public String getIdentifier() {
		return idProp.get();
	}

	@Override
	public boolean identifierNotNull() {
		return idProp.get() != null;
	}

	@Override
	public SimpleStringProperty getFieldProperty(int i) {
		switch (i) {
		case 0:
			return idProp;
		case 1:
			return form;
		case 2:
			return nload;
		case 3:
			return loc;
		default:
			return null;
		}
	}

	@Override
	public SimpleStringProperty getFilterProperty(int i) {
		switch (i) {
		case 0:
			return idProp;
		case 1:
			return form;
		case 2:
			return nload;
		case 3:
			return loc;
		default:
			return null;
		}
	}

	public String getIdProp() {
		return idProp.get();
	}

	public String getForm() {
		return form.get();
	}

	public String getNLoad() {
		return nload.get();
	}

	public String getLocation() {
		return loc.get();
	}
}
