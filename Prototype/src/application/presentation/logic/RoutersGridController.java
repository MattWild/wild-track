package application.presentation.logic;

import java.util.ArrayList;
import java.util.List;

import application.entities.Entry;
import application.entities.Meter;
import application.entities.Router;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class RoutersGridController extends TableController {
	@FXML
	private GridPane routersGrid;
	
	@FXML
	private ComboBox<String> radioTypeFilter;
	
	@FXML
	private ComboBox<String> networkIDFilter;
	
	@FXML
	private ComboBox<String> locationFilter;
	
	@FXML 
	private ComboBox<String> lanFilter;
	
	
	public RoutersGridController() {
		super(TableController.TableType.Routers);
	}
	

	@Override
	protected void initialize() {
		this.grid = routersGrid;

		this.filters.add(lanFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(radioTypeFilter);
		this.filters.add(locationFilter);
	}

	protected List<String> getFilters() {
		List<String> result = new ArrayList<String>();
		
		result.add(lanFilter.getSelectionModel().getSelectedItem());
		result.add(networkIDFilter.getSelectionModel().getSelectedItem());
		result.add(radioTypeFilter.getSelectionModel().getSelectedItem());
		result.add(locationFilter.getSelectionModel().getSelectedItem());
		
		return result;
	}


	@Override
	protected Entry generateEntry(List<String> fields) {
		return new Router(main, fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
	}
}
