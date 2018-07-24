package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import application.objects.entities.Environment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class EnvironmentsTabController {
	
	@FXML
	private Accordion environmentsAccordion;
	
	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public void populateTable() {
		try {
			for (Environment environment : main.getObjectLayer().getEnvironments()) {
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
			}
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
}
