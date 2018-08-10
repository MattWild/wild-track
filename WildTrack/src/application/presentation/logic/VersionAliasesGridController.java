package application.presentation.logic;

import java.sql.SQLException;

import application.Main;
import application.objects.settings.VersionAlias;
import application.presentation.PresentationLayer.EditingCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VersionAliasesGridController {
	
	@FXML
	private TableView<VersionAlias> table;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button removeButton;
	
	@FXML
	private Button saveButton;
	
	private Main main;
	private ObservableList<VersionAlias> versionAliases;

	public void setMain(Main main) {
		this.main = main;
	}

	@SuppressWarnings("unchecked")
	public void populateTable() {
		addButton.setOnAction(event -> {
			main.getObjectLayer().addVersionAlias();
		});
		
		removeButton.setOnAction(event -> {
			if (!table.getSelectionModel().isEmpty())
				main.getObjectLayer().deleteVersionAlias(table.getSelectionModel().getSelectedItem());
		});
		
		saveButton.setOnAction(event -> {
			try {
				main.getObjectLayer().saveVersionAliases();
			} catch (SQLException e) {
				main.errorHandle(e);
			}
		});
		versionAliases = main.getObjectLayer().getVersionAliases();
		
		table.setEditable(true);
		((TableColumn<VersionAlias, String>) table.getColumns().get(0)).setCellValueFactory(cellData -> cellData.getValue().raw());
		((TableColumn<VersionAlias, String>) table.getColumns().get(0)).setCellFactory((TableColumn<VersionAlias, String> param) -> new EditingCell<VersionAlias>());

		((TableColumn<VersionAlias, String>) table.getColumns().get(1)).setCellValueFactory(cellData -> cellData.getValue().alias());		
		((TableColumn<VersionAlias, String>) table.getColumns().get(1)).setCellFactory((TableColumn<VersionAlias, String> param) -> new EditingCell<VersionAlias>());

		
		table.setItems(versionAliases);
	}

}
