package application.object;

import java.util.List;

import application.entities.Entry;
import application.entities.Meter;
import application.presentation.logic.TableController.TableType;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class ObjectLayer {
	
	FilteredList<Entry> meters;
	

	public FilteredList<Entry> getEntries(TableType type) {
		return meters;
	}

	public void setDataFilter(TableType type, List<String> filterValues) {
		meters.setPredicate(entry -> {
			meters.getPredicate().
			if(newValue == null || newValue.compareTo("All") == 0) return true;
			
			if()
		});
	}

}
