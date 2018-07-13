package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry.FieldUpdateHandler;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Socket extends Entry {

	public Socket(Main app) {
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
		
		return result;
	}

	@Override
	public TableType getType() {
		return TableType.Sockets;
	}

	@Override
	public int getCRC() {
		return -1;
	}

	public void init(int id, String idString, String formString, String nloadString, String locString) {
		setId(id);
		children.add(new Label(idString));
		
		TextField formField = new TextField(formString);
		formField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(formField);
		
		TextField nloadField = new TextField(nloadString);
		nloadField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(nloadField);
				
		TextField locField = new TextField(locString);
		locField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(locField);
	}
}
