package application.presentation.logic;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry;
import application.presentation.PresentationLayer;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DeleteHANDialogueController extends DeleteDeviceDialogueController{
	private List<CheckBox> cbs;
	
	@FXML
	private GridPane options;
	
	@FXML
	private void deleteHAN() {
		finalDelete(TableType.HANDevices);
	}
	
	@FXML
	private void cancel() {
		stage.close();
	}
	
	@Override
	protected List<CheckBox> getCB() {
		return cbs;
	}

	@Override
	protected GridPane getOptions() {
		return options;
	}

	@Override
	protected void setCB(List<CheckBox> cbs) {
		this.cbs = cbs;
	}

}
