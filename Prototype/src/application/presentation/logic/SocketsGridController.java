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

public class SocketsGridController extends DeviceGridController {
	
	@FXML
	private GridPane socketsGrid;
	
	@FXML
	private TableView<Device> socketsTable;
	
	@FXML
	private TableColumn<Device,String> idColumn;
	
	@FXML
	private MenuButton idFilter;
	
	@FXML
	private TableColumn<Device,String> formColumn;
	
	@FXML
	private MenuButton formFilter;
	
	@FXML
	private TableColumn<Device,String> nloadColumn;
	
	@FXML
	private MenuButton nloadFilter;
	
	@FXML
	private TableColumn<Device,String> locColumn;
	
	@FXML
	private MenuButton locationFilter;
	
	
	@FXML
	private TableView<Device> addSocketsTable;
	
	@FXML
	private TableColumn<Device,String> addIdColumn;
	
	@FXML
	private TableColumn<Device,String> addFormColumn;
	
	@FXML
	private TableColumn<Device,String> addNloadColumn;
	
	@FXML
	private TableColumn<Device,String> addLocColumn;
	
	@FXML
	private Button addSocketsButton;
	
	@FXML
	private Button cancelAddSocketsButton;
	
	@FXML
	private TextField searchSocketsField;
	
	public SocketsGridController() {
		super(DeviceGridController.TableType.Sockets);
	}
	

	@Override
	protected void initialize() {
		this.table = socketsTable;
		this.addTable = addSocketsTable;
		this.grid = socketsGrid;

		this.filters.add(idFilter);
		this.filters.add(formFilter);
		this.filters.add(nloadFilter);
		this.filters.add(locationFilter);
		
		this.columns.add(idColumn);
		this.columns.add(formColumn);
		this.columns.add(nloadColumn);
		this.columns.add(locColumn);
		
		this.addColumns.add(addIdColumn);
		this.addColumns.add(addFormColumn);
		this.addColumns.add(addNloadColumn);
		this.addColumns.add(addLocColumn);
		
		this.addButton = addSocketsButton;
		this.cancelAddButton = cancelAddSocketsButton;
		
		this.searchField = searchSocketsField;
	}


	@Override
	protected void optionalColumnSetup(int i) {
		switch (i) {
		case 1:
		case 2:
		case 3:
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
			setupEditableAddColumn(i);
		}
	}
}
