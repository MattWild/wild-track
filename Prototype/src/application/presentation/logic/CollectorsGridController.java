package application.presentation.logic;

import java.util.ArrayList;
import java.util.List;

import application.entities.Collector;
import application.entities.Entry;
import application.entities.Meter;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

public class CollectorsGridController extends TableController{

	@FXML
	private GridPane collectorsGrid;
	
	@FXML 
	private ChoiceBox<String> ipFilter;
	
	@FXML
	private ChoiceBox<String> networkIDFilter;
	
	@FXML
	private ChoiceBox<String> collectorAppFilter;
	
	@FXML
	private ChoiceBox<String> collectorTypeFilter;
	
	@FXML
	private ChoiceBox<String> locationFilter;
	
	
	public CollectorsGridController() {
		super(TableController.TableType.Collectors);
	}
	

	@Override
	protected void initialize() {
		this.grid = collectorsGrid;

		this.filters.add(ipFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(collectorAppFilter);
		this.filters.add(collectorTypeFilter);
		this.filters.add(locationFilter);
	}

	protected List<String> getFilters() {
		List<String> result = new ArrayList<String>();
		
		result.add(ipFilter.getSelectionModel().getSelectedItem());
		result.add(networkIDFilter.getSelectionModel().getSelectedItem());
		result.add(collectorAppFilter.getSelectionModel().getSelectedItem());
		result.add(collectorTypeFilter.getSelectionModel().getSelectedItem());
		result.add(locationFilter.getSelectionModel().getSelectedItem());
		
		return result;
	}


	@Override
	protected Entry generateEntry(List<String> fields) {
		return new Collector(main, fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5), fields.get(6));
	}
}
