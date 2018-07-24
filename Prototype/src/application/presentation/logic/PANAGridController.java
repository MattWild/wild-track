package application.presentation.logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PANAGridController implements ComponentGridController {
	
	@FXML
	private Label versionLabel;
	
	@FXML
	private TextField versionField;
	
	@FXML
	private void initialize() {
		versionField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) versionField.setVisible(false);
		});
	}
	
	@Override
	public void initBindings(SimpleStringProperty versionProp, SimpleStringProperty userProp, SimpleStringProperty passProp) {
		versionField.setText(versionProp.get());
		
		versionProp.bindBidirectional(versionField.textProperty());
		
		versionLabel.textProperty().bind(versionField.textProperty());
	}

	@FXML
	public void versionLabelClick(MouseEvent event) {
		versionField.setVisible(true);
		versionField.requestFocus();
	}
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
}
