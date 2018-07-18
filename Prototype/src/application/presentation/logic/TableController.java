package application.presentation.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import application.Main;
import application.objects.entities.Entry;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;

public abstract class TableController {
	
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

	private TableType type;
	protected Main main;
	protected List<MenuButton> filters;
	private FilteredList<Entry> entries;
	private List<Set<String>> filterSettings;
	
	public TableController(TableType type) {
		this.type = type;
		filters = new ArrayList<MenuButton>();
		columns = new ArrayList<TableColumn<Entry, String>>();
		addColumns = new ArrayList<TableColumn<Entry, String>>();
		filterSettings = new ArrayList<Set<String>>();
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
		
		entries = new FilteredList<Entry>(main.getObjectLayer().getEntries(type));
		
		for (int i = 0; i < filters.size(); i++) {
			MenuButton filter = filters.get(i);
			addFilterSetting();
			
			final int index = i;
			
			ObservableList<MenuItem> options = filter.getItems();
			
			options.clear();
			CheckMenuItem allOption = new CheckMenuItem("All");
			
			allOption.setSelected(true);
			allOption.setOnAction(event -> {
				if (allOption.isSelected()){
					for (MenuItem item : options) {
						((CheckMenuItem) item).setSelected(true);
						addFilter(index, ((CheckMenuItem) item).getText());
					}
				} else {
					for (MenuItem item : options) {
						((CheckMenuItem) item).setSelected(false);
						removeFilter(index, ((CheckMenuItem) item).getText());
					}
				}
				
				computeFilter(type);
			});
			options.add(allOption);
			
			
			List<Object> raw = main.getMainDBController().getDistinctColumn(type, filters.indexOf(filter));
			for (Object option : raw) {
				CheckMenuItem optionItem = new CheckMenuItem((String) option);
				optionItem.setOnAction(event -> {
					if (optionItem.isSelected()){
						addFilter(index, optionItem.getText());
					} else {
						removeFilter(index, optionItem.getText());
						
						if (allOption.isSelected()) allOption.setSelected(false);
					}
					
					computeFilter(type);
				});
				
				options.add(optionItem);
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
		
		entries.getSource().addListener((Change<? extends Entry> change) -> {
			while(change.next()) {
				if (change.wasAdded()) {
					for (Entry entry : change.getAddedSubList()) {
						for (int i = 0; i < filters.size(); i++) {
							ObservableList<MenuItem> options = filters.get(i).getItems();
							
							boolean contains = false;
							
							Iterator<MenuItem> iter = options.iterator();
							while(!contains && iter.hasNext()) {
								MenuItem option = iter.next();
								if ((option.getText() == null && entry.getFilterValue(i) == null) ||
										(option.getText() != null && entry.getFilterValue(i) != null && 
										option.getText().compareTo(entry.getFilterValue(i)) == 0)) 
									contains = true;
							}
							
							if (!contains) {
								CheckMenuItem allOption = (CheckMenuItem) options.get(0);
								CheckMenuItem optionItem = new CheckMenuItem((String) entry.getFilterValue(i));
								final int index = i;
								optionItem.setOnAction(event -> {
									if (optionItem.isSelected()){
										addFilter(index, optionItem.getText());
									} else {
										removeFilter(index, optionItem.getText());
										
										if (allOption.isSelected()) allOption.setSelected(false);
									}
									
									computeFilter(type);
								});
								
								options.add(optionItem);
								allOption.fire();
							}
						}
					}
				}
				if (change.wasRemoved()) {
					for (Entry entry : change.getRemoved()) {
						for (int i = 0; i < filters.size(); i++) {
							
							boolean shouldRemain = false;
							Iterator<Entry> entryIter = entries.iterator();
							while (!shouldRemain && entryIter.hasNext()) {
								Entry stableEntry = entryIter.next();
								if ((stableEntry.getFilterValue(i) == null && entry.getFilterValue(i) == null) ||
										(stableEntry.getFilterValue(i) != null && entry.getFilterValue(i) != null && 
												stableEntry.getFilterValue(i).compareTo(entry.getFilterValue(i)) == 0)) {
									shouldRemain = true;
								}
							}
							
							if (!shouldRemain) {
								ObservableList<MenuItem> options = filters.get(i).getItems();
								MenuItem option;
								boolean contains = false;
								
								Iterator<MenuItem> iter = options.iterator();
								while(!contains && iter.hasNext()) {
									option = iter.next();
									
									if ((option.getText() == null && entry.getFilterValue(i) == null) ||
											(option.getText() != null && entry.getFilterValue(i) != null && 
											option.getText().compareTo(entry.getFilterValue(i)) == 0)) {
										contains = true;
										options.remove(option);
									}
								}
							}
						}
					}
				}
			}
		});
		
		hideAdd();
	}
	


	public void addFilter(int i, String text) {
		filterSettings.get(i).add(text);
	}

	public void removeFilter(int i, String text) {
		filterSettings.get(i).remove(text);
	}

	public void addFilterSetting() {
		filterSettings.add(new HashSet<String>());
	}
	
	public void computeFilter(TableType type) {
		entries.setPredicate(entry -> {
			boolean result = true;
			
			for (int i = 0; i < filterSettings.size(); i++) {
				result = result && filterSettings.get(i).contains(entry.getFilterValue(i));
			}
			
			return result;
		});
	}
	
	public void remove() {
		Entry entry = table.getSelectionModel().getSelectedItem();
		System.out.println(entry.getFieldProperty(0));
		main.getObjectLayer().deleteDevice(type, entry);
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
			main.getObjectLayer().commitDevice(type, entry);
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
		
		grid.getRowConstraints().get(1).setPrefHeight(35);
		grid.getRowConstraints().get(2).setPrefHeight(30);
	}
	
	private void hideAdd() {
		addButton.setDisable(true);
		addButton.setVisible(false);
		cancelAddButton.setDisable(true);
		cancelAddButton.setVisible(false);
		
		addTable.setVisible(false);
		
		grid.getRowConstraints().get(1).setPrefHeight(0);
		grid.getRowConstraints().get(2).setPrefHeight(0);
	}
	
	protected void save() {
		try {
			main.getObjectLayer().saveTable(type);
		} catch (SQLException e) {
			main.errorHandle(e);
		}
	}
	
	protected void refresh() {
		try {
			main.getObjectLayer().loadTable(type);
		} catch (SQLException e) {
			main.errorHandle(e);
		}
	}

	protected abstract void optionalAddColumnSetup(int i);

	protected abstract void optionalColumnSetup(int i);

	protected abstract void initialize();
	
	protected void setupEditableColumn(int i) {
		columns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new EditingCell());
		columns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Entry, String> t) -> {
                	Entry entry = (Entry) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    entry.getFieldProperty(i).setValue(t.getNewValue());
                    entry.indicateChanged();
                });
	}
	
	protected void setupEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new EditingCell());
		addColumns.get(i).setOnEditCommit(
                (TableColumn.CellEditEvent<Entry, String> t) -> {
                	Entry entry = (Entry) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow());
                    entry.getFieldProperty(i).setValue(t.getNewValue());
                    entry.indicateChanged();
                });
	}
	
	protected void setupNonEditableAddColumn(int i) {
		addColumns.get(i).setCellFactory((TableColumn<Entry, String> param) -> new NonEditingCell());
	}
	
	protected class NonEditingCell extends TableCell<Entry, String> {
		 @Override
         public void updateItem(String item, boolean empty) {
             super.updateItem(item, empty);
             if (!isEmpty()) {
            	 this.setStyle("-fx-background-color: gray ;");
                 setText(item);
             }
         }

	}
	
	protected class EditingCell extends TableCell<Entry, String> {

        private TextField textField;

        protected EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(item);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
//                        setGraphic(null);
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
        
        @Override
        public void commitEdit(String item) {
            // This block is necessary to support commit on losing focus
            if (!isEditing() && !item.equals(getItem())) {
                TableView<Entry> table = getTableView();
                if (table != null) {
                    TableColumn<Entry, String> column = getTableColumn();
                    CellEditEvent<Entry, String> event = new CellEditEvent<>(
                        table, new TablePosition<Entry,String>(table, getIndex(), column), 
                        TableColumn.editCommitEvent(), item
                    );
                    Event.fireEvent(column, event);
                }
            }

            super.commitEdit(item);
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnAction((e) -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    commitEdit(textField.getText());
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }
}
