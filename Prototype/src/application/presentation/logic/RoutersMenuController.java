package application.presentation.logic;

import java.util.List;

import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

public class RoutersMenuController extends MenuController {
	public RoutersMenuController() {
		super(TableController.TableType.Routers);
	}

	@FXML 
	private ScrollPane routersPane;
	
	@FXML
	private MenuBar routersMenu;

	@Override
	protected String getChildString() {
		return "presentation/view/RoutersGrid.fxml";
	}
	
	
	@Override
	public void initialize() {
		scroll = routersPane;
		menu = routersMenu;
	}
	
	@FXML
	private void refresh() {
		controller.refresh();
	}
	
	@FXML
	private void add() {
		main.getPresentationLayer().showAddDevice(TableType.Routers);
	}
	
	@FXML
	private void delete() {
		List<Entry> entries = controller.getSelected();
		
		if (entries.size() > 0) 
			main.getPresentationLayer().showDeleteDevice(entries, TableType.Routers);
	}
	
	@FXML 
	private void addNote() {
		List<Entry> entries = controller.getSelected();
		
		switch (entries.size()) {
		case 0:
			main.getPresentationLayer().showError("Incorrect Selection:", "You must select a router to add a note to.");
			break;
			
		case 1:
			main.getPresentationLayer().showAddNoteToDevice(entries.get(0), TableType.Routers);
			break;
			
		default:
			main.getPresentationLayer().showError("Incorrect Selection", "You cannot add a single note to more than one router at a time.");
			break;
		}
	}
}
