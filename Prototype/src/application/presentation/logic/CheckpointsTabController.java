package application.presentation.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import application.Main;
import application.objects.entities.Checkpoint;
import application.objects.entities.Environment;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class CheckpointsTabController {
	
	@FXML
	private GridPane grid;
	
	@FXML
	private TextField versionSearchField;
	
	private Main main;
	private CheckpointListBox curr;

	public void setMain(Main main) {
		this.main = main;
	}


	public void populateTable() {
		for (Environment environment : main.getObjectLayer().getEnvironments()) {
			buildCheckpointList(environment);
		}
		
		main.getObjectLayer().getEnvironments().addListener((Change<? extends Environment> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Environment environment : change.getAddedSubList()) {
						buildCheckpointList(environment);
					}
				} else if (change.wasRemoved()) {
					CheckpointListBox temp = (CheckpointListBox) grid.getChildren().get(0);
					
					while (temp != null) {
						for (int i = 0; i < temp.getChildren().size(); i++) {
							Node node = temp.getChildren().get(i);
							if (change.getRemoved().contains(node.getUserData())) {
								temp.remove(i);
							}
						}
						
						temp = temp.getAfter();
					}
				}
			}
		});
		
		versionSearchField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER)
				search();
		});
	}
	
	private void search() {
		CheckpointListBox temp = (CheckpointListBox) grid.getChildren().get(0);
		
		while (temp != null) {
			for (Node node : temp.getChildren()) {
				if (versionSearchField.getText() == null || versionSearchField.getText().length() == 0)
					node.setVisible(true);
				else {
					Environment environment = (Environment) node.getUserData();
					boolean visible = false;
					
					for (Checkpoint checkpoint : environment.getCheckpoints()) {
						if (checkpoint.getVersion().contains(versionSearchField.getText()))
							visible = true;
					}
					
					node.setVisible(visible);
				}
			}
			
			temp = temp.getAfter();
		}
	}


	private void buildCheckpointList(Environment environment) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("presentation/view/CheckpointList.fxml"));
			GridPane listTable = (GridPane) loader.load();
			listTable.setUserData(environment);
			CheckpointListController controller = loader.getController();
			
			controller.setMain(main);
			controller.setEnvironment(environment);
			controller.setUpTable();
			
			if (curr == null) {
				curr = new CheckpointListBox(null);
				grid.add(curr, 0, 0);
			}
			curr.getChildren().add(listTable);
			
			if (curr.isFull()) {
				CheckpointListBox temp = new CheckpointListBox(curr);
				curr.setAfter(temp);
				curr = temp;
				grid.add(curr, 0, grid.getRowConstraints().size());
				grid.getRowConstraints().add(new RowConstraints());
			}
		} catch (IOException e) {
			main.errorHandle(e);
		}
	}
	
	private class CheckpointListBox extends HBox {
		
		private CheckpointListBox beforeBox;
		private CheckpointListBox afterBox;
		
		public CheckpointListBox(CheckpointListBox before) {
			beforeBox = before;
		}
		
		public boolean isFull() {
			return this.getChildren().size() > 4;
		}
		
		public CheckpointListBox getBefore() {
			return beforeBox;
		}
		
		public void setAfter(CheckpointListBox after) {
			afterBox = after;
		}
		
		public CheckpointListBox getAfter() {
			return afterBox;
		}
		
		public Node remove(int index) {
			if (this.getChildren().size() <= index)
				return null;
			else {
				Node node = this.getChildren().remove(index);
				
				if (this.getAfter() != null) {
					this.getChildren().add(this.getAfter().remove(0));
					if (this.getAfter().getChildren().size() == 0)
						this.setAfter(null);
				}
				
				return node;
			}
		}
	}

}
