package application.objects.entities;

import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;

public class Socket implements Entry {
	
	private int id;
	private SimpleStringProperty idProp;
	private SimpleStringProperty form;
	private SimpleStringProperty nload;
	private SimpleStringProperty loc;
	
	private boolean changed;

	public Socket(int id, Integer idNum, String formString, String nloadString, String locString) {
		this.id = id;
		
		idProp = new SimpleStringProperty(idNum.toString());
		form = new SimpleStringProperty(formString);
		nload = new SimpleStringProperty(nloadString);
		loc = new SimpleStringProperty(locString);
	}

	@Override
	public TableType getType() {
		return TableType.Sockets;
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
	public String getFilterValue(int i) {
		switch (i) {
		case 0:
			return idProp.get();
		case 1:
			return form.get();
		case 2:
			return nload.get();
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

	@Override
	public boolean identifierNotNull() {
		return idProp.get() != null;
	}
}
