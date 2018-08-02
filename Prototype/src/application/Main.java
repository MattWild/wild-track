package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.database.MainDataController;
import application.object.ObjectLayer;
import application.presentation.PresentationLayer;
import application.presentation.logic.DeviceGridController.TableType;
import application.services.EnvironmentDatabaseService;
import application.services.ServiceLayer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private MainDataController mainController;
	private List<EnvironmentDatabaseService> environmentControllers;
	private PresentationLayer presentationLayer;
	private ObjectLayer objectLayer;
	private ServiceLayer serviceLayer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setUserAgentStylesheet(STYLESHEET_MODENA);
		
		mainController = new MainDataController();
		objectLayer = new ObjectLayer(this);
		serviceLayer = new ServiceLayer(this);
		presentationLayer = new PresentationLayer(this);
		try {
			objectLayer.loadVersionAliases();
			objectLayer.loadEnvironments();
			for (TableType type : TableType.values()) objectLayer.loadDevices(type);
			presentationLayer.showMain(stage);
		} catch (Exception e) {
			errorHandle(e);
		}
	}
	
	@Override
	public void init() throws Exception {
		System.out.println("Setting up connection to main database.");
		environmentControllers = new ArrayList<EnvironmentDatabaseService>();
	}
	
	public void pullData() {
		System.out.println("\nPulling and updating device data.");
		System.out.println("Removing leftover device to environment relationships.");
		try {
			mainController.clearEnvironRelationships();
		} catch (SQLException e) {
			errorHandle(e);
		}
		for (EnvironmentDatabaseService controller : environmentControllers) {
			System.out.println("Pulling data from environment with CRC=");
			try {
				mainController.updateTableFromEnvironment(TableType.Meters, controller.getData());
				mainController.updateTableFromEnvironment(TableType.Routers, controller.getData());
			} catch (SQLException e) {
				errorHandle(e);
			}
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
		
		for (EnvironmentDatabaseService controller : environmentControllers) {
			System.out.println("Closing connection to environment with CRC=");
			try {
				controller.close();
			} catch (SQLException e) {
				errorHandle(e);
			}
		}
	}

	public MainDataController getMainDBController() {
		return mainController;
	}

	public ObjectLayer getObjectLayer() {
		return objectLayer;
	}
	
	public ServiceLayer getServiceLayer() {
		return serviceLayer;
	}
	
	public PresentationLayer getPresentationLayer()  {
		return presentationLayer;
	}
	
	public void errorHandle(Exception e) {
		e.printStackTrace();
		if (presentationLayer != null)
			presentationLayer.showError(e.toString(), e.getMessage());
	}
}
