package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import application.objects.entities.Checkpoint;
import application.objects.entities.Component;
import application.objects.entities.Device;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;
import application.objects.entities.Component.ComponentType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RootLayoutController {
	@FXML
	private TabPane tabPane;
	
	@FXML 
	private MenuItem saveControl;
	
	@FXML
	private MenuItem saveAllControl;
	
	@FXML
	private MenuItem addControl;
	
	@FXML
	private Menu addComponentMenu;
	
	@FXML
	private MenuItem deleteControl;
	
	@FXML
	private MenuItem pullDeviceDataControl;

	@FXML
	private MenuItem pullAllDeviceDataControl;
	
	@FXML
	private MenuItem pullComponentDataControl;

	@FXML
	private MenuItem pullAllComponentDataControl;
	
	@FXML
	private Tab	environmentsTab;
	
	@FXML
	private Tab	checkpointsTab;
	
	@FXML
	private Tab	metersTab;
	
	@FXML
	private Tab	collectorsTab;
	
	@FXML
	private Tab	routersTab;
	
	@FXML
	private Tab	hanTab;
	
	@FXML
	private Tab	socketsTab;
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void initMenuBindings() {
		pullAllComponentDataControl.setOnAction(event -> {
			for (Environment environment : main.getObjectLayer().getEnvironments()) {
				main.getServiceLayer().processComponentServices(environment);
			}
		});
		
		pullAllDeviceDataControl.setOnAction(event -> {
			try {
				//main.getMainDBController().clearEnvironRelationships();
				for (Environment environment : main.getObjectLayer().getEnvironments()) {
					main.getServiceLayer().processDeviceServices(environment);
				}
				for (TableType type : TableType.values())
					main.getObjectLayer().loadDevices(type);
			} catch (SQLException e) {
				main.errorHandle(e);
			}
		});
		
		tabPane.getSelectionModel().selectedItemProperty().addListener((tabArg, oldTab, newTab) -> {
			if (oldTab == environmentsTab) {
				addControl.setVisible(true);
				addComponentMenu.setVisible(false);
				
				saveAllControl.setDisable(true);
				saveAllControl.setVisible(false);
				
				saveControl.setDisable(false);
				
				pullAllComponentDataControl.setDisable(true);
				pullAllComponentDataControl.setVisible(false);
				
				pullAllDeviceDataControl.setDisable(true);
				pullAllDeviceDataControl.setVisible(false);
				
				pullComponentDataControl.setVisible(false);
				pullDeviceDataControl.setVisible(false);
			}
			
			if (newTab == environmentsTab) {
				addControl.setDisable(true);
				addControl.setText("Add Environment");
				
				saveAllControl.setDisable(false);
				saveAllControl.setVisible(true);
				saveAllControl.setOnAction(event -> {
					try {
						main.getObjectLayer().saveAllEnvironments();
					} catch (SQLException e) {
						main.errorHandle(e);
					}
				});
				
				pullAllComponentDataControl.setVisible(true);
				pullAllComponentDataControl.setDisable(false);
				
				pullAllDeviceDataControl.setVisible(true);
				pullAllDeviceDataControl.setDisable(false);
				
				pullComponentDataControl.setVisible(true);
				pullDeviceDataControl.setVisible(true);
			} else if (newTab == checkpointsTab) {
				addControl.setDisable(true);
				addControl.setText("Add Checkpoint");
				
				saveControl.setOnAction(event -> {
					try {
						main.getObjectLayer().saveCheckpoints();
					} catch (SQLException e) {
						main.errorHandle(e);
					}
				});
			} else {
				addControl.setDisable(false);
				addControl.setText("Add");
				addControl.setOnAction(event -> {
					((DeviceGridController) newTab.getUserData()).startAdd();;
				});
				
				saveControl.setOnAction(event -> {
					try {
						main.getObjectLayer().saveDevices(((DeviceGridController) newTab.getUserData()).getType());
					} catch (SQLException e) {
						main.errorHandle(e);
					}
				});
			}
		});
		
		((EnvironmentsTabController) environmentsTab.getUserData()).selectedEnvironmentProperty().addListener((envArg, oldEnv, newEnv) -> {
			if (newEnv == null || newEnv.getUserData() == null) {
				saveControl.setDisable(true);
				pullComponentDataControl.setDisable(true);
				pullDeviceDataControl.setDisable(true);
			} else {
				Environment environment = (Environment) newEnv.getUserData();
				
				saveControl.setDisable(false);
				saveControl.setOnAction(event -> {
					try {
						main.getObjectLayer().saveEnvironment(environment);
					} catch (SQLException e) {
						main.errorHandle(e);
					}
				});
				
				pullComponentDataControl.setDisable(false);
				pullComponentDataControl.setOnAction(event -> {
					main.getServiceLayer().processComponentServices(environment);
				});
				
				pullDeviceDataControl.setDisable(false);
				pullDeviceDataControl.setOnAction(event -> {
					try {
						main.getServiceLayer().processDeviceServices(environment);
						for (TableType type : TableType.values())
							main.getObjectLayer().loadDevices(type);
					} catch (SQLException e) {
						main.errorHandle(e);
					}
				});
			}
		});
		
		main.getPresentationLayer().focusedProperty().addListener((focusArg, oldNode, newNode) -> {
			Tab tab = tabPane.getSelectionModel().getSelectedItem();
			
			Object focused = (newNode != null)? newNode.getUserData() : null;
			if (tab == environmentsTab) {
				if (focused instanceof Environment) {
					Environment environment = (Environment) focused;
					
					addControl.setVisible(true);
					addControl.setDisable(false);
					addControl.setText("Add Server");
					addControl.setOnAction(action -> {
						main.getObjectLayer().addServer(environment);
					});
					
					pullComponentDataControl.setVisible(true);
					pullComponentDataControl.setDisable(false);
					pullComponentDataControl.setOnAction(event -> {
						main.getServiceLayer().processComponentServices(environment);
					});
					pullDeviceDataControl.setVisible(true);
					pullDeviceDataControl.setDisable(false);
					pullDeviceDataControl.setOnAction(event -> {
						main.getServiceLayer().processDeviceServices(environment);
					});
	
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteEnvironment(environment);
					});
				} else if (focused instanceof Server) {
					Server server = (Server) focused;
					
					addControl.setVisible(false);
					addControl.setDisable(true);
					
					addComponentMenu.setVisible(true);
					addComponentMenu.setDisable(false);
					
					pullComponentDataControl.setVisible(true);
					pullComponentDataControl.setDisable(false);
					pullComponentDataControl.setOnAction(event -> {
						main.getServiceLayer().processComponentServices(server.getParent());
					});
					pullDeviceDataControl.setVisible(true);
					pullDeviceDataControl.setDisable(false);
					pullDeviceDataControl.setOnAction(event -> {
						main.getServiceLayer().processDeviceServices(server.getParent());
					});
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteServer(server);
					});
				} else if (focused instanceof Component) {
					Component component = (Component) focused;
					
					addControl.setVisible(true);
					addControl.setDisable(true);
					addControl.setText("Add");
					
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					pullComponentDataControl.setVisible(true);
					pullComponentDataControl.setDisable(false);
					pullComponentDataControl.setOnAction(event -> {
						main.getServiceLayer().processComponentServices(component.getParent().getParent());
					});
					pullDeviceDataControl.setVisible(true);
					pullDeviceDataControl.setDisable(false);
					pullDeviceDataControl.setOnAction(event -> {
						main.getServiceLayer().processDeviceServices(component.getParent().getParent());
					});
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteComponent(component);
					});
				} else {
					addControl.setVisible(true);
					addControl.setDisable(false);
					addControl.setText("Add Environment");
					addControl.setOnAction(action -> {
						main.getObjectLayer().addEnvironment();
					});
					
					pullComponentDataControl.setVisible(false);
					pullComponentDataControl.setDisable(true);
					pullDeviceDataControl.setVisible(false);
					pullDeviceDataControl.setDisable(true);
					
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(true);
				}
			} else if (tab == checkpointsTab) {
				if (focused instanceof Environment) {
					Environment environment = (Environment) focused;
					
					addControl.setDisable(false);
					addControl.setText("Add Checkpoint");
					addControl.setOnAction(event -> {
						main.getObjectLayer().addCheckpoint(environment);
					});
					
					deleteControl.setDisable(true);
				} else if (focused instanceof Checkpoint) {
					Checkpoint checkpoint = (Checkpoint) focused;
					
					addControl.setDisable(true);
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(event -> {
						main.getObjectLayer().deleteCheckpoint(checkpoint);
					});
				} else {
					addControl.setDisable(true);
					deleteControl.setDisable(true);
				}
			} else {
				if (newNode instanceof TableView<?>) {
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(event -> {
						Device entry = ((TableView<Device>) newNode).getSelectionModel().getSelectedItem();
						
						main.getObjectLayer().deleteDevice(entry.getType(), entry);
					});
				} else {
					deleteControl.setDisable(true);
				}
			}
		});
	}

	public void setUpTable(DeviceGridController.TableType type) {
		try {
			String fxmlPath = null;
			Tab tab = null;
			switch (type) {
			case Collectors:
				fxmlPath = "presentation/view/CollectorsGrid.fxml";
				tab = collectorsTab;
				break;
			case HANDevices:
				fxmlPath = "presentation/view/HANGrid.fxml";
				tab = hanTab;
				break;
			case Meters:
				fxmlPath = "presentation/view/MetersGrid.fxml";
				tab = metersTab;
				break;
			case Routers:
				fxmlPath = "presentation/view/RoutersGrid.fxml";
				tab = routersTab;
				break;
			case Sockets:
				fxmlPath = "presentation/view/SocketsGrid.fxml";
				tab = socketsTab;
				break;
			default:
				break;
			
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlPath));
			AnchorPane table = (AnchorPane) loader.load();
			
			((ScrollPane)((AnchorPane) tab.getContent()).getChildren().get(0)).setContent(table);
			DeviceGridController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
			tab.setUserData(tableController);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpEnvironmentTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/EnvironmentsTab.fxml"));
			BorderPane table = (BorderPane) loader.load();
			
			((AnchorPane) environmentsTab.getContent()).getChildren().add(table);
			EnvironmentsTabController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
			environmentsTab.setUserData(tableController);
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	public void setUpCheckpointsTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/CheckpointsTab.fxml"));
			BorderPane table = (BorderPane) loader.load();
			
			((AnchorPane) checkpointsTab.getContent()).getChildren().add(table);
			CheckpointsTabController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
			checkpointsTab.setUserData(tableController);
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	@FXML 
	private void addCommandCenter() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.COMMANDCENTER);
	}
	
	@FXML 
	private void addCentralServices() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.CENTRALSERVICES);
	}
	
	@FXML 
	private void addSBS() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.SBS);
	}
	
	@FXML 
	private void addNMS() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.NMS);
	}
	
	@FXML 
	private void addPANA() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.PANA);
	}
	
	@FXML 
	private void addGSIS() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.GSIS);
	}
	
	@FXML 
	private void addABNT() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.ABNT);
	}
	
	@FXML 
	private void addAMS() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.AMS);
	}
	
	@FXML
	private void addCM() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.CM);
	}
	
	@FXML 
	private void addANSI() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.ANSI);
	}
	
	@FXML 
	private void addCapabilityServices() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.CAPABILTYSERVICES);
	}
	
	@FXML 
	private void addM2M() {
		main.getObjectLayer().addComponent((Server) ((Node) main.getPresentationLayer().focusedProperty().get()).getUserData(), ComponentType.M2M);
	}
	
	@FXML
	private void showSettings() {
		main.getPresentationLayer().showSettings();
	}
}
