package application.presentation.logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.presentation.PresentationLayer;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public abstract class MenuController {

	private TableType type;
	protected Main main;
	protected ScrollPane scroll;
	private AnchorPane anchor;
	protected TableController controller;
	protected MenuBar menu;
	
	public MenuController(TableType type) {
		this.type = type;
	}
	
	public TableType getType() {
		return type;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setupGrid() {
		initialize();
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(getChildString()));
			anchor = (AnchorPane) loader.load();
			
			scroll.setContent(anchor);
			controller = loader.getController();
			controller.setMain(main);
			try {
				controller.populateTable();
			} catch (SQLException e) {
				main.errorHandle(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract String getChildString();
	
	protected abstract void initialize();
}
