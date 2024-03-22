package edu.cornell.ncrn.ced2ar.ddigen.category;

import java.util.UUID;

public class Category {

	private String id;
	private final String uuid;
	private String label;

	public Category() {
		uuid = UUID.randomUUID().toString();
	}

	public Category(String id) {
		this();
		setId(id);
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getUuid() {
		return uuid;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setId(String id) {
		this.id = id;
	}
}