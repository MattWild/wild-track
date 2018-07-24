package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.entities.Component;
import application.objects.entities.Component.ComponentType;
import application.objects.entities.Environment;
import application.objects.entities.Server;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EnvironmentTableController {
	
	@FXML
	private GridPane grid;
	
	@FXML 
	private Label nameLabel;
	
	@FXML
	private TextField nameField;
	
	@FXML
	public void initialize() {
		nameField.focusedProperty().addListener((arg, oldValue, newValue) -> {
			if (!newValue) nameField.setVisible(false);
		});
	}
	
	@FXML
	public void nameLabelClick(MouseEvent event) {
		nameField.setVisible(true);
		nameField.requestFocus();
	}
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
	
	private Environment environment;
	private Main main;

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		
		nameField.textProperty().bindBidirectional(environment.name());
		nameLabel.textProperty().bind(environment.name());
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setUpTable() {
		int i = 0;
		for (Integer serverId : environment.getServerIds()) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("presentation/view/ServerGrid.fxml"));
				GridPane serverGrid = (GridPane) loader.load();
				ServerGridController serverController = loader.getController();
				Pane pane = new Pane();
				pane.getChildren().add(serverGrid);
				Server server = main.getObjectLayer().getServer(serverId);
				serverController.initBindings(server.name(), server.ip(), server.fqdn(), server.type(), server.hasDB(), server.dbType(), server.sysUser(), server.sysPass(), server.port(), server.usesSid(), server.sid());
				grid.add(pane, i, 1);
				i++;
			
				for (Component component : environment.getComponents(serverId)) {
					try {
						loader = new FXMLLoader();
						switch(component.getType()) {
						case ABNT:
							loader.setLocation(Main.class.getResource("presentation/view/ABNTGrid.fxml"));
							break;
						case ANSI:
							loader.setLocation(Main.class.getResource("presentation/view/ANSIGrid.fxml"));
							break;
						case CAPABILTYSERVICES:
							loader.setLocation(Main.class.getResource("presentation/view/CapabilityServicesGrid.fxml"));
							break;
						case CENTRALSERVICES:
							loader.setLocation(Main.class.getResource("presentation/view/CentralServicesGrid.fxml"));
							break;
						case CM:
							loader.setLocation(Main.class.getResource("presentation/view/CMGrid.fxml"));
							break;
						case COLLECTOR:
							loader.setLocation(Main.class.getResource("presentation/view/CollectorGrid.fxml"));
							break;
						case COMMANDCENTER:
							loader.setLocation(Main.class.getResource("presentation/view/CommandCenterGrid.fxml"));
							break;
						case GSIS:
							loader.setLocation(Main.class.getResource("presentation/view/GSISGrid.fxml"));
							break;
						case M2M:
							loader.setLocation(Main.class.getResource("presentation/view/M2MGrid.fxml"));
							break;
						case NMS:
							loader.setLocation(Main.class.getResource("presentation/view/NMSGrid.fxml"));
							break;
						case PANA:
							loader.setLocation(Main.class.getResource("presentation/view/PANAGrid.fxml"));
							break;
						case SBS:
							loader.setLocation(Main.class.getResource("presentation/view/SBSGrid.fxml"));
							break;
						default:
							break;
						
						}
						GridPane componentGrid = (GridPane) loader.load();
						ComponentGridController controller = loader.getController();
						
						controller.initBindings(component.version(), component.user(), component.pass());
						serverController.addComponentGrid(componentGrid);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
