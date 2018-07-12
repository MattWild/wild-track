package application.presentation.logic;

import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddSocketsDialogueController extends AddDeviceDialogueController {
	
	@FXML
	private TextArea socketIDArea;
	
	@FXML
	private void addSockets() {
		finalAdd(socketIDArea, TableType.Sockets);
	}
	
	@FXML 
	private void cancel() {
		stage.close();
	}
}
