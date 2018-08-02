package application.objects.entities;

import application.presentation.logic.DeviceGridController.TableType;
import javafx.beans.property.SimpleStringProperty;

public interface Entry {
	
	public int getId();
	
	public TableType getType();
	
	public SimpleStringProperty getFieldProperty(int i);

	public String getFilterValue(int i);

	public void indicateChanged();
	
	public boolean isChanged();

	public boolean identifierNotNull();

	public void setId(int addDevices);
	
}
