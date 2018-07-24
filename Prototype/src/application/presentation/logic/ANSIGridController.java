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

public class ANSIGridController implements ComponentGridController {
	
	@FXML
	private Label versionLabel;
	
	@FXML
	private TextField versionField;
	
	@FXML
	private Label userLabel;
	
	@FXML
	private TextField userField;
	
	@FXML
	private Label passLabel;
	
	@FXML
	private TextField passField;
	
	@FXML
	private void initialize() {
		versionField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) versionField.setVisible(false);
		});
		
		userField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) userField.setVisible(false);
		});
		
		passField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) passField.setVisible(false);
		});
	}
	
	@Override
	public void initBindings(SimpleStringProperty versionProp, SimpleStringProperty userProp, SimpleStringProperty passProp) {
		versionField.setText(versionProp.get());
		userField.setText(userProp.get());
		passField.setText(passProp.get());
		
		versionProp.bindBidirectional(versionField.textProperty());
		userProp.bindBidirectional(userField.textProperty());
		passProp.bindBidirectional(passField.textProperty());
		
		versionLabel.textProperty().bind(versionField.textProperty());
		userLabel.textProperty().bind(userField.textProperty());
		passLabel.textProperty().bind(passField.textProperty());
	}

	@FXML
	public void versionLabelClick(MouseEvent event) {
		versionField.setVisible(true);
		versionField.requestFocus();
	}
	
	@FXML
	public void userLabelClick(MouseEvent event) {
		userField.setVisible(true);
		userField.requestFocus();
	}
	
	@FXML
	public void passLabelClick(MouseEvent event) {
		passField.setVisible(true);
		passField.requestFocus();
	}
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
}
