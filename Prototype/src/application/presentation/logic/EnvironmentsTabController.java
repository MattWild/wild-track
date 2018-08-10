package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.environment.Component;
import application.objects.environment.Environment;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class EnvironmentsTabController {
	
	@FXML
	private Accordion environmentsAccordion;
	
	@FXML
	private TextField ccVersionSearchField;
	
	@FXML
	private TextField crcSearchField;
	
	private Main main;
	private SortedList<Environment> sortedEnvironments;
	
	private ListChangeListener<Environment> environmentListener = (Change<? extends Environment> change) -> {
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
	};

	public void setMain(Main main) {
		this.main = main;
	}

	public void enableTable() {
		sortedEnvironments = new SortedList<Environment>(main.getObjectLayer().getEnvironments(), (env1, env2) -> {
			if (env1.getName() == null) {
				if (env2.getName() == null)
					return 0;
				else
					return -1;
			}
			
			if (env2.getName() == null)
				return 1;
			
			return env1.getName().compareTo(env2.getName());
		});
		
		for (Environment environment : sortedEnvironments) {
			buildEnvironmentPane(environment);
		}
		
		sortedEnvironments.addListener(environmentListener);
	}
	
	public void disableTable() {
		environmentsAccordion.getPanes().clear();
		
		sortedEnvironments.removeListener(environmentListener);
	}
	
	public Environment getSelectedEnvironment() {
		if (environmentsAccordion.getExpandedPane() != null) {
			return (Environment) environmentsAccordion.getExpandedPane().getUserData();
		} else {
			return null;
		}
	}

	public ObjectProperty<TitledPane> selectedEnvPaneProp() {
		return environmentsAccordion.expandedPaneProperty();
	}

	private void search() {
		for (TitledPane envPane : environmentsAccordion.getPanes()) {
			Environment environment = (Environment) envPane.getUserData();
			Component commandCenter = environment.getCommandCenter();
			String ccVersionSearchText = ccVersionSearchField.getText();
			String crcSearchText = crcSearchField.getText();
			
			boolean shouldBeVisible = true;
			if (!(ccVersionSearchText == null || ccVersionSearchText.length() == 0 || (
					commandCenter != null && commandCenter.getVersion() != null &&
					(commandCenter.getVersion().contains(ccVersionSearchText) ||
							(main.getObjectLayer().getAliasMapping().get(commandCenter.getVersion()) != null &&
									main.getObjectLayer().getAliasMapping().get(commandCenter.getVersion()).getAlias().contains(ccVersionSearchText)))))) {
				shouldBeVisible = false;
			}
			
			try {
				if (!(crcSearchText == null || crcSearchText.length() == 0 || 
						(environment.getCRC() != 0 && environment.getCRC() == Integer.parseInt(crcSearchText))))
					shouldBeVisible = false;
			} catch (NumberFormatException e) {
				main.errorHandle("Cannot Search by CRC = " + crcSearchText, "CRC search text must be valid integer.");
			}
			
			envPane.setVisible(shouldBeVisible);
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
			controller.setupTable();
			environmentsAccordion.getPanes().add(environmentPane);
			environmentPane.setUserData(environment);
			environmentPane.textProperty().bind(environment.name());
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	@FXML
	private void initialize() {
		ccVersionSearchField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				search();
		});
		
		crcSearchField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				search();
		});
	}
}
