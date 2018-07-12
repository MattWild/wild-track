package application.presentation.logic;

import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddCollectorsDialogueController extends AddDeviceDialogueController {
	
	@FXML
	private TextArea collectorIPArea;
	
	@FXML
	private void addCollectors() {
		finalAdd(collectorIPArea, TableType.Collectors);
	}
	
	@FXML 
	private void cancel() {
		stage.close();
	}
}
