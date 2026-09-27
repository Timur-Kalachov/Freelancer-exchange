package com.model;

public class Application {

	private int id;

	private int orderId;
	
	private int freelancerId;
	
	private String status;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getFreelancerId() {
		return freelancerId;
	}

	public void setFreelancerId(int freelancerId) {
		this.freelancerId = freelancerId;
	}



}