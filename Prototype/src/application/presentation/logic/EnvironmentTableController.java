package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EnvironmentTableController {
	
	@FXML
	private GridPane grid;
	
	@FXML 
	private Label nameLabel;
	
	@FXML
	private TextField nameField;
	
	@FXML
	public void initialize() {
		nameField.focusedProperty().addListener((arg, oldValue, newValue) -> {
			if (!newValue) nameField.setVisible(false);
		});
	}
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
	
	@FXML
	public void setFocus(MouseEvent event) {
		if (event.getClickCount() == 1) {
			Node node = ((Node) event.getSource());
			if(node.getUserData() == null) node.setUserData(environment);
			node.requestFocus();
		} else {
			nameField.setVisible(true);
			nameField.requestFocus();
		}
	}
	
	private Environment environment;
	private Main main;

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		
		nameField.focusedProperty().addListener((arg, oldValue, newValue)-> {
			if (!newValue) nameField.setVisible(false);
		});
		
		nameField.textProperty().bindBidirectional(environment.name());
		nameLabel.textProperty().bind(environment.name());
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void setUpTable() {
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
			
			Pane pane = new Pane();
			pane.getChildren().add(serverGrid);
			pane.setUserData(server);
			serverController.setMain(main);
			serverController.setServer(server);
			serverController.initBindings();
			serverController.setUpGrid();
			
			grid.add(pane, grid.getColumnConstraints().size() - 1, 1);
			grid.getColumnConstraints().add(new ColumnConstraints());
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}

}
