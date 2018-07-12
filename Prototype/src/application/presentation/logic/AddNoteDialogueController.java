package application.presentation.logic;

import java.sql.SQLException;

import application.Main;
import application.presentation.PresentationLayer;
import application.presentation.logic.TableController.TableType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public abstract class AddNoteDialogueController {
	
	protected Main main;
	protected String identifier;
	protected int CRC;
	protected Stage stage;

	public void setLanAddress(String lanAddress) {
		this.identifier = lanAddress;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	protected void addNote(TableType type, String text) {
		if (text != null) {
			try {
				main.getMainDBController().addNoteTo(type, identifier, text);
				main.getEnvironmentController(CRC).stageNote(identifier, text);
			} catch (SQLException e) {
				main.errorHandle(e);
			}
			stage.close();
		} else {
			main.getPresentationLayer().showError("Invalid Input", "Note cannot be empty.");
		}
	}

	public void setIdentifier(String string) {
		identifier = string;
		setIdentifierLabel(string);
	}

	public void setCRC(int cRC) {
		CRC = cRC;
	}

	protected abstract void setIdentifierLabel(String string);

}
