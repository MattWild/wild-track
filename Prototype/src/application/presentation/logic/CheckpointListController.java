package application.presentation.logic;

import java.io.IOException;

import application.Main;
import application.objects.environment.Checkpoint;
import application.objects.environment.Component;
import application.objects.environment.Environment;
import application.objects.environment.Server;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CheckpointListController {
	
	@FXML
	private GridPane grid;
	
	@FXML 
	private VBox environmentLabelBox;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label versionLabel;
	
	@FXML
	private Label isSQLLabel;
	
	private Main main;
	private Environment environment;
	private final ChangeListener<? super Boolean> sqlListener = (arg, oldValue, newValue) -> {
		if (newValue)
			isSQLLabel.setText("SQL");
		else
			isSQLLabel.setText("ORACLE");
	};
	private ChangeListener<? super Boolean> hasDbListener;
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
		
		initBindings();
	}

	public void setupTable() {
		for (Checkpoint checkpoint : environment.getCheckpoints()) {
			buildCheckpointBox(checkpoint);
		}
		
		environment.getCheckpoints().addListener((Change<? extends Checkpoint> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Checkpoint checkpoint : change.getAddedSubList()) {
						buildCheckpointBox(checkpoint);
					}
				} else if (change.wasRemoved()) {
					for (Checkpoint checkpoint : change.getRemoved()) {
						grid.getChildren().removeIf(child -> ((Node) child).getUserData() == checkpoint);
					}
				}
			}
		});
	}

	private void initBindings() {
		nameLabel.textProperty().bind(environment.name());
		
		if (environment.getCommandCenter() != null) {
			versionLabel.textProperty().bind(environment.getCommandCenter().version());
		}
		
		environment.commandCenter().addListener((arg, oldValue, newValue) -> {
			versionLabel.textProperty().unbind();
			if (newValue != null) {
				versionLabel.textProperty().bind(newValue.version());
			} else {
				versionLabel.setText("");
			}
		});
		
		if (environment.getCentralServices() != null) {
			Component centralServices = environment.getCentralServices();
			
			if (centralServices.getParent().getHasDB()) {
				if (centralServices.getParent().getIsSQL())
					isSQLLabel.setText("SQL");
				else
					isSQLLabel.setText("ORACLE");
				
				centralServices.getParent().isSQL().addListener(sqlListener);
			} else {
				isSQLLabel.setText("--");
			}
			
			hasDbListener = hasDBListener(centralServices.getParent());
			centralServices.getParent().hasDB().addListener(hasDbListener);
		} else {
			isSQLLabel.setText("--");
		}
		
		environment.centralServices().addListener((arg, oldValue, newValue) -> {
			if (oldValue != null) {
				oldValue.getParent().hasDB().removeListener(hasDbListener);
			}
			
			if (newValue != null) {
				Component centralServices = environment.getCentralServices();
				
				if (centralServices.getParent().getHasDB()) {
					if (centralServices.getParent().getIsSQL())
						isSQLLabel.setText("SQL");
					else
						isSQLLabel.setText("ORACLE");
					
					centralServices.getParent().isSQL().addListener(sqlListener);
				} else {
					isSQLLabel.setText("--");
				}
				
				hasDbListener = hasDBListener(centralServices.getParent());
				centralServices.getParent().hasDB().addListener(hasDbListener);
			} else {
				isSQLLabel.setText("--");
			}
		});
		environmentLabelBox.setUserData(environment);
	}
	
	private void buildCheckpointBox(Checkpoint checkpoint) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/CheckpointBox.fxml"));
			StackPane box = (StackPane) loader.load();
			box.setUserData(checkpoint);
			CheckpointBoxController controller = loader.getController();
			
			controller.setCheckpoint(checkpoint);
			
			grid.add(box, 0, grid.getChildren().size());
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}

	private ChangeListener<? super Boolean> hasDBListener(Server server) {
		return (arg, oldValue, newValue) -> {
			if (newValue) {
				if (server.getIsSQL())
					isSQLLabel.setText("SQL");
				else
					isSQLLabel.setText("ORACLE");
				
				server.isSQL().addListener(sqlListener);
			} else {
				isSQLLabel.setText("--");
				
				server.isSQL().removeListener(sqlListener);
			}
		};
	}

	@FXML
	private void environmentLabelClicked(MouseEvent event) {
		((Node) event.getSource()).requestFocus();
	}

}
