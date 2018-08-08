package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import application.objects.hardware.Collector;
import application.objects.hardware.Device;
import application.objects.hardware.Device.DeviceType;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class EnvironmentTableController {
	
	@FXML
	private GridPane grid;
	
	@FXML 
	private Label nameLabel;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private ComboBox<String> collectorIPBox;
	
	@FXML
	private Label crcLabel;
	
	@FXML
	private Label notesLabel;
	
	@FXML
	private TextField notesField;
	
	@FXML 
	private Label typeLabel;
	
	@FXML
	private Label radiosLabel;
	
	@FXML
	private Label userLabel;
	
	@FXML
	private TextField userField;
	
	@FXML
	private Label passLabel;
	
	@FXML
	private TextField passField;

	private Main main;
	
	private Environment environment;

	public void setMain(Main main) {
		this.main = main;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		
		initBindings();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void setupTable() {
		grid.getColumnConstraints().add(new ColumnConstraints());
		for (Server server : environment.getServers()) {
			buildServerGrid(server);
		}
		
		environment.getServers().addListener((Change<? extends Server> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Server server : change.getAddedSubList()) {
						buildServerGrid(server);
					}
				} else if (change.wasRemoved()) {
					for (Server server : change.getRemoved()) {
						for (Node node : grid.getChildren()) {
							if (node.getUserData() == server) {
								grid.getChildren().remove(node);
								grid.getColumnConstraints().remove(GridPane.getColumnIndex(node));
								break;
							}
						}
					}
				}
			}
		});
	}

	private void initBindings() {
		
		userLabel.textProperty().bind(userField.textProperty());
		userField.focusedProperty().addListener((arg, oldValue, newValue)-> {
			if (!newValue) userField.setVisible(false);
		});
		
		passLabel.textProperty().bind(passField.textProperty());
		passField.focusedProperty().addListener((arg, oldValue, newValue)-> {
			if (!newValue) passField.setVisible(false);
		});
		
		nameField.focusedProperty().addListener((arg, oldValue, newValue)-> {
			if (!newValue) nameField.setVisible(false);
		});
		nameField.textProperty().bindBidirectional(environment.name());
		nameLabel.textProperty().bind(environment.name());
		
		crcLabel.setText((environment.getCRC() == 0)? null : "" + environment.getCRC());
		environment.crc().addListener((arg, oldValue, newValue) -> {
			if (newValue.intValue() == 0) 
				crcLabel.setText("");
			else
				crcLabel.setText("" + newValue.intValue());
		});
		
		notesField.focusedProperty().addListener((arg, oldValue, newValue) -> {
			if (!newValue) notesField.setVisible(false);
		});
		notesField.textProperty().bindBidirectional(environment.notes());
		notesLabel.textProperty().bind(environment.notes());
		
		
		ObservableList<String> ips = collectorIPBox.getItems();
		ips.add("--");
		for (Device entry : main.getObjectLayer().getDevices(DeviceType.COLLECTORS)) {
			ips.add(((Collector) entry).getIp());
		}
		
		if (environment.getCollector() != null) {
			collectorIPBox.getSelectionModel().select(environment.getCollector().getIp());
		} else {
			collectorIPBox.getSelectionModel().select(0);
		}
		
		collectorIPBox.getSelectionModel().selectedIndexProperty().addListener((arg, oldValue, newValue) -> {
			if (newValue.intValue() > 0 && newValue != oldValue)
				environment.setCollector((Collector) main.getObjectLayer().getDevices(DeviceType.COLLECTORS).get(newValue.intValue() - 1));
			else if (newValue.intValue() == 0)
				environment.setCollector(null);
		});
		
		main.getObjectLayer().getDevices(DeviceType.COLLECTORS).addListener((Change<? extends Device> change) -> {
			while (change.next()) 
				if (change.wasAdded()) {
					for (Device entry : change.getAddedSubList()) {
						String ip = ((Collector) entry).getIp();
						ips.add(ip);
						
						
						if (collectorIPBox.getSelectionModel().isEmpty() && 
								environment.getCollector() != null && 
								environment.getCollector().getIp() != null && 
								environment.getCollector().getIp().compareTo(ip) == 0)
							collectorIPBox.getSelectionModel().select(ip);
					}
				} else if (change.wasRemoved()) {
					for (Device entry : change.getRemoved()) {
						ips.remove(((Collector) entry).getIp());
					}
				}
		});
		
		if (environment.getCollector() != null) {
			Collector collector = environment.getCollector();
			
			typeLabel.textProperty().bind(collector.collectorType());
			radiosLabel.textProperty().bind(collector.radios());
			userField.textProperty().bindBidirectional(collector.username());
			passField.textProperty().bindBidirectional(collector.password());
		}
		
		environment.collector().addListener((arg, oldValue, newValue) -> {
			if (oldValue != null) {
				typeLabel.textProperty().unbind();
				typeLabel.setText(null);
				radiosLabel.textProperty().unbind();
				radiosLabel.setText(null);
				userField.textProperty().unbindBidirectional(oldValue.username());
				userField.setText(null);
				passField.textProperty().unbindBidirectional(oldValue.password());
				passField.setText(null);
			}
			
			
			if (newValue != null) {
				if (newValue.getIp() == null) {
					collectorIPBox.getSelectionModel().clearSelection();
				} else {
					collectorIPBox.getSelectionModel().select(newValue.getIp());
					
					typeLabel.textProperty().bind(newValue.collectorType());
					radiosLabel.textProperty().bind(newValue.radios());
					userField.textProperty().bindBidirectional(newValue.username());
					passField.textProperty().bindBidirectional(newValue.password());
				}
			}
		});
	}

	private void buildServerGrid(Server server) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/ServerGrid.fxml"));
			GridPane serverGrid = (GridPane) loader.load();
			ServerGridController serverController = loader.getController();
			
			serverGrid.setUserData(server);
			serverController.setMain(main);
			serverController.setUpGrid();
			
			grid.add(serverGrid, environment.getServers().indexOf(server), 3);
			ColumnConstraints col = new ColumnConstraints();
			col.setHgrow(Priority.NEVER);
			grid.getColumnConstraints().add(0,col);
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}

	@FXML
	private void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
	
	@FXML
	private void setNameFocus(MouseEvent event) {
		if (event.getClickCount() == 1) {
			Node node = ((Node) event.getSource());
			if(node.getUserData() == null) node.setUserData(environment);
			node.requestFocus();
		} else {
			nameField.setVisible(true);
			nameField.requestFocus();
		}
	}
	
	@FXML
	private void setNotesFocus(MouseEvent event) {
		notesField.setVisible(true);
		notesField.requestFocus();
	}
	
	@FXML
	private void setUserFocus(MouseEvent event) {
		if (environment.getCollector() != null) {
			userField.setVisible(true);
			userField.requestFocus();
		}
	}
	
	@FXML
	private void setPassFocus(MouseEvent event) {
		if (environment.getCollector() != null) {
			passField.setVisible(true);
			passField.requestFocus();
		}
	}
}
