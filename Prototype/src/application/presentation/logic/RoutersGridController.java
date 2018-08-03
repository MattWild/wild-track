package application.presentation.logic;

import application.objects.entities.Device;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RoutersGridController extends DeviceGridController {
	
	
	@FXML
	private GridPane routersGrid;
	
	
	@FXML
	private TableView<Device> routersTable;
	
	@FXML
	private TableColumn<Device,String> lanColumn;
	
	@FXML 
	private MenuButton lanFilter;
	
	@FXML
	private TableColumn<Device,String> netIdColumn;
	
	@FXML
	private MenuButton networkIDFilter;
	
	@FXML
	private TableColumn<Device,String> otherCRCsColumn;
	
	@FXML
	private TableColumn<Device,String> typeColumn;
	
	@FXML
	private MenuButton radioTypeFilter;
	
	@FXML
	private TableColumn<Device,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Device,String> notesColumn;
	
	
	
	@FXML
	private TableView<Device> addRoutersTable;
	
	@FXML
	private TableColumn<Device,String> addLanColumn;
	
	@FXML
	private TableColumn<Device,String> addNetIdColumn;
	
	@FXML
	private TableColumn<Device,String> addOtherCRCsColumn;
	
	@FXML
	private TableColumn<Device,String> addTypeColumn;
	
	@FXML
	private TableColumn<Device,String> addLocColumn;
	
	@FXML
	private TableColumn<Device,String> addNotesColumn;
	
	
	
	@FXML
	private Button addRoutersButton;
	
	@FXML
	private Button cancelAddRoutersButton;
	
	
	@FXML
	private TextField searchRoutersField;
	
	public RoutersGridController() {
		super(DeviceGridController.TableType.Routers);
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
		
		this.searchField = searchRoutersField;
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
