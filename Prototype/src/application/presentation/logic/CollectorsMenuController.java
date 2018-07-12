package application.presentation.logic;

import java.util.List;

import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

public class CollectorsMenuController extends MenuController {

	public CollectorsMenuController() {
		super(TableController.TableType.Collectors);
	}

	@FXML 
	private ScrollPane collectorsPane;
	
	@FXML
	private MenuBar collectorsMenu;

	@Override
	protected String getChildString() {
		return "presentation/view/CollectorsGrid.fxml";
	}
	
	
	@Override
	public void initialize() {
		scroll = collectorsPane;
		menu = collectorsMenu;
	}
	
	@FXML
	private void refresh() {
		controller.refresh();
	}
	
	@FXML
	private void add() {
		main.getPresentationLayer().showAddDevice(TableType.Collectors);
	}
	
	@FXML
	private void delete() {
		List<Entry> entries = controller.getSelected();
		
		if (entries.size() > 0) 
			main.getPresentationLayer().showDeleteDevice(entries, TableType.Collectors);
	}

}
