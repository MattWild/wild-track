package application.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.presentation.logic.TableController;
import application.presentation.logic.TableController.TableType;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public abstract class Entry {
	
	protected List<Node> children;
	private CheckBox cb;
	protected Main app;
	
	public Entry(Main app) {
		this.app = app;
		cb = new CheckBox("");
	}

	public List<Node> getChildren() {
		return children;
	}
	
	protected class FieldUpdateHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode().equals(KeyCode.ENTER)) {
				List<List<Object>> temp = new ArrayList<List<Object>>();
				temp.add(getUpdateableValues());
				if (getCRC() != -1) app.getEnvironmentController(getCRC()).stageChange(getThis());
				try {
					app.getMainDBController().updateTableFromTable(getType(), temp);
				} catch (SQLException e) {
					app.errorHandle(e);
				}
			}
		}
	}
	
	private Entry getThis() {
		return this;
	}

	public abstract List<Object> getUpdateableValues();

	public abstract TableType getType();
	
	public abstract int getCRC();

	public CheckBox getCheckBox() {
		return cb;
	}
}
