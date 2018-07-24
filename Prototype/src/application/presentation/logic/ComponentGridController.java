package application.presentation.logic;

import javafx.beans.property.SimpleStringProperty;

public interface ComponentGridController {
	
	public void initBindings(SimpleStringProperty versionProp, SimpleStringProperty userProp, SimpleStringProperty passProp);
	
}
