package application.presentation.logic;

import application.objects.entities.Component;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ComponentGridController {
	
	@FXML
	private GridPane grid;
	
	@FXML
	private HBox componentLabelBox;
	
	@FXML 
	private Label componentLabel;
	
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
	
	private Component component;
	
	public void setComponent(Component component) {
		this.component = component;
		
		switch (component.getType()) {
		case ABNT:
			componentLabel.setText("ABNT");
			break;
		case ANSI:
			componentLabel.setText("ANSI");
			break;
		case CAPABILTYSERVICES:
			componentLabel.setText("Capability Services");
			break;
		case CENTRALSERVICES:
			componentLabel.setText("Central Services");
			hideVersion();
			break;
		case CM:
			componentLabel.setText("CM");
			break;
		case COLLECTOR:
			componentLabel.setText("Collector");
			break;
		case COMMANDCENTER:
			componentLabel.setText("Command Center");
			break;
		case GSIS:
			componentLabel.setText("GSIS");
			hideUsername();
			hidePassword();
			break;
		case M2M:
			componentLabel.setText("M2M");
			hideUsername();
			hidePassword();
			break;
		case NMS:
			componentLabel.setText("NMS");
			break;
		case PANA:
			componentLabel.setText("PANA");
			hideUsername();
			hidePassword();
			break;
		case SBS:
			componentLabel.setText("SBS");
			break;
		default:
			break;
		}
		
		componentLabelBox.setUserData(component);
	}
	
	private void hideVersion() {
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) != null) {
				if (GridPane.getRowIndex(child) == 1) {
					child.setVisible(false);
				}
			}
		}
		grid.getRowConstraints().get(1).setMaxHeight(0);
	}
	
	private void hideUsername() {
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) != null) {
				if (GridPane.getRowIndex(child) == 2) {
					child.setVisible(false);
				}
			}
		}
		grid.getRowConstraints().get(2).setMaxHeight(0);
	}
	
	private void hidePassword() {
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) != null) {
				if (GridPane.getRowIndex(child) == 2) {
					child.setVisible(false);
				}
			}
		}
		grid.getRowConstraints().get(2).setMaxHeight(0);
	}
	
	public Component getComponent() {
		return component;
	}
	
	@FXML
	private void componentBoxClicked(MouseEvent event) {
		componentLabelBox.requestFocus();
	}
	
	
	public void initBindings() {
		versionField.setText(component.version().get());
		userField.setText(component.user().get());
		passField.setText(component.pass().get());
		
		component.version().bindBidirectional(versionField.textProperty());
		component.user().bindBidirectional(userField.textProperty());
		component.pass().bindBidirectional(passField.textProperty());
		
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
