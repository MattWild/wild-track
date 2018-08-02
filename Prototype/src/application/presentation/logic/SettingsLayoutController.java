package application.presentation.logic;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SettingsLayoutController {

	@FXML
	private Tab versionAliasTab;
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setupTabs() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/VersionAliasesGrid.fxml"));
			GridPane grid = (GridPane) loader.load();
			
			versionAliasTab.setContent(grid);
			VersionAliasesGridController controller = loader.getController();
			controller.setMain(main);
			controller.populateTable();
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
}
