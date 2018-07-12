package application.presentation.logic;

import application.Main;
import application.presentation.PresentationLayer;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddNoteToRouterDialogueController extends AddNoteDialogueController {

	@FXML
	private TextArea noteArea;
	
	@FXML Label routerNoLabel;
	
	@FXML
	private void addNoteToRouter() {
		addNote(TableType.Routers, noteArea.getText());
	}
	
	@FXML
	private void cancel() {
		stage.close();
	}
	
	@Override
	protected void setIdentifierLabel(String string) {
		routerNoLabel.setText(string);
	}

}
