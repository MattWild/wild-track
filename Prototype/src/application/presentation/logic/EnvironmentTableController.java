package application.presentation.logic;

import java.io.IOException;
import java.io.StringBufferInputStream;

import application.Main;
import application.objects.entities.Collector;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Entry;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.converter.IntegerStringConverter;

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
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
	
	@FXML
	public void setNameFocus(MouseEvent event) {
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
	public void setUserFocus(MouseEvent event) {
		if (environment.getCollector() != null) {
			userField.setVisible(true);
			userField.requestFocus();
		}
	}
	
	@FXML
	public void setPassFocus(MouseEvent event) {
		if (environment.getCollector() != null) {
			passField.setVisible(true);
			passField.requestFocus();
		}
	}
	
	private Environment environment;
	private Main main;

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		
		initBindings();
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
		
		ObservableList<String> ips = collectorIPBox.getItems();
		ips.add("--");
		for (Entry entry : main.getObjectLayer().getDevices(TableType.Collectors)) {
			ips.add(((Collector) entry).getIp());
		}
		
		if (environment.getCollector() != null) {
			collectorIPBox.getSelectionModel().select(environment.getCollector().getIp());
		} else {
			collectorIPBox.getSelectionModel().select(0);
		}
		
		collectorIPBox.getSelectionModel().selectedIndexProperty().addListener((arg, oldValue, newValue) -> {
			if (newValue.intValue() > 0 && newValue != oldValue)
				environment.setCollector((Collector) main.getObjectLayer().getDevices(TableType.Collectors).get(newValue.intValue() - 1));
			else if (newValue.intValue() == 0)
				environment.setCollector(null);
		});
		
		main.getObjectLayer().getDevices(TableType.Collectors).addListener((Change<? extends Entry> change) -> {
			while (change.next()) 
				if (change.wasAdded()) {
					for (Entry entry : change.getAddedSubList()) {
						String ip = ((Collector) entry).getIp();
						ips.add(ip);
						
						
						if (collectorIPBox.getSelectionModel().isEmpty() && 
								environment.getCollector() != null && 
								environment.getCollector().getIp() != null && 
								environment.getCollector().getIp().compareTo(ip) == 0)
							collectorIPBox.getSelectionModel().select(ip);
					}
				} else if (change.wasRemoved()) {
					for (Entry entry : change.getRemoved()) {
						ips.remove(((Collector) entry).getIp());
					}
				}
		});
		
		if (environment.getCollector() != null) {
			Collector collector = environment.getCollector();
			
			crcLabel.textProperty().bind(collector.netId());
			typeLabel.textProperty().bind(collector.type());
			radiosLabel.textProperty().bind(collector.radios());
			userField.textProperty().bindBidirectional(collector.username());
			passField.textProperty().bindBidirectional(collector.password());
		}
		
		environment.collector().addListener((arg, oldValue, newValue) -> {
			if (oldValue != null) {
				crcLabel.textProperty().unbind();
				crcLabel.setText(null);
				typeLabel.textProperty().unbind();
				typeLabel.setText(null);
				radiosLabel.textProperty().unbind();
				radiosLabel.setText(null);
				userLabel.textProperty().unbindBidirectional(oldValue.username());
				userLabel.setText(null);
				passLabel.textProperty().unbindBidirectional(oldValue.password());
				passLabel.setText(null);
			}
			
			
			if (newValue != null) {
				if (newValue.getIp() == null) {
					collectorIPBox.getSelectionModel().clearSelection();
				} else {
					collectorIPBox.getSelectionModel().select(newValue.getIp());
					
					crcLabel.textProperty().bind(newValue.netId());
					typeLabel.textProperty().bind(newValue.type());
					radiosLabel.textProperty().bind(newValue.radios());
					userField.textProperty().bindBidirectional(newValue.username());
					passField.textProperty().bindBidirectional(newValue.password());
				}
			}
		});
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void setUpTable() {
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
	
	private void buildServerGrid(Server server) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/ServerGrid.fxml"));
			GridPane serverGrid = (GridPane) loader.load();
			ServerGridController serverController = loader.getController();
			
			serverGrid.setUserData(server);
			serverController.setMain(main);
			serverController.setServer(server);
			serverController.initBindings();
			serverController.setUpGrid();
			
			grid.add(serverGrid, environment.getServers().indexOf(server), 2);
			ColumnConstraints col = new ColumnConstraints();
			col.setHgrow(Priority.NEVER);
			grid.getColumnConstraints().add(0,col);
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}

}
