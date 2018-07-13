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
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MetersGridController extends TableController{

	@FXML
	private TableView<Entry> metersTable;
	
	@FXML
	private TableColumn<Entry,Property> lanColumn;
	
	@FXML 
	private ComboBox<String> lanFilter;
	
	@FXML
	private TableColumn<Entry,Property> meterNameColumn;
	
	@FXML
	private ComboBox<String> meterNameFilter;
	
	@FXML
	private TableColumn<Entry,Property> meterTypeColumn;
	
	@FXML
	private ComboBox<String> meterTypeFilter;
	
	@FXML
	private TableColumn<Entry,Property> netIdColumn;
	
	@FXML
	private ComboBox<String> networkIDFilter;
	
	@FXML
	private TableColumn<Entry,Property> otherCRCsColumn;
	
	@FXML
	private TableColumn<Entry,Property> locColumn;
	
	@FXML
	private ComboBox<String> locationFilter;
	
	@FXML
	private TableColumn<Entry,Property> socketColumn;
	
	@FXML
	private ComboBox<String> socketFilter;
	
	@FXML
	private TableColumn<Entry,Property> notesColumn;
	
	
	public MetersGridController() {
		super(TableController.TableType.Meters);
	}
	

	@Override
	protected void initialize() {
		this.table = metersTable;

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
	}
}
