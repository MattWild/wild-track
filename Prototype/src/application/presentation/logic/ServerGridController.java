package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.environment.Component;
import application.objects.environment.Server;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.VBox;
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
	private RadioButton isSQLButton;
	
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
	
	private Main main;
	private Server server;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void initBindings() {
		nameField.setText(server.name().get());
		ipField.setText(server.ip().get());
		fqdnField.setText(server.fqdn().get());
		typeField.setText(server.type().get());
		hasDBButton.setSelected(server.hasDB().get());
		dbTypeField.setText(server.dbType().get());
		isSQLButton.setSelected(server.isSQL().get());
		sysUserField.setText(server.sysUser().get());
		sysPassField.setText(server.sysPass().get());
		portField.setText(Integer.toString(server.port().get()));
		sidCatChoiceBox.getSelectionModel().select((server.usesSid().get())? 0 : 1);
		sidField.setText(server.sid().get());
		
		server.name().bindBidirectional(nameField.textProperty());
		server.ip().bindBidirectional(ipField.textProperty());
		server.fqdn().bindBidirectional(fqdnField.textProperty());
		server.type().bindBidirectional(typeField.textProperty());
		server.hasDB().bindBidirectional(hasDBButton.selectedProperty());
		server.dbType().bindBidirectional(dbTypeField.textProperty());
		server.isSQL().bindBidirectional(isSQLButton.selectedProperty());
		server.sysUser().bindBidirectional(sysUserField.textProperty());
		server.sysPass().bindBidirectional(sysPassField.textProperty());
		portField.textProperty().bindBidirectional(server.port(), new NumberStringConverter());
		sidCatChoiceBox.getSelectionModel().selectedIndexProperty().addListener((arg, oldValue, newValue) -> {
			if (newValue.intValue() == 1) 
				server.indicateServiceName();
			else 
				server.indicateSID();
			
			System.out.println(newValue.intValue() + " " + server.getUsesSid());
		});
		
		server.usesSid().addListener((arg, oldValue, newValue) -> {
			if (newValue && sidCatChoiceBox.getSelectionModel().getSelectedIndex() != 0) {
				sidCatChoiceBox.getSelectionModel().select(1);
			} else if (sidCatChoiceBox.getSelectionModel().getSelectedIndex() != 1) {
				sidCatChoiceBox.getSelectionModel().select(0);
			}
		});
		server.sid().bindBidirectional(sidField.textProperty());
		
		
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

	public void setUpGrid() {
		for (Component component : server.getComponents()) {
			buildComponentGrid(component);
		}
		
		server.getComponents().addListener((Change<? extends Component> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Component component : change.getAddedSubList()) {
						buildComponentGrid(component);
					}
				} else if (change.wasRemoved()) {
					for (Component component : change.getRemoved()) {
						for (Node node : componentsBox.getChildren()) {
							if (node.getUserData() == component) {
								componentsBox.getChildren().remove(node);
								break;
							}
						}
					}
				}
			}
		});
	}
	
	private void buildComponentGrid(Component component) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/ComponentGrid.fxml"));
			GridPane componentGrid = (GridPane) loader.load();
			ComponentGridController controller = loader.getController();
			
			controller.setComponent(component);
			controller.setMain(main);
			componentGrid.setUserData(component);
			componentsBox.getChildren().add(componentGrid);
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	private void processShowAdd(boolean hasDbValue, boolean isSQLValue) {
		isSQLButton.setVisible(hasDbValue);
		
		for (Node child : grid.getChildren()) {
			if (GridPane.getRowIndex(child) != null) {
				if (GridPane.getRowIndex(child) == 5) {
					child.setVisible(hasDbValue);
				} else if (GridPane.getRowIndex(child) >= 6 && GridPane.getRowIndex(child) <= 9) {
					child.setVisible(hasDbValue && !isSQLValue);
				}
			}
		}
		
		if (hasDbValue) {
			grid.getRowConstraints().get(5).setMaxHeight(Control.USE_COMPUTED_SIZE);
		} else {
			grid.getRowConstraints().get(5).setMaxHeight(0);
		}
		
		for(int i = 6; i <= 9; i++) {
			if (hasDbValue && !isSQLValue) {
				grid.getRowConstraints().get(i).setMaxHeight(Control.USE_COMPUTED_SIZE);
			} else {
				grid.getRowConstraints().get(i).setMaxHeight(0);
			}
		}
	}

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
			processShowAdd(newValue, isSQLButton.isSelected());
		});
		
		isSQLButton.setSelected(false);
		isSQLButton.selectedProperty().addListener((arg, oldValue, newValue) -> {
			processShowAdd(hasDBButton.isSelected(), newValue);
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
		
		
		sidCatChoiceBox.getItems().addAll("SID", "Service Name");
		sidCatChoiceBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sidCatChoiceBox.setVisible(false);
		});
		
		sidField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (newValue == false) sidField.setVisible(false);
		});
	}

	@FXML
	private void nameLabelClick(MouseEvent event) {
		if (event.getClickCount() == 1) {
			Node node = ((Node) event.getSource());
			if (node.getUserData() == null) node.setUserData(server);
			node.requestFocus();
		} else {
			nameField.setVisible(true);
			nameField.requestFocus();
		}
	}
	
	@FXML
	private void ipLabelClick(MouseEvent event) {
		ipField.setVisible(true);
		ipField.requestFocus();
	}
	
	@FXML
	private void fqdnLabelClick(MouseEvent event) {
		fqdnField.setVisible(true);
		fqdnField.requestFocus();
	}
	
	@FXML
	private void typeLabelClick(MouseEvent event) {
		typeField.setVisible(true);
		typeField.requestFocus();
	}
	
	@FXML
	private void dbTypeLabelClick(MouseEvent event) {
		dbTypeField.setVisible(true);
		dbTypeField.requestFocus();
	}
	
	@FXML
	private void sysUserLabelClick(MouseEvent event) {
		sysUserField.setVisible(true);
		sysUserField.requestFocus();
	}
	
	@FXML
	private void sysPassLabelClick(MouseEvent event) {
		sysPassField.setVisible(true);
		sysPassField.requestFocus();
	}
	
	@FXML
	private void portLabelClick(MouseEvent event) {
		portField.setVisible(true);
		portField.requestFocus();
	}
	
	@FXML
	private void sidLabelClick(MouseEvent event) {
		sidField.setVisible(true);
		sidField.requestFocus();
	}
	
	@FXML
	private void sidCatPassLabelClick(MouseEvent event) {
		sidCatChoiceBox.setVisible(true);
		sidCatChoiceBox.requestFocus();
	}
	
	@FXML
	private void fieldEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			((Node) event.getSource()).setVisible(false);
		}
	}
}
