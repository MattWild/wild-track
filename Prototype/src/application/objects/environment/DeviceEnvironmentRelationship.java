package application.objects.environment;

import java.sql.Timestamp;

import application.objects.hardware.Device;
import javafx.collections.ListChangeListener;

public class DeviceEnvironmentRelationship {
	private Environment environment;
	private Device device;
	private Timestamp latestReadDate;
	
	private ListChangeListener<Environment> environmentRelationshipListener = (ListChangeListener.Change<? extends Environment> change) -> {
		while (change.next())
			if (change.wasAdded()) {
				for (Environment environment : change.getAddedSubList()) {
					if (environment.getId() == getEnvironment().getId()) {
						environment.addDeviceRelationship(this);
						setEnvironment(environment);
					}
				}
			} else if (change.wasRemoved()) {
				for (Environment environment : change.getRemoved())
					if (environment == getEnvironment()) {
						environment.removeDeviceRelationship(this);
						device.removeEnvironmentRelationship(this);
					}
			}
	};
	
	private ListChangeListener<Device> deviceRelationshipListener = (ListChangeListener.Change<? extends Device> change) -> {
		while (change.next())
			if (change.wasAdded()) {
				for (Device addDevice : change.getAddedSubList()) {
					if (addDevice.getId() == getDevice().getId()) {
						addDevice.addEnvironmentRelationship(this);
						setDevice(addDevice);
					}
				}
			} else if (change.wasRemoved()) {
				for (Device removeDevice : change.getRemoved())
					if (removeDevice == getDevice()) {
						removeDevice.removeEnvironmentRelationship(this);
						environment.removeDeviceRelationship(this);
					}
			}
	};
	
	public DeviceEnvironmentRelationship(Environment environment, Device device, Timestamp latestReadDate) {
		this.environment = environment;
		this.device = device;
		this.latestReadDate = latestReadDate;
	}

	public Environment getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public void setDevice(Device device) {
		this.device = device;
	}
	
	public Timestamp getLatestReadDate() {
		return latestReadDate;
	}
	
	public ListChangeListener<Environment> getEnvironmentRelationshipListener() {
		return environmentRelationshipListener;
	}
	
	public ListChangeListener<Device> getDeviceRelationshipListener() {
		return deviceRelationshipListener;
	}
}
