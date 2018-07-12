package application.presentation.logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import application.Main;
import application.database.MainDataController;
import application.entities.Entry;
import application.entities.Meter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MetersGridController extends TableController{

	@FXML
	private GridPane metersGrid;
	
	@FXML
	private ChoiceBox<String> meterNameFilter;
	
	@FXML
	private ChoiceBox<String> meterTypeFilter;
	
	@FXML
	private ChoiceBox<String> networkIDFilter;
	
	@FXML
	private ChoiceBox<String> locationFilter;
	
	@FXML
	private ChoiceBox<String> socketFilter;
	
	@FXML 
	private ChoiceBox<String> lanFilter;
	
	
	public MetersGridController() {
		super(TableController.TableType.Meters);
	}
	

	@Override
	protected void initialize() {
		this.grid = metersGrid;

		this.filters.add(lanFilter);
		this.filters.add(meterNameFilter);
		this.filters.add(meterTypeFilter);
		this.filters.add(networkIDFilter);
		this.filters.add(locationFilter);
		this.filters.add(socketFilter);
	}

	protected List<String> getFilters() {
		List<String> result = new ArrayList<String>();
		
		result.add(lanFilter.getSelectionModel().getSelectedItem());
		result.add(meterNameFilter.getSelectionModel().getSelectedItem());
		result.add(meterTypeFilter.getSelectionModel().getSelectedItem());
		result.add(networkIDFilter.getSelectionModel().getSelectedItem());
		result.add(locationFilter.getSelectionModel().getSelectedItem());
		result.add(socketFilter.getSelectionModel().getSelectedItem());
		
		return result;
	}


	@Override
	protected Entry generateEntry(List<String> fields) {
		return new Meter(main, fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5), fields.get(6), fields.get(7));
	}
}
