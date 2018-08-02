package application.presentation.logic;
import application.objects.entities.Entry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class MetersGridController extends DeviceGridController{

	@FXML
	private GridPane metersGrid;
	
	@FXML
	private TableView<Entry> metersTable;
	
	@FXML
	private TableColumn<Entry,String> lanColumn;
	
	@FXML 
	private MenuButton lanFilter;
	
	@FXML
	private TableColumn<Entry,String> meterNameColumn;
	
	@FXML
	private MenuButton meterNameFilter;
	
	@FXML
	private TableColumn<Entry,String> meterTypeColumn;
	
	@FXML
	private MenuButton meterTypeFilter;
	
	@FXML
	private TableColumn<Entry,String> netIdColumn;
	
	@FXML
	private MenuButton networkIDFilter;
	
	@FXML
	private TableColumn<Entry,String> otherCRCsColumn;
	
	@FXML
	private TableColumn<Entry,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Entry,String> socketColumn;
	
	@FXML
	private MenuButton socketFilter;
	
	@FXML
	private TableColumn<Entry,String> notesColumn;
	

	
	@FXML
	private TableView<Entry> addMetersTable;
	
	@FXML
	private TableColumn<Entry,String> addLanColumn;
	
	@FXML
	private TableColumn<Entry,String> addMeterNameColumn;
	
	@FXML
	private TableColumn<Entry,String> addMeterTypeColumn;
	
	@FXML
	private TableColumn<Entry,String> addNetIdColumn;
	
	@FXML
	private TableColumn<Entry,String> addOtherCRCsColumn;
	
	@FXML
	private TableColumn<Entry,String> addLocColumn;
	
	@FXML
	private TableColumn<Entry,String> addSocketColumn;
	
	@FXML
	private TableColumn<Entry,String> addNotesColumn;
	
	@FXML
	private Button addMeterButton;
	
	@FXML
	private Button cancelAddMeterButton;
	
	
	@FXML
	private TextField searchMetersField;
	
	public MetersGridController() {
		super(DeviceGridController.TableType.Meters);
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
