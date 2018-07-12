package application.presentation.logic;

import java.util.ArrayList;
import java.util.List;

import application.entities.Entry;
import application.entities.Socket;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;

public class SocketsGridController extends TableController {
	@FXML
	private GridPane socketsGrid;
	
	@FXML
	private ChoiceBox<String> idFilter;
	
	@FXML
	private ChoiceBox<String> formFilter;
	
	@FXML
	private ChoiceBox<String> nloadFilter;
	
	@FXML
	private ChoiceBox<String> locationFilter;
	
	
	public SocketsGridController() {
		super(TableController.TableType.Sockets);
	}
	

	@Override
	protected void initialize() {
		this.grid = socketsGrid;

		this.filters.add(idFilter);
		this.filters.add(formFilter);
		this.filters.add(nloadFilter);
		this.filters.add(locationFilter);
	}

	protected List<String> getFilters() {
		List<String> result = new ArrayList<String>();
		
		result.add(idFilter.getSelectionModel().getSelectedItem());
		result.add(formFilter.getSelectionModel().getSelectedItem());
		result.add(nloadFilter.getSelectionModel().getSelectedItem());
		result.add(locationFilter.getSelectionModel().getSelectedItem());
		
		return result;
	}


	@Override
	protected Entry generateEntry(List<String> fields) {
		return new Socket(main, fields.get(0), fields.get(1), fields.get(2), fields.get(3));
	}
}
