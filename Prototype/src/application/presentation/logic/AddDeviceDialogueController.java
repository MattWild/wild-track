package application.presentation.logic;

import java.sql.SQLException;

import application.Main;
import application.presentation.logic.TableController.TableType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public abstract class AddDeviceDialogueController {
	
	protected Main main;
	protected Stage stage;

	public void setMain(Main main) {
		this.main = main;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	protected void finalAdd(TextArea identifierArea, TableType type) {
		String[] result = identifierArea.getText().split(",");
		for(int i = 0; i < result.length; i++) result[i] = result[i].trim();
		try {
			main.getMainDBController().add(type, result);
		} catch (SQLException e) {
			main.errorHandle(e);
		}
		
		stage.close();
	}

}
