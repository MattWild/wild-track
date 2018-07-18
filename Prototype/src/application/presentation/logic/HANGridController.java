package application.presentation.logic;

import application.objects.entities.Entry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class HANGridController extends TableController {
	
	@FXML
	private GridPane hanGrid;
	
	@FXML
	private TableView<Entry> hanTable;
	
	@FXML
	private TableColumn<Entry,String> unitIdColumn;
	
	@FXML
	private MenuButton unitIdFilter;
	
	@FXML
	private TableColumn<Entry,String> nameColumn;
	
	@FXML
	private MenuButton deviceNameFilter;
	
	@FXML
	private TableColumn<Entry,String> installColumn;
	
	@FXML
	private TableColumn<Entry,String> macColumn;
	
	@FXML
	private TableColumn<Entry,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Entry,String> notesColumn;
	
	
	@FXML
	private TableView<Entry> addHanTable;
	
	@FXML
	private TableColumn<Entry,String> addUnitIdColumn;
	
	@FXML
	private TableColumn<Entry,String> addNameColumn;
	
	@FXML
	private TableColumn<Entry,String> addInstallColumn;
	
	@FXML
	private TableColumn<Entry,String> addMacColumn;
	
	@FXML
	private TableColumn<Entry,String> addLocColumn;
	
	@FXML
	private TableColumn<Entry,String> addNotesColumn;
	
	
	@FXML
	private Button addHANButton;
	
	@FXML
	private Button cancelAddHANButton;
	
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
	
	public HANGridController() {
		super(TableController.TableType.HANDevices);
	}
	

	@Override
	protected void initialize() {
		this.table = hanTable;
		this.addTable = addHanTable;
		this.grid = hanGrid;

		this.filters.add(unitIdFilter);
		this.filters.add(deviceNameFilter);
		this.filters.add(locationFilter);
		
		this.columns.add(unitIdColumn);
		this.columns.add(nameColumn);
		this.columns.add(installColumn);
		this.columns.add(macColumn);
		this.columns.add(locColumn);
		this.columns.add(notesColumn);
		
		this.addColumns.add(addUnitIdColumn);
		this.addColumns.add(addNameColumn);
		this.addColumns.add(addInstallColumn);
		this.addColumns.add(addMacColumn);
		this.addColumns.add(addLocColumn);
		this.addColumns.add(addNotesColumn);
		
		this.addButton = addHANButton;
		this.cancelAddButton = cancelAddHANButton;
	}


	@Override
	protected void optionalColumnSetup(int i) {
		switch (i) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			setupEditableColumn(i);
		}
	}


	@Override
	protected void optionalAddColumnSetup(int i) {
		switch (i) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			setupEditableAddColumn(i);
		}
	}
}
