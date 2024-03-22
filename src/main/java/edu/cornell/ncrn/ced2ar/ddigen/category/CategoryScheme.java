package edu.cornell.ncrn.ced2ar.ddigen.category;

import java.util.List;
import java.util.UUID;

public class CategoryScheme {

	private List<Category> categoryList;
	private String id;
	private final String uuid;

	public CategoryScheme() {
		this.uuid = UUID.randomUUID().toString();
	}

	public CategoryScheme(String id, List<Category> categoryList) {
		this();
		setId(id);
		setCategoryList(categoryList);
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public String getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public void setId(String id) {
		this.id = id;
	}
}
