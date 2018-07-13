package application.entities;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry.FieldUpdateHandler;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Router extends Entry {
	
	public Router(Main app) {
		super(app);
		children = new ArrayList<Node>();
	}

	@Override
	public List<Object> getUpdateableValues() {
		List<Object> result = new ArrayList<Object>();
		
		result.add(((Label) children.get(0)).getText());
		result.add(((TextField) children.get(4)).getText());
		
		return result;
	}

	@Override
	public TableType getType() {
		return TableType.Routers;
	}

	@Override
	public int getCRC() {
		return Integer.parseInt(((Label) children.get(1)).getText());
	}
	
	public void init(int id, String lanString, String netIDString, String otherCRCsString, String typeString, String locString, String commentString) {
		setId(id);
		children.add(new Label(lanString));
		children.add(new Label(netIDString));
		children.add(new Label(otherCRCsString));
		children.add(new Label(typeString));
		
		TextField locField = new TextField(locString);
		locField.setOnKeyPressed(new FieldUpdateHandler());
		children.add(locField);
		
		children.add(new Label(commentString));
	}

}
