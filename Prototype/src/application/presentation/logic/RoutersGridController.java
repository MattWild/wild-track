package application.presentation.logic;

import application.objects.entities.Entry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class RoutersGridController extends TableController {
	
	
	@FXML
	private GridPane routersGrid;
	
	
	@FXML
	private TableView<Entry> routersTable;
	
	@FXML
	private TableColumn<Entry,String> lanColumn;
	
	@FXML 
	private MenuButton lanFilter;
	
	@FXML
	private TableColumn<Entry,String> netIdColumn;
	
	@FXML
	private MenuButton networkIDFilter;
	
	@FXML
	private TableColumn<Entry,String> otherCRCsColumn;
	
	@FXML
	private TableColumn<Entry,String> typeColumn;
	
	@FXML
	private MenuButton radioTypeFilter;
	
	@FXML
	private TableColumn<Entry,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Entry,String> notesColumn;
	
	
	
	@FXML
	private TableView<Entry> addRoutersTable;
	
	@FXML
	private TableColumn<Entry,String> addLanColumn;
	
	@FXML
	private TableColumn<Entry,String> addNetIdColumn;
	
	@FXML
	private TableColumn<Entry,String> addOtherCRCsColumn;
	
	@FXML
	private TableColumn<Entry,String> addTypeColumn;
	
	@FXML
	private TableColumn<Entry,String> addLocColumn;
	
	@FXML
	private TableColumn<Entry,String> addNotesColumn;
	
	
	
	@FXML
	private Button addRoutersButton;
	
	@FXML
	private Button cancelAddRoutersButton;
	
	
	@FXML
	private void save(ActionEvent event) {
		save();
	}
	
	@FXML
	private void refresh(ActionEvent event) {
		refresh();
	}
	
	@FXML
	private void add(ActionEvent event) {
		startAdd();
	}
	
	@FXML
	private void remove(ActionEvent event) {
		remove();
	}
	
	public RoutersGridController() {
		super(TableController.TableType.Routers);
	}
	

	@Override
	protected void initialize() {
		this.table = routersTable;
		this.addTable = addRoutersTable;
		this.grid = routersGrid;

		this.filters.add(lanFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(radioTypeFilter);
		this.filters.add(locationFilter);
		
		this.columns.add(lanColumn);
		this.columns.add(netIdColumn);
		this.columns.add(otherCRCsColumn);
		this.columns.add(typeColumn);
		this.columns.add(locColumn);
		this.columns.add(notesColumn);
		
		this.addColumns.add(addLanColumn);
		this.addColumns.add(addNetIdColumn);
		this.addColumns.add(addOtherCRCsColumn);
		this.addColumns.add(addTypeColumn);
		this.addColumns.add(addLocColumn);
		this.addColumns.add(addNotesColumn);
		
		this.addButton = addRoutersButton;
		this.cancelAddButton = cancelAddRoutersButton;
	}

	@Override
	protected void optionalColumnSetup(int i) {
		switch (i) {
		case 4:
		case 5:
			setupEditableColumn(i);
		}
	}


	@Override
	protected void optionalAddColumnSetup(int i) {
		switch (i) {
		case 0:
		case 4:
			setupEditableAddColumn(i);
			break;
		default:
			setupNonEditableAddColumn(i);
			break;
		}
	}
}
