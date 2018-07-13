package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry.FieldUpdateHandler;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Collector extends Entry {

	public Collector(Main app) {
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
		result.add(((TextField) children.get(6)).getText());
		
		return result;
	}

	@Override
	public TableType getType() {
		return TableType.Collectors;
	}

	@Override
	public int getCRC() {
		try {
			return Integer.parseInt(((TextField) children.get(2)).getText());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public void init(int id, String ipString, String radiosString, String netIDString, String appString, String typeString, String locString, String commentString) {
		setId(id);
		
		children.add(new Label(ipString));
		TextField radiosField = new TextField(radiosString);
		radiosField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(radiosField);
		
		TextField netIDField = new TextField(netIDString);
		netIDField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(netIDField);
		
		TextField appField = new TextField(appString);
		appField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(appField);
		
		TextField typeField = new TextField(typeString);
		typeField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(typeField);
				
		TextField locField = new TextField(locString);
		locField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(locField);

		TextField commentField = new TextField(commentString);
		commentField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(commentField);
	}

}
