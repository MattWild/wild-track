package application.presentation.logic;

import java.util.List;

import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

public class MetersMenuController extends MenuController{

	public MetersMenuController() {
		super(TableController.TableType.Meters);
	}

	@FXML 
	private ScrollPane meterspane;
	
	@FXML
	private MenuBar metersMenu;

	@Override
	protected String getChildString() {
		return "presentation/view/MetersGrid.fxml";
	}
	
	
	@Override
	public void initialize() {
		scroll = meterspane;
		menu = metersMenu;
	}
	
	@FXML
	private void refresh() {
		controller.refresh();
	}
	
	@FXML
	private void add() {
		main.getPresentationLayer().showAddDevice(TableType.Meters);
	}
	
	@FXML
	private void delete() {
		List<Entry> entries = controller.getSelected();
		
		if (entries.size() > 0) 
			main.getPresentationLayer().showDeleteDevice(entries, TableType.Meters);
	}
	
	@FXML 
	private void addNote() {
		List<Entry> entries = controller.getSelected();
		
		switch (entries.size()) {
		case 0:
			main.getPresentationLayer().showError("Incorrect Selection:", "You must select a meter to add a note to.");
			break;
			
		case 1:
			main.getPresentationLayer().showAddNoteToDevice(entries.get(0), TableType.Meters);
			break;
			
		default:
			main.getPresentationLayer().showError("Incorrect Selection", "You cannot add a single note to more than one meter at a time.");
			break;
		}
	}
}
