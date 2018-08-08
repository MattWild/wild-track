package application.presentation.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import application.Main;
import application.objects.hardware.Device;
import application.objects.hardware.Device.DeviceType;
import application.presentation.PresentationLayer.EditingCell;
import application.presentation.PresentationLayer.NonEditingCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public abstract class DeviceGridController {

	protected GridPane grid;
	protected List<TableColumn<Device, String>> columns;
	protected TableView<Device> table;
	protected TableView<Device> addTable;
	protected List<TableColumn<Device, String>> addColumns;
	protected Button addButton;
	protected Button cancelAddButton;
	protected TextField searchField;

	protected List<MenuButton> filters;
	private DeviceType type;
	private Main main;
	private FilteredList<Device> devices;
	private List<ObservableMap<String, SimpleIntegerProperty>> filterTracker;
	private List<ObservableMap<String, CheckMenuItem>> filterSettings;
	private ListChangeListener<? super Device> entriesListener = (ListChangeListener.Change<? extends Device> change) -> {
		while(change.next()) {
			if (change.wasAdded()) {
				for (Device device : change.getAddedSubList()) {
					for (int i = 0; i < filters.size(); i++) {
						if (filterTracker.get(i).containsKey(device.getFilterProperty(i).get()))
							filterTracker.get(i).get(device.getFilterProperty(i).get()).set(filterTracker.get(i).get(device.getFilterProperty(i).get()).get() + 1); 
						else {
							SimpleIntegerProperty countProp = new SimpleIntegerProperty(1);
							countProp.addListener(countPropListener(device.getFilterProperty(i).get(), i));
							
							filterTracker.get(i).put(device.getFilterProperty(i).get(), countProp);
						}
						
						device.getFilterProperty(i).addListener(filterPropListener(i));
					}
				}
			}
			if (change.wasRemoved()) {
				for (Device device : change.getRemoved()) {
					for (int i = 0; i < filters.size(); i++) {
						filterTracker.get(i).get(device.getFilterProperty(i).get()).set(filterTracker.get(i).get(device.getFilterProperty(i).get()).get() - 1);
					}
				}
			}
		}
	};
	
	public DeviceGridController(DeviceType type) {
		this.type = type;
		filters = new ArrayList<MenuButton>();
		columns = new ArrayList<TableColumn<Device, String>>();
		addColumns = new ArrayList<TableColumn<Device, String>>();
		filterSettings = new ArrayList<ObservableMap<String, CheckMenuItem>>();
		filterTracker = new ArrayList<ObservableMap<String, SimpleIntegerProperty>>();
	}
	
	public DeviceType getType() {
		return type;
	}
	
	public void setMain(Main main) {
		this.main = main;
		
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
		
		for (int i = 0; i < filters.size(); i++) {
			MenuButton filter = filters.get(i);
			ObservableList<MenuItem> options = filter.getItems();
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
		}
		
		for (int i = 0; i < columns.size(); i++) {
			TableColumn<Device,String> column = columns.get(i);
			TableColumn<Device,String> addColumn = addColumns.get(i);
			final int index = i;
			column.setCellValueFactory(cellData -> cellData.getValue().getFieldProperty(index));
			addColumn.setCellValueFactory(cellData -> cellData.getValue().getFieldProperty(index));
			optionalColumnSetup(i);
			optionalAddColumnSetup(i);
		}
	}
	
	public void disableTable() {
		((FilteredList<Device>) table.getItems()).getSource().removeListener(entriesListener);
		searchField.setText(null);
		for (MenuButton filter : filters) {
			((CheckMenuItem) filter.getItems().get(0)).setSelected(true);
			((CheckMenuItem) filter.getItems().get(0)).fire();
		}
		
		
		for(ObservableMap<String, SimpleIntegerProperty> count : filterTracker)
			count.clear();
	}

	public void enableTable() {
		devices = new FilteredList<Device>(new SortedList<Device>(main.getObjectLayer().getDevices(type), (d1, d2) -> {
			return d1.getIdentifier().compareTo(d2.getIdentifier());
		}));
		
		for (int i = 0; i < filters.size(); i++) {
			ObservableList<MenuItem> options = filters.get(i).getItems();
			CheckMenuItem allOption = (CheckMenuItem) options.get(0);
			
			for (Device device : devices) {
				if (filterTracker.get(i).containsKey(device.getFilterProperty(i).get()))
					filterTracker.get(i).get(device.getFilterProperty(i).get()).set(filterTracker.get(i).get(device.getFilterProperty(i).get()).get() + 1); 
				else {
					SimpleIntegerProperty countProp = new SimpleIntegerProperty(1);
					countProp.addListener(countPropListener(device.getFilterProperty(i).get(), i));
					
					filterTracker.get(i).put(device.getFilterProperty(i).get(), countProp);
				}
				
				device.getFilterProperty(i).addListener(filterPropListener(i));
			}
			
			allOption.fire();
		}
		table.setItems(devices);
		
		devices.getSource().addListener(entriesListener);
		
		hideAdd();
	}
	
	public void startAdd() {
		Device device = main.getObjectLayer().createDevice(type);
		ObservableList<Device> temp = FXCollections.observableArrayList(device);
		addTable.setItems(temp);
		
		showAdd();
	}

	public void finishAdd() {
		Device device = addTable.getItems().get(0);
		if (device.identifierNotNull()) {
			main.getObjectLayer().addDevice(type, device);
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
				optionItem.setSelected(true);
				options.add(optionItem);
			} else if (change.wasRemoved()) {
				Iterator<MenuItem> iter = options.iterator();
				
				while(iter.hasNext()) {
					MenuItem option = iter.next();
				    
					if ((option.getText() == null && change.getKey() == null) || 
							(option.getText() != null && change.getKey() != null && option.getText().compareTo(change.getKey()) == 0)) {
						iter.remove();
						break;
					}
					
					filterSettings.get(i).remove(change.getKey());
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
	
	private void computeFilter(DeviceType type) {
		devices.setPredicate(device -> {
			boolean result = true;
			
			for (int i = 0; i < filterSettings.size(); i++) {
				result = result && (
							((CheckMenuItem) filters.get(i).getItems().get(0)).isSelected() || 
							(filterSettings.get(i).get(device.getFilterProperty(i).get()) != null &&
							filterSettings.get(i).get(device.getFilterProperty(i).get()).isSelected())
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
	
	protected abstract void optionalAddColumnSetup(int i);

	protected abstract void optionalColumnSetup(int i);

	protected abstract void initialize();

	protected void save() {
		try {
			main.getObjectLayer().saveDevices(type);
		} catch (SQLException e) {
			main.errorHandle(e);
		}
	}

	protected void setupEditableColumn(int i) {
		columns.get(i).setCellFactory((TableColumn<Device, String> param) -> new EditingCell<Device>());
		columns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Device, String> t) -> {
                	Device device = (Device) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    device.getFieldProperty(i).setValue(t.getNewValue());
                    device.indicateChanged();
                });
	}
	
	protected void setupEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Device, String> param) -> new EditingCell<Device>());
		addColumns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Device, String> t) -> {
                	Device device = (Device) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    device.getFieldProperty(i).setValue(t.getNewValue());
                    device.indicateChanged();
                });
	}
	
	protected void setupNonEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Device, String> param) -> new NonEditingCell<Device>());
	}
}
