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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public abstract class TableController {
	
	public enum TableType {
		Meters,
		Collectors,
		Routers,
		HANDevices,
		Sockets
	}

	private TableType type;
	private boolean paused;
	protected Main main;
	protected GridPane grid;
	protected List<ComboBox<String>> filters;
	protected Map<CheckBox, Entry> entries;
	
	public TableController(TableType type) {
		this.type = type;
		filters = new ArrayList<ComboBox<String>>();
		entries = new HashMap<CheckBox, Entry>();
		paused = false;
	}
	
	public TableType getType() {
		return type;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void populateTable() {
		initialize();
		
		refresh();
	}

	public void refresh() {
		paused = true;
		
		for (ComboBox<String> filter : filters) {
			try {
				List<String> options = filter.getItems();
				options.clear();
				options.add("All");
				List<Object> raw = main.getMainDBController().getDistinctColumn(type, filters.indexOf(filter));
				for (Object option : raw)
					options.add((String) option);
				
				filter.getSelectionModel().selectFirst();
				filter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						filter.getSelectionModel().select(newValue.intValue());
						if (!paused) refresh();
					}
				});
			} catch (SQLException e) {
				main.errorHandle(e);
			}
		}
		
		grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 1);
		entries.clear();
		
		try {
			List<String> selected = getFilters();
			ResultSet results = main.getMainDBController().getTableData(type, selected);
			
			int i = 2;
			if (results != null) {
				int columnNum = results.getMetaData().getColumnCount();
				while (results.next()) {
					List<String> fields = new ArrayList<String>();
					for (int j = 0; j < columnNum; j++) fields.add(results.getString(j + 1));
					
					Entry entry = generateEntry(fields);
					List<Node> children = entry.getChildren();
					
					grid.add(entry.getCheckBox(), 0, i);
					entries.put(entry.getCheckBox(), entry);
					for (int j = 0; j < children.size(); j++) {
						grid.add(children.get(j), j + 1, i);
					}
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		paused = false;
	}
	
	protected abstract List<String> getFilters();

	protected abstract Entry generateEntry(List<String> fields);

	protected abstract void initialize();

	public List<Entry> getSelected() {
		List<Entry> selected = new ArrayList<Entry>();
		for (CheckBox cb : entries.keySet())
			if (cb.isSelected()) selected.add(entries.get(cb));
		
		return selected;
	}
}
