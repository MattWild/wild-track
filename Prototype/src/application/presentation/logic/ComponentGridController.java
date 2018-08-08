package application.presentation.logic;

import application.Main;
import application.objects.environment.Component;
import application.objects.environment.Component.ComponentType;
import application.objects.settings.VersionAlias;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
	
	private Main main;
	private Component component;
	private ChangeListener<? super String> aliasListener = (arg, oldValue, newValue) -> {
		versionLabel.setText(newValue);
	};
	
	public void setMain(Main main) {
		this.main = main;
		
		initBindings();
	}
	
	public void setComponent(Component component) {
		this.component = component;
		
		grid.setMaxHeight(55);
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
		case AMS:
			componentLabel.setText("AMS");
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
	
	public Component getComponent() {
		return component;
	}
	
	private void initBindings() {
		versionField.setText(component.version().get());
		userField.setText(component.user().get());
		passField.setText(component.pass().get());
		
		component.version().bindBidirectional(versionField.textProperty());
		component.user().bindBidirectional(userField.textProperty());
		component.pass().bindBidirectional(passField.textProperty());
		
		if (component.getType() == ComponentType.COMMANDCENTER) {
			ObservableMap<String, VersionAlias> mapping = main.getObjectLayer().getAliasMapping();
			
			if (mapping.containsKey(component.getVersion())) {
				versionLabel.setText(mapping.get(component.getVersion()).getAlias());
				
				mapping.get(component.getVersion()).alias().addListener(aliasListener);
			} else 
				versionLabel.setText(component.getVersion());
			
			mapping.addListener((MapChangeListener.Change<? extends String, ? extends VersionAlias> change) -> {
				if (mapping.containsKey(component.getVersion())) {
					versionLabel.setText(mapping.get(component.getVersion()).getAlias());
					mapping.get(component.getVersion()).alias().addListener(aliasListener);
				} else {
					versionLabel.setText(component.getVersion());
					
					if (change.wasRemoved() && change.getKey().compareTo(component.getVersion()) == 0)
						change.getValueRemoved().alias().removeListener(aliasListener);
				}
			});
			
			component.version().addListener((arg, oldValue, newValue) -> {
				if (mapping.containsKey(oldValue)) {
					mapping.get(newValue).alias().removeListener(aliasListener);
				}
				
				if (mapping.containsKey(newValue)) {
					versionLabel.setText(mapping.get(newValue).getAlias());
					
					mapping.get(newValue).alias().addListener(aliasListener);
				} else 
					versionLabel.setText(newValue);
			});
		} else 
			versionLabel.textProperty().bind(versionField.textProperty());
		userLabel.textProperty().bind(userField.textProperty());
		passLabel.textProperty().bind(passField.textProperty());
	}

	private void hideVersion() {
		grid.getChildren().removeIf(child -> GridPane.getRowIndex(child) != null && GridPane.getRowIndex(child) == 1);
	}

	private void hideUsername() {
		grid.getChildren().removeIf(child -> GridPane.getRowIndex(child) != null && GridPane.getRowIndex(child) == 2);
	}

	private void hidePassword() {
		grid.getChildren().removeIf(child -> GridPane.getRowIndex(child) != null && GridPane.getRowIndex(child) == 3);
	}

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

	@FXML
	private void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}

	@FXML
	private void versionLabelClick(MouseEvent event) {
		if (!(component.getType() == ComponentType.COMMANDCENTER ||
				component.getType() == ComponentType.SBS ||
				component.getType() == ComponentType.ANSI ||
				component.getType() == ComponentType.ABNT ||
				component.getType() == ComponentType.CM || 
				component.getType() == ComponentType.CAPABILTYSERVICES ||
				component.getType() == ComponentType.M2M)) {
			versionField.setVisible(true);
			versionField.requestFocus();
		}
	}

	@FXML
	private void userLabelClick(MouseEvent event) {
		userField.setVisible(true);
		userField.requestFocus();
	}

	@FXML
	private void passLabelClick(MouseEvent event) {
		passField.setVisible(true);
		passField.requestFocus();
	}

	@FXML
	private void componentBoxClicked(MouseEvent event) {
		componentLabelBox.requestFocus();
		System.out.println(grid.getHeight() + " " + grid.getMaxHeight() + grid.getPrefHeight());
	}
}
