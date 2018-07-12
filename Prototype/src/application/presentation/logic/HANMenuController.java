package application.presentation.logic;

import java.util.List;

import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

public class HANMenuController extends MenuController {
	public HANMenuController() {
		super(TableController.TableType.HANDevices);
	}

	@FXML 
	private ScrollPane HANPane;
	
	@FXML
	private MenuBar HANMenu;

	@Override
	protected String getChildString() {
		return "presentation/view/HANGrid.fxml";
	}
	
	
	@Override
	public void initialize() {
		scroll = HANPane;
		menu = HANMenu;
	}
	
	@FXML
	private void refresh() {
		controller.refresh();
	}
	
	@FXML
	private void add() {
		main.getPresentationLayer().showAddDevice(TableType.HANDevices);
	}
	
	@FXML
	private void delete() {
		List<Entry> entries = controller.getSelected();
		
		if (entries.size() > 0) 
			main.getPresentationLayer().showDeleteDevice(entries, TableType.HANDevices);
	}
}
