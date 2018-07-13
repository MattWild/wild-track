package application.presentation.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.entities.Entry;
import application.presentation.PresentationLayer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class TableController {
	
	public enum TableType {
		Meters,
		Collectors,
		Routers,
		HANDevices,
		Sockets
	}
	
	protected List<TableColumn<Entry, Property>> columns;
	protected TableView<Entry> table;

	private TableType type;
	protected Main main;
	protected List<ComboBox<String>> filters;
	protected Map<CheckBox, Entry> entries;
	
	public TableController(TableType type) {
		this.type = type;
		filters = new ArrayList<ComboBox<String>>();
		columns = new ArrayList<TableColumn<Entry, Property>>();
	}
	
	public TableType getType() {
		return type;
	}
	
	public void setMain(Main main) {
		this.main = main;
		main.getPresentationLayer().setGrid(type, this);
	}

	public void populateTable() throws SQLException {
		initialize();
		
		for (ComboBox<String> filter : filters) {
			List<String> options = filter.getItems();
			
			options.clear();
			options.add("All");
			List<Object> raw = main.getMainDBController().getDistinctColumn(type, filters.indexOf(filter));
			for (Object option : raw) {
				options.add((String) option);
			}
			
			filter.getSelectionModel().selectFirst();
			filter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				filter.getSelectionModel()
				main.getObjectLayer().setDataFilter(type, newValue);
			});
		}
		
		for (int i = 0; i < columns.size(); i++) {
			TableColumn<Entry,Property> column = columns.get(i);
			final int index = i;
			column.setCellValueFactory(cellData -> cellData.getValue().getFieldProperty(index));
		}
		
		table.setItems(main.getObjectLayer().getEntries(type));
	}

	protected abstract void initialize();
}
