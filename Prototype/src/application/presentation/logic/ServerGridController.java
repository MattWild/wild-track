package application.presentation.logic;

import java.util.List;

import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class ServerGridController {
	
	@FXML
	private GridPane grid;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private Label ipLabel;
	
	@FXML
	private TextField ipField;
	
	@FXML
	private Label fqdnLabel;
	
	@FXML
	private TextField fqdnField;
	
	@FXML
	private Label typeLabel;
	
	@FXML
	private TextField typeField;
	
	@FXML
	private RadioButton hasDBButton;
	
	@FXML
	private Label dbTypeLabel;
	
	@FXML
	private TextField dbTypeField;
	
	@FXML
	private Label sysUserLabel;
	
	@FXML
	private TextField sysUserField;
	
	@FXML
	private Label sysPassLabel;
	
	@FXML 
	private TextField sysPassField;
	
	@FXML
	private Label portLabel;
	
	@FXML
	private TextField portField;
	
	@FXML
	private Label sidCatLabel;
	
	@FXML
	private ChoiceBox<String> sidCatChoiceBox;
	
	@FXML
	private Label sidLabel;
	
	@FXML
	private TextField sidField;
	
	@FXML
	private VBox componentsBox;
	
	@FXML
	private void initialize() {
		nameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) nameField.setVisible(false);
		});
		
		ipField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) ipField.setVisible(false);
		});
		
		fqdnField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) fqdnField.setVisible(false);
		});
		
		typeField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) typeField.setVisible(false);
		});
		
		hasDBButton.setSelected(true);
		hasDBButton.selectedProperty().addListener((arg, oldValue, newValue) -> {
			for (Node child : grid.getChildren()) {
				if (GridPane.getRowIndex(child) != null && GridPane.getRowIndex(child) >= 5 && GridPane.getRowIndex(child) <= 9)
					child.setVisible((newValue)? true : false);
			}
			
			for(int i = 5; i <= 9; i++) {
				if (newValue) {
					grid.getRowConstraints().get(i).setMaxHeight(Control.USE_COMPUTED_SIZE);
				} else {
					grid.getRowConstraints().get(i).setMaxHeight(0);
				}
			}
		});
		
		dbTypeField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) dbTypeField.setVisible(false);
		});
		
		sysUserField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sysUserField.setVisible(false);
		});
		
		sysPassField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sysPassField.setVisible(false);
		});
		
		portField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) portField.setVisible(false);
		});
		
		
		sidCatChoiceBox.getItems().addAll("Service Name", "SID");
		sidCatChoiceBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sidCatChoiceBox.setVisible(false);
		});
		
		sidField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sidField.setVisible(false);
		});
	}
	
	public void initBindings(SimpleStringProperty nameProp, 
			SimpleStringProperty ipProp,
			SimpleStringProperty fqdnProp, 
			SimpleStringProperty typeProp, 
			SimpleBooleanProperty hasDBProp, 
			SimpleStringProperty dbTypeProp, 
			SimpleStringProperty sysUserProp, 
			SimpleStringProperty sysPassProp, 
			SimpleIntegerProperty portProp, 
			SimpleBooleanProperty useSidProp,
			SimpleStringProperty sidProp) {
		
		nameField.setText(nameProp.get());
		ipField.setText(ipProp.get());
		fqdnField.setText(fqdnProp.get());
		typeField.setText(typeProp.get());
		hasDBButton.setSelected(hasDBProp.get());
		dbTypeField.setText(dbTypeProp.get());
		sysUserField.setText(sysUserProp.get());
		sysPassField.setText(sysPassProp.get());
		portField.setText(Integer.toString(portProp.get()));
		sidCatChoiceBox.getSelectionModel().select((useSidProp.get())? 1 : 0);
		sidField.setText(sidProp.get());
		
		nameProp.bindBidirectional(nameField.textProperty());
		ipProp.bindBidirectional(ipField.textProperty());
		fqdnProp.bindBidirectional(fqdnField.textProperty());
		typeProp.bindBidirectional(typeField.textProperty());
		hasDBProp.bindBidirectional(hasDBButton.selectedProperty());
		dbTypeProp.bindBidirectional(dbTypeField.textProperty());
		sysUserProp.bindBidirectional(sysUserField.textProperty());
		sysPassProp.bindBidirectional(sysPassField.textProperty());
		portField.textProperty().bindBidirectional(portProp, new NumberStringConverter());
		sidCatChoiceBox.getSelectionModel().selectedIndexProperty().addListener((arg, oldValue, newValue) -> {
			if (newValue.intValue() == 1) 
				useSidProp.set(true);
			else 
				useSidProp.set(false);
		});
		
		useSidProp.addListener((arg, oldValue, newValue) -> {
			if (newValue) {
				sidCatChoiceBox.getSelectionModel().select(1);
			} else {
				sidCatChoiceBox.getSelectionModel().select(0);
			}
		});
		sidProp.bindBidirectional(sidField.textProperty());
		
		
		nameLabel.textProperty().bind(nameField.textProperty());
		ipLabel.textProperty().bind(ipField.textProperty());
		fqdnLabel.textProperty().bind(fqdnField.textProperty());
		typeLabel.textProperty().bind(typeField.textProperty());
		dbTypeLabel.textProperty().bind(dbTypeField.textProperty());
		sysUserLabel.textProperty().bind(sysUserField.textProperty());
		sysPassLabel.textProperty().bind(sysPassField.textProperty());
		portLabel.textProperty().bind(portField.textProperty());
		sidCatLabel.textProperty().bind(sidCatChoiceBox.getSelectionModel().selectedItemProperty());
		sidLabel.textProperty().bind(sidField.textProperty());
	}
	
	public void addComponentGrid(GridPane componentGrid) {
		componentsBox.getChildren().add(componentGrid);
	}

	@FXML
	public void nameLabelClick(MouseEvent event) {
		nameField.setVisible(true);
		nameField.requestFocus();
	}
	
	@FXML
	public void ipLabelClick(MouseEvent event) {
		ipField.setVisible(true);
		ipField.requestFocus();
	}
	
	@FXML
	public void fqdnLabelClick(MouseEvent event) {
		fqdnField.setVisible(true);
		fqdnField.requestFocus();
	}
	
	@FXML
	public void typeLabelClick(MouseEvent event) {
		typeField.setVisible(true);
		typeField.requestFocus();
	}
	
	@FXML
	public void dbTypeLabelClick(MouseEvent event) {
		dbTypeField.setVisible(true);
		dbTypeField.requestFocus();
	}
	
	@FXML
	public void sysUserLabelClick(MouseEvent event) {
		sysUserField.setVisible(true);
		sysUserField.requestFocus();
	}
	
	@FXML
	public void sysPassLabelClick(MouseEvent event) {
		sysPassField.setVisible(true);
		sysPassField.requestFocus();
	}
	
	@FXML
	public void portLabelClick(MouseEvent event) {
		portField.setVisible(true);
		portField.requestFocus();
	}
	
	@FXML
	public void sidLabelClick(MouseEvent event) {
		sidField.setVisible(true);
		sidField.requestFocus();
	}
	
	@FXML
	public void sidCatPassLabelClick(MouseEvent event) {
		sidCatChoiceBox.setVisible(true);
		sidCatChoiceBox.requestFocus();
	}
	
	@FXML
	public void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
}
