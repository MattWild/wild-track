package application.presentation.logic;

import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddHANDialogueController extends AddDeviceDialogueController {
	
	@FXML
	private TextArea HANUnitIDArea;
	
	@FXML
	private void addHAN() {
		finalAdd(HANUnitIDArea, TableType.HANDevices);
	}
	
	@FXML 
	private void cancel() {
		stage.close();
	}
}
