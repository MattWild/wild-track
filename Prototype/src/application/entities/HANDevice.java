package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry.FieldUpdateHandler;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HANDevice extends Entry {

	public HANDevice(Main app) {
		super(app);
		children = new ArrayList<Node>();
	}

	@Override
	public List<Object> getUpdateableValues() {
		List<Object> result = new ArrayList<Object>();
		
		result.add(((Label) children.get(0)).getText());
		result.add(((TextField) children.get(1)).getText());
		result.add(((TextField) children.get(2)).getText());
		result.add(((TextField) children.get(3)).getText());
		result.add(((TextField) children.get(4)).getText());
		result.add(((TextField) children.get(5)).getText());
		
		return result;
	}

	@Override
	public TableType getType() {
		return TableType.HANDevices;
	}

	@Override
	public int getCRC() {
		return -1;
	}

	public void init(int id, String unitIDString, String deviceNameString, String installCodeString, String macAddressString,String locString, String commentString) {
		setId(id);
		
		children.add(new Label(unitIDString));
		TextField deviceNameField = new TextField(deviceNameString);
		deviceNameField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(deviceNameField);
		
		TextField installCodeField = new TextField(installCodeString);
		installCodeField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(installCodeField);
		
		TextField macAddressField = new TextField(macAddressString);
		macAddressField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(macAddressField);
				
		TextField locField = new TextField(locString);
		locField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(locField);

		TextField commentField = new TextField(commentString);
		commentField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(commentField);
	}
}
