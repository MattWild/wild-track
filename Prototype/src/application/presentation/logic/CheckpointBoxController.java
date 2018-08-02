package application.presentation.logic;

import application.Main;
import application.objects.entities.Checkpoint;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CheckpointBoxController {

	@FXML
	private Label versionLabel;
	
	@FXML
	private TextField versionField;
	
	@FXML
	private StackPane versionLabelHolder;
	
	private Main main;
	private Checkpoint checkpoint;
	
	@FXML
	private void initialize() {
		versionField.setVisible(false);
		
		versionField.focusedProperty().addListener((arg, oldValue, newValue) -> {
			if (newValue == false) versionField.setVisible(false);
		});
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setCheckpoint(Checkpoint checkpoint) {
		this.checkpoint = checkpoint;
		
		initBindings();
	}
	
	private void initBindings() {
		versionField.textProperty().bindBidirectional(checkpoint.version());
		
		versionLabel.textProperty().bind(versionField.textProperty());
		
		versionLabelHolder.setUserData(checkpoint);
	}
	
	@FXML
	private void versionLabelClicked(MouseEvent event) {
		if (event.getClickCount() == 1) {
			((Node) event.getSource()).requestFocus();
		} else {
			versionField.setVisible(true);
			versionField.requestFocus();
		}
	}
	
	@FXML
	private void enterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}

}
