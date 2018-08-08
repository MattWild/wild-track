package application;

import java.sql.SQLException;

import application.data.DataLayer;
import application.objects.ObjectLayer;
import application.presentation.PresentationLayer;
import application.services.ServiceLayer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private DataLayer dataLayer;
	private PresentationLayer presentationLayer;
	private ObjectLayer objectLayer;
	private ServiceLayer serviceLayer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setUserAgentStylesheet(STYLESHEET_MODENA);
		
		dataLayer = new DataLayer();
		objectLayer = new ObjectLayer(this);
		serviceLayer = new ServiceLayer(this);
		presentationLayer = new PresentationLayer(this);
		try {
			objectLayer.loadSettings();
			objectLayer.loadEnvironmentDetails();
			objectLayer.loadDeviceDetails();
			presentationLayer.showMain(stage);
		} catch (Exception e) {
			errorHandle(e);
		}
	}
	
	@Override
	public void init() throws Exception {
		System.out.println("Setting up connection to main database.");
	}
	
	@Override
	public void stop() {
		System.out.println("\nClosing database connection.");
		System.out.println("\nClosing connection to main database.");
		try {
			dataLayer.close();
		} catch (SQLException e) {
			errorHandle(e);
		}
	}

	public DataLayer getDataLayer() {
		return dataLayer;
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
	
	public void errorHandle(String errorTitle, String errorMessage) {
		if (presentationLayer != null)
			presentationLayer.showError(errorTitle, errorMessage);
	}
}
