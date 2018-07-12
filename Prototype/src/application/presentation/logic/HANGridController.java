package application.presentation.logic;

import java.util.ArrayList;
import java.util.List;

import application.entities.Entry;
import application.entities.HANDevice;
import application.entities.Meter;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

public class HANGridController extends TableController {
	@FXML
	private GridPane HANGrid;
	
	@FXML
	private ChoiceBox<String> unitIDFilter;
	
	@FXML
	private ChoiceBox<String> deviceNameFilter;
	
	@FXML
	private ChoiceBox<String> locationFilter;
	
	
	public HANGridController() {
		super(TableController.TableType.HANDevices);
	}
	

	@Override
	protected void initialize() {
		this.grid = HANGrid;

		this.filters.add(unitIDFilter);
		this.filters.add(deviceNameFilter);
		this.filters.add(locationFilter);
	}

	protected List<String> getFilters() {
		List<String> result = new ArrayList<String>();
		
		result.add(unitIDFilter.getSelectionModel().getSelectedItem());
		result.add(deviceNameFilter.getSelectionModel().getSelectedItem());
		result.add(locationFilter.getSelectionModel().getSelectedItem());
		
		return result;
	}


	@Override
	protected Entry generateEntry(List<String> fields) {
		return new HANDevice(main, fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
	}
}
