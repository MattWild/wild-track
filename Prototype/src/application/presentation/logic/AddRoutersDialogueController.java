package application.presentation.logic;

import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddRoutersDialogueController extends AddDeviceDialogueController {
	
	@FXML
	private TextArea routerNoArea;
	
	@FXML
	private void addRouters() {
		finalAdd(routerNoArea, TableType.Routers);
	}
	
	@FXML 
	private void cancel() {
		stage.close();
	}
}
