package application.presentation.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.axis.wsdl.symbolTable.MessageEntry;

import application.Main;
import application.objects.entities.Entry;
import application.presentation.PresentationLayer.EditingCell;
import application.presentation.PresentationLayer.NonEditingCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public abstract class DeviceGridController {
	
	public enum TableType {
		Meters,
		Collectors,
		Routers,
		HANDevices,
		Sockets
	}

	protected GridPane grid;
	protected List<TableColumn<Entry, String>> columns;
	protected TableView<Entry> table;
	protected TableView<Entry> addTable;
	protected List<TableColumn<Entry, String>> addColumns;
	protected Button addButton;
	protected Button cancelAddButton;
	protected TextField searchField;

	private TableType type;
	protected Main main;
	protected List<MenuButton> filters;
	private FilteredList<Entry> entries;
	private List<ObservableMap<String, SimpleIntegerProperty>> filterTracker;
	private List<ObservableMap<String, CheckMenuItem>> filterSettings;
	
	public DeviceGridController(TableType type) {
		this.type = type;
		filters = new ArrayList<MenuButton>();
		columns = new ArrayList<TableColumn<Entry, String>>();
		addColumns = new ArrayList<TableColumn<Entry, String>>();
		filterSettings = new ArrayList<ObservableMap<String, CheckMenuItem>>();
		filterTracker = new ArrayList<ObservableMap<String, SimpleIntegerProperty>>();
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
		addButton.setOnAction(event -> {
			finishAdd();
		});
		cancelAddButton.setOnAction(event -> {
			cancelAdd();
		});
		searchField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				search();
		});
		
		entries = new FilteredList<Entry>(main.getObjectLayer().getDevices(type));
		
		for (int i = 0; i < filters.size(); i++) {
			MenuButton filter = filters.get(i);
			ObservableList<MenuItem> options = filter.getItems();options.clear();
			CheckMenuItem allOption = new CheckMenuItem("All");
			
			allOption.setSelected(true);
			allOption.setOnAction(event -> {
				if (allOption.isSelected()){
					for (MenuItem item : options) {
						((CheckMenuItem) item).setSelected(true);
					}
				} else {
					for (MenuItem item : options) {
						((CheckMenuItem) item).setSelected(false);
					}
				}
				
				computeFilter(type);
			});
			options.add(allOption);
			
			filterTracker.add(FXCollections.observableMap(new HashMap<String, SimpleIntegerProperty>()));
			filterSettings.add(FXCollections.observableMap(new HashMap<String, CheckMenuItem>()));
			filterTracker.get(i).addListener(filterMapListener(allOption, options, i));
			
			for (Entry entry : entries) {
				if (filterTracker.get(i).containsKey(entry.getFieldProperty(i).get()))
					filterTracker.get(i).get(entry.getFieldProperty(i).get()).set(filterTracker.get(i).get(entry.getFieldProperty(i).get()).get() + 1); 
				else {
					SimpleIntegerProperty countProp = new SimpleIntegerProperty(1);
					countProp.addListener(countPropListener(entry.getFieldProperty(i).get(), i));
					
					filterTracker.get(i).put(entry.getFieldProperty(i).get(), countProp);
				}
				
				entry.getFieldProperty(i).addListener(filterPropListener(i));
			}
			
			allOption.fire();
		}
		
		for (int i = 0; i < columns.size(); i++) {
			TableColumn<Entry,String> column = columns.get(i);
			TableColumn<Entry,String> addColumn = addColumns.get(i);
			final int index = i;
			column.setCellValueFactory(cellData -> cellData.getValue().getFieldProperty(index));
			addColumn.setCellValueFactory(cellData -> cellData.getValue().getFieldProperty(index));
			optionalColumnSetup(i);
			optionalAddColumnSetup(i);
		}
		table.setItems(entries);
		
		entries.getSource().addListener(entriesListener());
		
		hideAdd();
	}
	
	private MapChangeListener<? super String, ? super SimpleIntegerProperty> filterMapListener(CheckMenuItem allOption, ObservableList<MenuItem> options, int i) {
		return (MapChangeListener.Change<? extends String, ? extends SimpleIntegerProperty> change) -> {
			if (change.wasAdded()) {
				CheckMenuItem optionItem = new CheckMenuItem(change.getKey());
				optionItem.setOnAction(event -> {
					if (!optionItem.isSelected())
						allOption.setSelected(false);
					
					computeFilter(type);
				});
				
				filterSettings.get(i).put(change.getKey(), optionItem);
				options.add(optionItem);
			} else if (change.wasRemoved()) {
				Iterator<MenuItem> iter = options.iterator();
				
				while(iter.hasNext()) {
					MenuItem option = iter.next();
				    
					if (option.getText() != null && change.getKey() != null && option.getText().compareTo(change.getKey()) == 0) {
						iter.remove();
						break;
					}
					
					filterSettings.get(i).remove(change.getKey());
				}
			}
		};
	}
	
	private ListChangeListener<? super Entry> entriesListener() {
		return (ListChangeListener.Change<? extends Entry> change) -> {
			while(change.next()) {
				if (change.wasAdded()) {
					for (Entry entry : change.getAddedSubList()) {
						for (int i = 0; i < filters.size(); i++) {
							if (filterTracker.get(i).containsKey(entry.getFieldProperty(i).get()))
								filterTracker.get(i).get(entry.getFieldProperty(i).get()).set(filterTracker.get(i).get(entry.getFieldProperty(i).get()).get() + 1); 
							else {
								SimpleIntegerProperty countProp = new SimpleIntegerProperty(1);
								countProp.addListener(countPropListener(entry.getFieldProperty(i).get(), i));
								
								filterTracker.get(i).put(entry.getFieldProperty(i).get(), countProp);
								
								entry.getFieldProperty(i).addListener(filterPropListener(i));
							}
						}
					}
				}
				if (change.wasRemoved()) {
					for (Entry entry : change.getRemoved()) {
						for (int i = 0; i < filters.size(); i++) {
							filterTracker.get(i).get(entry.getFieldProperty(i).get()).set(filterTracker.get(i).get(entry.getFieldProperty(i).get()).get() - 1);;
						}
					}
				}
			}
		};
	}
	
	private ChangeListener<? super String> filterPropListener(int i) {
		return (arg, oldValue, newValue) -> {
			filterTracker.get(i).get(oldValue).set(filterTracker.get(i).get(oldValue).get() - 1);;

			if (filterTracker.get(i).containsKey(newValue))
				filterTracker.get(i).get(newValue).set(filterTracker.get(i).get(newValue).get() + 1); 
			else {
				SimpleIntegerProperty countProp = new SimpleIntegerProperty(1);
				countProp.addListener(countPropListener(newValue, i));
				
				filterTracker.get(i).put(newValue, countProp);
			}
		};
	}
	
	private ChangeListener<? super Number> countPropListener(String string, int i) {
		return (arg, oldValue, newValue) -> {
			if (newValue.intValue() == 0) 
				filterTracker.get(i).remove(string);
		};
	}
	
	private void computeFilter(TableType type) {
		entries.setPredicate(entry -> {
			boolean result = true;
			
			for (int i = 0; i < filterSettings.size(); i++) {
				result = result && (
							((CheckMenuItem) filters.get(i).getItems().get(0)).isSelected() ||
							filterSettings.get(i).get(entry.getFieldProperty(i).get()).isSelected()
						);
			}
			
			return result;
		});
	}
	
	private void search() {
		String identifier = searchField.getText();
		
		List<CheckMenuItem> searchResults = new ArrayList<CheckMenuItem>();
		for (String str : filterSettings.get(0).keySet())
			if (str.contains(identifier))
				searchResults.add(filterSettings.get(0).get(str));
		if (!searchResults.isEmpty()) {
			((CheckMenuItem) filters.get(0).getItems().get(0)).setSelected(false);
			((CheckMenuItem) filters.get(0).getItems().get(0)).fire();
			
			for (CheckMenuItem option : searchResults)
				option.setSelected(true);
			
			computeFilter(type);
		}
	}
	
	public void startAdd() {
		Entry entry = main.getObjectLayer().createDevice(type);
		ObservableList<Entry> temp = FXCollections.observableArrayList(entry);
		addTable.setItems(temp);
		
		showAdd();
	}
	
	public void finishAdd() {
		Entry entry = addTable.getItems().get(0);
		if (entry.identifierNotNull()) {
			main.getObjectLayer().addDevice(type, entry);
			addTable.setItems(null);
			hideAdd();
		} else {
			main.getPresentationLayer().showError("Add " + type.toString() + " failure.", "You must provide a valid identifier for the device.");
		}
	}
	
	public void cancelAdd() {
		addTable.setItems(null);
		
		hideAdd();
	}
	
	private void showAdd() {
		addButton.setDisable(false);
		addButton.setVisible(true);
		cancelAddButton.setDisable(false);
		cancelAddButton.setVisible(true);
		
		addTable.setVisible(true);
		
		grid.getRowConstraints().get(GridPane.getRowIndex(addTable.getParent())).setPrefHeight(35);
		grid.getRowConstraints().get(GridPane.getRowIndex(addButton.getParent())).setPrefHeight(30);
	}
	
	private void hideAdd() {
		addButton.setDisable(true);
		addButton.setVisible(false);
		cancelAddButton.setDisable(true);
		cancelAddButton.setVisible(false);
		
		addTable.setVisible(false);
		
		grid.getRowConstraints().get(GridPane.getRowIndex(addTable.getParent())).setPrefHeight(0);
		grid.getRowConstraints().get(GridPane.getRowIndex(addButton.getParent())).setPrefHeight(0);
	}
	
	protected void save() {
		try {
			main.getObjectLayer().saveDevices(type);
		} catch (SQLException e) {
			main.errorHandle(e);
		}
	}


	protected abstract void optionalAddColumnSetup(int i);

	protected abstract void optionalColumnSetup(int i);

	protected abstract void initialize();
	
	protected void setupEditableColumn(int i) {
		columns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new EditingCell<Entry>());
		columns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Entry, String> t) -> {
                	Entry entry = (Entry) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    entry.getFieldProperty(i).setValue(t.getNewValue());
                    entry.indicateChanged();
                });
	}
	
	protected void setupEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new EditingCell<Entry>());
		addColumns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Entry, String> t) -> {
                	Entry entry = (Entry) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    entry.getFieldProperty(i).setValue(t.getNewValue());
                    entry.indicateChanged();
                });
	}
	
	protected void setupNonEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new NonEditingCell<Entry>());
	}
}
