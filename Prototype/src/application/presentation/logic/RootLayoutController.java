package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.presentation.PresentationLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class RootLayoutController {
	@FXML
	TabPane tabPane;
	
	private Main main;
	private AnchorPane metersMenu;
	private MetersMenuController metersMenuController;
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setUpTable(TableController.TableType type) {
		try {
			String fxmlPath = null;
			switch (type) {
			case Collectors:
				fxmlPath = "presentation/view/CollectorsMenu.fxml";
				break;
			case HANDevices:
				fxmlPath = "presentation/view/HANMenu.fxml";
				break;
			case Meters:
				fxmlPath = "presentation/view/MetersMenu.fxml";
				break;
			case Routers:
				fxmlPath = "presentation/view/RoutersMenu.fxml";
				break;
			case Sockets:
				fxmlPath = "presentation/view/SocketsMenu.fxml";
				break;
			default:
				break;
			
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlPath));
			AnchorPane menu = (AnchorPane) loader.load();
			
			((AnchorPane) tabPane.getTabs().get(type.ordinal()).getContent()).getChildren().add(menu);
			MenuController menuController = loader.getController();
			menuController.setMain(main);
			menuController.setupGrid();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void pull() {
		main.pullData();
	}
	
	@FXML void push() {
		main.pushData();
	}
}
