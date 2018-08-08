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

public class CollectorsGridController extends DeviceGridController{
	
	@FXML 
	private GridPane collectorsGrid;

	@FXML
	private TableView<Device> collectorsTable;
	
	@FXML
	private TableColumn<Device,String> ipColumn;
	
	@FXML 
	private MenuButton ipFilter;
	
	@FXML
	private TableColumn<Device,String> radiosColumn;
	
	@FXML
	private TableColumn<Device,String> netIdColumn;
	
	@FXML
	private MenuButton networkIDFilter;
	
	@FXML
	private TableColumn<Device,String> appColumn;
	
	@FXML
	private MenuButton collectorAppFilter;
	
	@FXML
	private TableColumn<Device,String> typeColumn;
	
	@FXML
	private MenuButton collectorTypeFilter;
	
	@FXML
	private TableColumn<Device,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	@FXML
	private TableColumn<Device,String> notesColumn;
	
	
	
	@FXML
	private TableView<Device> addCollectorsTable;
	
	@FXML
	private TableColumn<Device,String> addIpColumn;
	
	@FXML
	private TableColumn<Device,String> addRadiosColumn;
	
	@FXML
	private TableColumn<Device,String> addNetIdColumn;
	
	@FXML
	private TableColumn<Device,String> addAppColumn;
	
	@FXML
	private TableColumn<Device,String> addTypeColumn;
	
	@FXML
	private TableColumn<Device,String> addLocColumn;
	
	@FXML
	private TableColumn<Device,String> addNotesColumn;
	
	
	@FXML
	private Button addCollectorsButton;
	
	@FXML
	private Button cancelAddCollectorsButton;
	
	@FXML
	private TextField searchCollectorsField;
	
	
	public CollectorsGridController() {
		super(DeviceType.COLLECTORS);
	}
	

	@Override
	protected void initialize() {
		this.table = collectorsTable;
		this.addTable = addCollectorsTable;
		this.grid = collectorsGrid;

		this.filters.add(ipFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(collectorAppFilter);
		this.filters.add(collectorTypeFilter);
		this.filters.add(locationFilter);
		
		this.columns.add(ipColumn);
		this.columns.add(radiosColumn);
		this.columns.add(netIdColumn);
		this.columns.add(appColumn);
		this.columns.add(typeColumn);
		this.columns.add(locColumn);
		this.columns.add(notesColumn);
		
		this.addColumns.add(addIpColumn);
		this.addColumns.add(addRadiosColumn);
		this.addColumns.add(addNetIdColumn);
		this.addColumns.add(addAppColumn);
		this.addColumns.add(addTypeColumn);
		this.addColumns.add(addLocColumn);
		this.addColumns.add(addNotesColumn);
		
		this.addButton = addCollectorsButton;
		this.cancelAddButton = cancelAddCollectorsButton;
		
		this.searchField = searchCollectorsField;
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
		case 6:
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
		case 6:
			setupEditableAddColumn(i);
		}
	}
}
