package application.presentation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import application.entities.Entry;
import application.presentation.logic.AddDeviceDialogueController;
import application.presentation.logic.AddNoteDialogueController;
import application.presentation.logic.DeleteDeviceDialogueController;
import application.presentation.logic.RootLayoutController;
import application.presentation.logic.TableController;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PresentationLayer {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Map<TableType,TableController> grids;
	private Main main;

	public PresentationLayer(Main main) {
		this.main = main;
		grids = new HashMap<TableType, TableController>();
	}

	public void showMain(Stage stage) {
		try {
			primaryStage = stage;
			primaryStage.setTitle("L3 Asset Manager: Prototype");
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			RootLayoutController controller = loader.getController();
			controller.setMain(main);
			//for (TableType type : TableType.values())
				controller.setUpTable(TableType.Meters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGrid(TableType type, TableController controller) {
		grids.put(type, controller);
	}
	
	public void showError(String errorTitle, String errorMsg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(errorTitle);
		alert.setContentText(errorMsg);
		
		alert.showAndWait();
	}
	


	public void showAddNoteToDevice(Entry entry, TableType type) {
		try {
			FXMLLoader loader = new FXMLLoader();
			switch(type) {
			case Meters:
				loader.setLocation(Main.class.getResource("presentation/view/AddNoteToMeterDialogue.fxml"));
				break;
			case Routers:
				loader.setLocation(Main.class.getResource("presentation/view/AddNoteToRouterDialogue.fxml"));
				break;
			default:
				break;
			}
			AnchorPane addNoteDialog = (AnchorPane) loader.load();
			
			Stage dialogStage= new Stage();
			dialogStage.setTitle("Add Note");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(addNoteDialog);
			dialogStage.setScene(scene);
			
			AddNoteDialogueController controller = loader.getController();
			controller.setMain(main);
			controller.setStage(dialogStage);
			controller.setIdentifier(((Label) entry.getChildren().get(0)).getText());
			switch(type) {
			case Meters:
				controller.setCRC(Integer.parseInt(((Label) entry.getChildren().get(3)).getText()));
				break;
			case Routers:
				controller.setCRC(Integer.parseInt(((Label) entry.getChildren().get(1)).getText()));
				break;
			default:
				break;
			}
			dialogStage.showAndWait();
			
			grids.get(type).refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDeleteDevice(List<Entry> entries, TableType type) {
		try {
			FXMLLoader loader = new FXMLLoader();
			switch(type) {
			case Meters:
				loader.setLocation(Main.class.getResource("presentation/view/DeleteMetersDialogue.fxml"));
				break;
			case Collectors:
				loader.setLocation(Main.class.getResource("presentation/view/DeleteCollectorsDialogue.fxml"));
				break;
			case HANDevices:
				loader.setLocation(Main.class.getResource("presentation/view/DeleteHANDialogue.fxml"));
				break;
			case Routers:
				loader.setLocation(Main.class.getResource("presentation/view/DeleteRoutersDialogue.fxml"));
				break;
			case Sockets:
				loader.setLocation(Main.class.getResource("presentation/view/DeleteSocketsDialogue.fxml"));
				break;
			default:
				break;
			}
			AnchorPane deleteMetersDialog = (AnchorPane) loader.load();
			
			Stage dialogStage= new Stage();
			dialogStage.setTitle("Delete Device");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(deleteMetersDialog);
			dialogStage.setScene(scene);
			
			DeleteDeviceDialogueController controller = loader.getController();
			controller.setMain(main);
			controller.setStage(dialogStage);
			controller.setEntries(entries);
			dialogStage.showAndWait();
			
			grids.get(type).refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAddDevice(TableType type) {
		try {
			FXMLLoader loader = new FXMLLoader();
			switch(type) {
			case Meters:
				loader.setLocation(Main.class.getResource("presentation/view/AddMetersDialogue.fxml"));
				break;
			case Collectors:
				loader.setLocation(Main.class.getResource("presentation/view/AddCollectorsDialogue.fxml"));
				break;
			case HANDevices:
				loader.setLocation(Main.class.getResource("presentation/view/AddHANDialogue.fxml"));
				break;
			case Routers:
				loader.setLocation(Main.class.getResource("presentation/view/AddRoutersDialogue.fxml"));
				break;
			case Sockets:
				loader.setLocation(Main.class.getResource("presentation/view/AddSocketsDialogue.fxml"));
				break;
			default:
				break;
			}
			AnchorPane dialog = (AnchorPane) loader.load();
			
			Stage dialogStage= new Stage();
			dialogStage.setTitle("Add Device");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(dialog);
			dialogStage.setScene(scene);
			
			AddDeviceDialogueController controller = loader.getController();
			controller.setMain(main);
			controller.setStage(dialogStage);
			dialogStage.showAndWait();
			
			grids.get(type).refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
