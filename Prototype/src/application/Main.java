package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.database.AppDatabase;
import application.database.CentralServicesDataController;
import application.database.DataController;
import application.database.MainDataController;
import application.database.SQLDB;
import application.entities.Entry;
import application.object.ObjectLayer;
import application.presentation.PresentationLayer;
import application.presentation.logic.AddMetersDialogueController;
import application.presentation.logic.AddNoteDialogueController;
import application.presentation.logic.DeleteMetersDialogueController;
import application.presentation.logic.RootLayoutController;
import application.presentation.logic.TableController;
import application.presentation.logic.TableController.TableType;

import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static final String MAIN_ADDRESS = "10.1.220.223";
	private static final String MAIN_DBNAME = "EnvironmentManagement";
	private static final String MAIN_USER = "EscalationsTeam";
	private static final String MAIN_PASS = "1Esc2ala!tions";
	
	private MainDataController mainController;
	private Map<Integer, CentralServicesDataController> environmentControllers;
	private PresentationLayer presentationLayer;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		System.out.println("Setting up connection to main database.");
		mainController = new MainDataController(MAIN_ADDRESS, MAIN_USER, MAIN_PASS, MAIN_DBNAME);
		environmentsInit();
	}
	
	public void pullData() {
		System.out.println("\nPulling and updating device data.");
		System.out.println("Removing leftover device to environment relationships.");
		try {
			mainController.clearEnvironRelationships();
		} catch (SQLException e) {
			errorHandle(e);
		}
		for (Integer CRC : environmentControllers.keySet()) {
			System.out.println("Pulling data from environment with CRC=" + CRC);
			CentralServicesDataController controller = environmentControllers.get(CRC);
			try {
				mainController.updateTableFromEnvironment(TableType.Meters, controller.retrieveData(TableType.Meters));
				mainController.updateTableFromEnvironment(TableType.Routers, controller.retrieveData(TableType.Routers));
			} catch (SQLException e) {
				errorHandle(e);
			}
		}
	}

	private void environmentsInit() {
		System.out.println("\nInitializing Environments");
		List<List<Object>> environmentParams = null;
		try {
			environmentParams = mainController.getEnvironmentConnectionParameters();
		} catch (SQLException e) {
			errorHandle(e);
			System.exit(-1);
		}
		environmentControllers = new HashMap<Integer, CentralServicesDataController>();
		
		for (List<Object> params : environmentParams) {
			try {
				System.out.println("Setting up connection to environment at " + params.get(2));
				CentralServicesDataController controller = new CentralServicesDataController();
				if (((String) params.get(1)).compareTo("1") == 0) {
					controller.initSQLDB(
							(String) params.get(2), 
							(String) params.get(3), 
							(String) params.get(4), 
							"CentralServices");
				} else {
					controller.initOracleDB(
							(String) params.get(2), 
							(String) params.get(3), 
							(String) params.get(4), 
							(String) params.get(5), 
							(String) params.get(6),
							(String) params.get(7));
				}
				environmentControllers.put(Integer.parseInt((String) params.get(0)),controller);
			} catch (Exception e) {
				System.err.println("Could not connect to Server at ip: " + params.get(2) + "\n Error Message:" + e.getMessage());
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		presentationLayer = new PresentationLayer(this);
		
		try {
			presentationLayer.showMain(stage);
		} catch (Exception e) {
			errorHandle(e);
		}
	}
	
	@Override
	public void stop() {
		System.out.println("\nClosing database connection.");
		System.out.println("\nClosing connection to main database.");
		try {
			mainController.close();
		} catch (SQLException e) {
			errorHandle(e);
		}
		
		pushData();
		for (Integer CRC : environmentControllers.keySet()) {
			System.out.println("Closing connection to environment with CRC=" + CRC);
			CentralServicesDataController controller = environmentControllers.get(CRC);
			try {
				controller.close();
			} catch (SQLException e) {
				errorHandle(e);
			}
		}
	}
	
	public void pushData() {
		for (Integer CRC : environmentControllers.keySet()) {
			System.out.println("Pushing data to environment with CRC=" + CRC);
			CentralServicesDataController controller = environmentControllers.get(CRC);
			try {
				controller.pushChanges();
			} catch (SQLException e) {
				errorHandle(e);
			}
		}
	}

	public MainDataController getMainDBController() {
		return mainController;
	}
	
	public CentralServicesDataController getEnvironmentController(int crc) {
		return environmentControllers.get(crc);
	}
	
	public PresentationLayer getPresentationLayer()  {
		return presentationLayer;
	}
	
	public void errorHandle(Exception e) {
		e.printStackTrace();
		if (presentationLayer != null)
			presentationLayer.showError(e.toString(), e.getMessage());
	}

	public ObjectLayer getObjectLayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
