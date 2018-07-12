package application.presentation.logic;

import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddMetersDialogueController extends AddDeviceDialogueController {
	
	@FXML
	private TextArea meterNoArea;
	
	@FXML
	private void addMeters() {
		finalAdd(meterNoArea, TableType.Meters);
	}
	
	@FXML 
	private void cancel() {
		stage.close();
	}
}
