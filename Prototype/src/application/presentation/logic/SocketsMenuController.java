package application.presentation.logic;

import java.util.List;

import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

public class SocketsMenuController extends MenuController {
	public SocketsMenuController() {
		super(TableController.TableType.Sockets);
	}

	@FXML 
	private ScrollPane socketsPane;
	
	@FXML
	private MenuBar socketsMenu;

	@Override
	protected String getChildString() {
		return "presentation/view/SocketsGrid.fxml";
	}
	
	
	@Override
	public void initialize() {
		scroll = socketsPane;
		menu = socketsMenu;
	}
	
	@FXML
	private void refresh() {
		controller.refresh();
	}
	
	@FXML
	private void add() {
		main.getPresentationLayer().showAddDevice(TableType.Sockets);
	}
	
	@FXML
	private void delete() {
		List<Entry> entries = controller.getSelected();
		
		if (entries.size() > 0) 
			main.getPresentationLayer().showDeleteDevice(entries, TableType.Sockets);
	}
}
