package application.objects.entities;

import javafx.beans.property.SimpleStringProperty;

public class VersionAlias {
	
	private int id;
	private SimpleStringProperty raw;
	private SimpleStringProperty alias;
	
	public VersionAlias(int id, String raw, String alias) {
		this.id = id;
		this.raw = new SimpleStringProperty(raw);
		this.alias = new SimpleStringProperty(alias);
	}
	
	public VersionAlias() {
		this(-1, "RAW", "ALIAS");
	}
	
	public VersionAlias(String raw, String alias) {
		this(-1, raw, alias);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRaw() {
		return raw.get();
	}
	
	public void setRaw(String raw) {
		this.raw.set(raw);
	}
	
	public SimpleStringProperty raw() {
		return raw;
	}
	
	public String getAlias() {
		return alias.get();
	}
	
	public void setAlias(String alias) {
		this.alias.set(alias);
	}
	
	public SimpleStringProperty alias() {
		return alias;
	}

}
