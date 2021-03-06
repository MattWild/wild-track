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

public class MetersGridController extends DeviceGridController{

	@FXML
	private GridPane metersGrid;
	
	@FXML
	private TableView<Device> metersTable;
	
	@FXML
	private TableColumn<Device,String> lanColumn;
	
	@FXML 
	private MenuButton lanFilter;
	
	@FXML
	private TableColumn<Device,String> meterNameColumn;
	
	@FXML
	private MenuButton meterNameFilter;
	
	@FXML
	private TableColumn<Device,String> meterTypeColumn;
	
	@FXML
	private MenuButton meterTypeFilter;
	
	@FXML
	private TableColumn<Device,String> netIdColumn;
	
	@FXML
	private MenuButton networkIDFilter;
	
	@FXML
	private TableColumn<Device,String> otherCRCsColumn;
	
	@FXML
	private TableColumn<Device,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Device,String> socketColumn;
	
	@FXML
	private MenuButton socketFilter;
	
	@FXML
	private TableColumn<Device,String> notesColumn;
	

	
	@FXML
	private TableView<Device> addMetersTable;
	
	@FXML
	private TableColumn<Device,String> addLanColumn;
	
	@FXML
	private TableColumn<Device,String> addMeterNameColumn;
	
	@FXML
	private TableColumn<Device,String> addMeterTypeColumn;
	
	@FXML
	private TableColumn<Device,String> addNetIdColumn;
	
	@FXML
	private TableColumn<Device,String> addOtherCRCsColumn;
	
	@FXML
	private TableColumn<Device,String> addLocColumn;
	
	@FXML
	private TableColumn<Device,String> addSocketColumn;
	
	@FXML
	private TableColumn<Device,String> addNotesColumn;
	
	@FXML
	private Button addMeterButton;
	
	@FXML
	private Button cancelAddMeterButton;
	
	
	@FXML
	private TextField searchMetersField;
	
	public MetersGridController() {
		super(DeviceType.METERS);
	}
	

	@Override
	protected void initialize() {
		this.table = metersTable;
		this.addTable = addMetersTable;
		this.grid = metersGrid;

		this.filters.add(lanFilter);
		this.filters.add(meterNameFilter);
		this.filters.add(meterTypeFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(locationFilter);
		this.filters.add(socketFilter);
		
		this.columns.add(lanColumn);
		this.columns.add(meterNameColumn);
		this.columns.add(meterTypeColumn);
		this.columns.add(netIdColumn);
		this.columns.add(otherCRCsColumn);
		this.columns.add(locColumn);
		this.columns.add(socketColumn);
		this.columns.add(notesColumn);
		
		this.addColumns.add(addLanColumn);
		this.addColumns.add(addMeterNameColumn);
		this.addColumns.add(addMeterTypeColumn);
		this.addColumns.add(addNetIdColumn);
		this.addColumns.add(addOtherCRCsColumn);
		this.addColumns.add(addLocColumn);
		this.addColumns.add(addSocketColumn);
		this.addColumns.add(addNotesColumn);
		
		this.addButton = addMeterButton;
		this.cancelAddButton = cancelAddMeterButton;
		
		this.searchField = searchMetersField;
	}


	@Override
	protected void optionalColumnSetup(int i) {
		switch (i) {
		case 5:
		case 6:
		case 7:
			setupEditableColumn(i);
			break;
		}
	}


	@Override
	protected void optionalAddColumnSetup(int i) {
		switch (i) {
		case 0:
		case 5:
		case 6:
			setupEditableAddColumn(i);
			break;
		default:
			setupNonEditableAddColumn(i);
		}
	}
}
