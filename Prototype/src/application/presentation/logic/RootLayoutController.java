package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class RootLayoutController {
	@FXML
	TabPane tabPane;
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setUpTable(TableController.TableType type) {
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
			TableController tableController = loader.getController();
			tableController.setMain(main);
			tableController.populateTable();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void pull() {
		main.pullData();
	}
}
