package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.presentation.logic.TableController;
import application.presentation.logic.TableController.TableType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Meter implements Entry {
	
	private int id;
	private SimpleStringProperty lan;
	private SimpleStringProperty meterName;
	private SimpleStringProperty meterType;
	private SimpleIntegerProperty netId;
	private SimpleStringProperty otherCRCs;
	private SimpleStringProperty loc;
	private SimpleStringProperty socket;
	private SimpleStringProperty notes;
	
	
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public Property getFieldProperty(int i) {
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

	/*@Override
	public List<Object> getUpdateableValues() {
		List<Object> result = new ArrayList<Object>();
		
		result.add(((Label) children.get(0)).getText());
		result.add(((TextField) children.get(5)).getText());
		result.add(((TextField) children.get(6)).getText());
		
		return result;
	}*/

	/*@Override
	public TableType getType() {
		return TableType.Meters;
	}*/

	/*@Override
	public int getCRC() {
		return Integer.parseInt(((Label) children.get(3)).getText());
	}*/
	
	/*public void init(int id, String lanString, String nameString, String typeString, String netIDString, String otherCRCsString, String locString, String socketString, String commentString) {
		setId(id);
		
		children.add(new Label(lanString));
		children.add(new Label(nameString));
		children.add(new Label(typeString));
		children.add(new Label(netIDString));
		children.add(new Label(otherCRCsString));
		
		TextField locField = new TextField(locString);
		locField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(locField);
		
		TextField socketField = new TextField(socketString);
		socketField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(socketField);
		
		children.add(new Label(commentString));
	}*/
}

