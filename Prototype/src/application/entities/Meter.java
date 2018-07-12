package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry.FieldUpdateHandler;
import application.presentation.logic.TableController;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Meter extends Entry {
	
	public Meter(Main app, String lanString, String nameString, String typeString, String netIDString, String otherCRCsString, String locString, String socketString, String commentString) {
		super(app);
		children = new ArrayList<Node>();
		
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
	}

	@Override
	public List<Object> getUpdateableValues() {
		List<Object> result = new ArrayList<Object>();
		
		result.add(((Label) children.get(0)).getText());
		result.add(((TextField) children.get(5)).getText());
		result.add(((TextField) children.get(6)).getText());
		
		return result;
	}

	@Override
	public TableType getType() {
		return TableType.Meters;
	}

	@Override
	public int getCRC() {
		return Integer.parseInt(((Label) children.get(3)).getText());
	}
}
