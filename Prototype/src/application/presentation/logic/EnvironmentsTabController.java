package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Device;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class EnvironmentsTabController {
	
	@FXML
	private Accordion environmentsAccordion;
	
	@FXML
	private TextField ccVersionSearchField;
	
	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public void populateTable() {
		ccVersionSearchField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				search();
		});
		
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
	
	private void search() {
		for (TitledPane envPane : environmentsAccordion.getPanes()) {
			Component commandCenter = ((Environment) envPane.getUserData()).getCommandCenter();
			String searchText = ccVersionSearchField.getText();
			if (searchText == null || searchText.length() == 0 || (
					commandCenter != null && commandCenter.getVersion() != null &&
					(commandCenter.getVersion().contains(searchText) ||
							(main.getObjectLayer().getAliasMapping().get(commandCenter.getVersion()) != null &&
									main.getObjectLayer().getAliasMapping().get(commandCenter.getVersion()).getAlias().contains(searchText))))) {
				envPane.setVisible(true);
			} else {
				envPane.setVisible(false);
			}
		}
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
	
	public Environment getSelectedEnvironment() {
		if (environmentsAccordion.getExpandedPane() != null) {
			return (Environment) environmentsAccordion.getExpandedPane().getUserData();
		} else {
			return null;
		}
	}

	public ObjectProperty<TitledPane> selectedEnvironmentProperty() {
		return environmentsAccordion.expandedPaneProperty();
	}
}
