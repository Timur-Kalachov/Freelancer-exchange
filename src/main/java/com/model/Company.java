package com.model;




public class Company {

	private int id;

	private String name;

	private String description;

	public Company() {
		name = "";
		description="";
	}

	public Company(int id,String name, String description) {
		this.id=id;
		this.name=name;
		this.description=description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nazva) {
		this.name = nazva;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}