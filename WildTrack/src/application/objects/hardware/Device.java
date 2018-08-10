package application.objects.hardware;

import application.objects.environment.DeviceEnvironmentRelationship;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.transformation.SortedList;

public abstract class Device {
	
	public enum DeviceType {
		METERS,
		COLLECTORS,
		ROUTERS,
		HAN_DEVICES,
		SOCKETS
	}
	
	int id;
	boolean isChanged = false;
	protected ObservableList<DeviceEnvironmentRelationship> environmentRelationships;
	protected SortedList<DeviceEnvironmentRelationship> sortedRelationships;
	protected SimpleStringProperty crcProp;
	protected SimpleStringProperty otherCRCsProp;
	
	public Device(int id) {
		this.id = id;
		environmentRelationships = FXCollections.observableArrayList();
		sortedRelationships = new SortedList<DeviceEnvironmentRelationship>(environmentRelationships, (er1, er2) -> {	
			if (er1 == null || er1.getLatestReadDate() == null) 
				if (er2  == null || er2.getLatestReadDate() == null)
					return 0;
				else
					return 1;
			else
				if (er2 == null || er2.getLatestReadDate() == null)
					return -1;
				else {
					int value = er1.getLatestReadDate().compareTo(er2.getLatestReadDate());
			
					if (value == 0)
						return 0;
					else if (value < 0)
						return 1;
					else
						return -1;
				}
		});
		crcProp = generateCRCProp();
		otherCRCsProp = generateOtherCRCsProp();
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isChanged() {
		return isChanged;
	}

	public void indicateChanged() {
		isChanged = true;
	}

	public void indicateSaved() {
		isChanged = false;
	}

	public void addEnvironmentRelationship(DeviceEnvironmentRelationship environmentRelationship) {
		environmentRelationships.add(environmentRelationship);
	}

	public void removeEnvironmentRelationship(DeviceEnvironmentRelationship environmentRelationship) {
		environmentRelationships.remove(environmentRelationship);
	}

	public ObservableList<DeviceEnvironmentRelationship> getEnvironmentRelationships() {
		return environmentRelationships;
	}

	public abstract DeviceType getType();

	public abstract String getIdentifier();

	public abstract boolean identifierNotNull();

	public abstract SimpleStringProperty getFieldProperty(int i);

	public abstract SimpleStringProperty getFilterProperty(int i);

	private SimpleStringProperty generateCRCProp() {
		SimpleStringProperty crcProp = new SimpleStringProperty();
		
		if (sortedRelationships.size() != 0 && sortedRelationships.get(0) != null)
			crcProp.set(sortedRelationships.get(0).getEnvironment().getCRC() + "");
		
		sortedRelationships.addListener((Change<? extends DeviceEnvironmentRelationship> change) -> {
			if (sortedRelationships.size() != 0 && sortedRelationships.get(0) != null)
				crcProp.set(sortedRelationships.get(0).getEnvironment().getCRC() + "");
			else
				crcProp.set(null);
		});
		
		return crcProp;
	}
	
	private SimpleStringProperty generateOtherCRCsProp() {
		SimpleStringProperty otherCRCsProp = new SimpleStringProperty();
		
		calculateOtherCRCsText(otherCRCsProp);
		
		ChangeListener<Number> listener = (arg, oldValue, newValue) -> {
			calculateOtherCRCsText(otherCRCsProp);
		};
		
		for (DeviceEnvironmentRelationship environmentRelationship : sortedRelationships) {
			environmentRelationship.getEnvironment().crc().addListener(listener);
		}
		
		sortedRelationships.addListener((Change<? extends DeviceEnvironmentRelationship> change) -> {
			calculateOtherCRCsText(otherCRCsProp);
			
			while (change.next()) {
				if (change.wasAdded()) {
					for (DeviceEnvironmentRelationship environmentRelationship : change.getAddedSubList()) {
						environmentRelationship.getEnvironment().crc().addListener(listener);
					}
				} else if(change.wasRemoved()) {
					for (DeviceEnvironmentRelationship environmentRelationship : change.getRemoved()) {
						environmentRelationship.getEnvironment().crc().removeListener(listener);
					}
				}
			}
		});
		
		return otherCRCsProp;
	}
	
	private void calculateOtherCRCsText(SimpleStringProperty prop) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < sortedRelationships.size(); i++) {
			sb.append(sortedRelationships.get(i).getEnvironment().getCRC() + ", ");
		}
		prop.set(sb.toString());
	}
}
