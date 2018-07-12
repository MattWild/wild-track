package application.presentation.logic;

import application.Main;
import application.presentation.PresentationLayer;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddNoteToMeterDialogueController extends AddNoteDialogueController {

	@FXML
	private TextArea noteArea;
	
	@FXML Label meterNoLabel;
	
	@FXML
	private void addNoteToMeter() {
		addNote(TableType.Meters, noteArea.getText());
	}
	
	@FXML
	private void cancel() {
		stage.close();
	}
	
	@Override
	protected void setIdentifierLabel(String string) {
		meterNoLabel.setText(string);
	}

}
