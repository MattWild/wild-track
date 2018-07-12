package application.presentation.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.entities.Entry;
import application.presentation.logic.TableController.TableType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class DeleteDeviceDialogueController {
	
	protected Main main;
	protected Stage stage;
	
	protected void finalDelete(TableType type) {
		List<String> lans = new ArrayList<String>();
		
		for(CheckBox cb : getCB()) {
			if (cb.isSelected()) lans.add(cb.getText());
		}
		
		if (!lans.isEmpty())
			try {
				main.getMainDBController().delete(type, lans.toArray(new String[] {}));
			} catch (SQLException e) {
				main.errorHandle(e);
			}
		
		stage.close();
	}

	public void setEntries(List<Entry> entries) {
		setCB(new ArrayList<CheckBox>());
		
		for (int i = 0; i < entries.size(); i++) {
			CheckBox cb = new CheckBox(((Label) entries.get(i).getChildren().get(0)).getText());
			cb.setSelected(true);
			
			getCB().add(cb);
			getOptions().add(cb, 1, i);
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	protected abstract List<CheckBox> getCB();
	
	protected abstract GridPane getOptions();
	
	protected abstract void setCB(List<CheckBox> arrayList);

}
