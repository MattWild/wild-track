package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Entry;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class EnvironmentsTabController {
	
	@FXML
	private Accordion environmentsAccordion;
	
	@FXML
	private MenuItem addControl;
	
	@FXML
	private Menu addComponentMenu;
	
	@FXML
	private MenuItem deleteControl;
	
	private Main main;

	public void setMain(Main main) {
		this.main = main;
		

		main.getPresentationLayer().focusedProperty().addListener((ObservableValue<? extends Node> change, Node oldNode, Node newNode)-> {
			if (newNode != null) {
				Object focused = newNode.getUserData();
				
				
				System.out.println(newNode + " " + focused);
				if (focused == null) {
					addControl.setVisible(true);
					addControl.setDisable(false);
					addControl.setText("Add Environment");
					addControl.setOnAction(action -> {
						main.getObjectLayer().addEnvironment();
					});
					
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(true);
				} else if (focused instanceof Environment) {
					addControl.setVisible(true);
					addControl.setDisable(false);
					addControl.setText("Add Server");
					addControl.setOnAction(action -> {
						main.getObjectLayer().addServer((Environment) focused);
					});
	
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteEnvironment((Environment) focused);
					});
				} else if (focused instanceof Server) {
					addControl.setVisible(false);
					addControl.setDisable(true);
					
					addComponentMenu.setVisible(true);
					addComponentMenu.setDisable(false);
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteServer((Server) focused);
					});
				} else if (focused instanceof Component) {
					addControl.setVisible(true);
					addControl.setDisable(true);
					addControl.setText("Add");
					
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(false);
					deleteControl.setOnAction(action -> {
						main.getObjectLayer().deleteComponent((Component) focused);
					});
				} else {
					addControl.setVisible(true);
					addControl.setDisable(false);
					addControl.setText("Add Environment");
					addControl.setOnAction(action -> {
						main.getObjectLayer().addEnvironment();
					});
					
					addComponentMenu.setVisible(false);
					addComponentMenu.setDisable(true);
					
					deleteControl.setDisable(true);
				}
			}
		});
	}
	
	@FXML
	public void initialize() {
		addControl.setDisable(true);
		deleteControl.setDisable(true);
	}

	public void populateTable() {
		for (Environment environment : main.getObjectLayer().getEnvironments()) {
			buildEnvironmentPane(environment);
		}
		
		main.getObjectLayer().getEnvironments().addListener((Change<? extends Environment> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Environment environment : change.getAddedSubList()) {
						buildEnvironmentPane(environment);
					}
				} else if (change.wasRemoved()) {
					for (Environment environment : change.getRemoved()) {
						for (TitledPane environmentPane : environmentsAccordion.getPanes()) {
							if (environmentPane.getUserData() == environment) {
								environmentsAccordion.getPanes().remove(environmentPane);
								break;
							}
						}
					}
				}
			}
		});
	}
	
	private void buildEnvironmentPane(Environment environment) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/EnvironmentTable.fxml"));
			AnchorPane table = (AnchorPane) loader.load();
			EnvironmentTableController controller = loader.getController();
			
			TitledPane environmentPane = new TitledPane();
			environmentPane.setContent(table);
			controller.setMain(main);
			controller.setEnvironment(environment);
			controller.setUpTable();
			environmentsAccordion.getPanes().add(environmentPane);
			environmentPane.setUserData(environment);
			environmentPane.textProperty().bind(environment.name());
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	@FXML
	public void saveEnvironment() {
		try {
			main.getObjectLayer().saveEnvironment((Environment) environmentsAccordion.getExpandedPane().getUserData());
		} catch (SQLException e) {
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
}
