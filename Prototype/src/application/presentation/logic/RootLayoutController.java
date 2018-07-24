package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import application.objects.entities.Environment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RootLayoutController {
	@FXML
	private TabPane tabPane;
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setUpTable(DeviceGridController.TableType type) {
		try {
			String fxmlPath = null;
			switch (type) {
			case Collectors:
				fxmlPath = "presentation/view/CollectorsGrid.fxml";
				break;
			case HANDevices:
				fxmlPath = "presentation/view/HANGrid.fxml";
				break;
			case Meters:
				fxmlPath = "presentation/view/MetersGrid.fxml";
				break;
			case Routers:
				fxmlPath = "presentation/view/RoutersGrid.fxml";
				break;
			case Sockets:
				fxmlPath = "presentation/view/SocketsGrid.fxml";
				break;
			default:
				break;
			
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlPath));
			AnchorPane table = (AnchorPane) loader.load();
			
			((ScrollPane)((AnchorPane) tabPane.getTabs().get(type.ordinal() + 1).getContent()).getChildren().get(0)).setContent(table);
			DeviceGridController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpEnvironmentTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/EnvironmentsTab.fxml"));
			BorderPane table = (BorderPane) loader.load();
			
			((AnchorPane) tabPane.getTabs().get(0).getContent()).getChildren().add(table);
			EnvironmentsTabController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	@FXML
	public void pull() {
		main.pullData();
	}
}
