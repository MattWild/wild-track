package application.presentation.logic;

import application.objects.hardware.Device;
import application.objects.hardware.Device.DeviceType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HANGridController extends DeviceGridController {
	
	@FXML
	private GridPane hanGrid;
	
	@FXML
	private TableView<Device> hanTable;
	
	@FXML
	private TableColumn<Device,String> unitIdColumn;
	
	@FXML
	private MenuButton unitIdFilter;
	
	@FXML
	private TableColumn<Device,String> nameColumn;
	
	@FXML
	private MenuButton deviceNameFilter;
	
	@FXML
	private TableColumn<Device,String> installColumn;
	
	@FXML
	private TableColumn<Device,String> macColumn;
	
	@FXML
	private TableColumn<Device,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Device,String> notesColumn;
	
	
	@FXML
	private TableView<Device> addHanTable;
	
	@FXML
	private TableColumn<Device,String> addUnitIdColumn;
	
	@FXML
	private TableColumn<Device,String> addNameColumn;
	
	@FXML
	private TableColumn<Device,String> addInstallColumn;
	
	@FXML
	private TableColumn<Device,String> addMacColumn;
	
	@FXML
	private TableColumn<Device,String> addLocColumn;
	
	@FXML
	private TableColumn<Device,String> addNotesColumn;
	
	
	@FXML
	private Button addHANButton;
	
	@FXML
	private Button cancelAddHANButton;
	
	@FXML
	private TextField searchHANField;
	
	public HANGridController() {
		super(DeviceType.HAN_DEVICES);
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
		
		this.searchField = searchHANField;
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
