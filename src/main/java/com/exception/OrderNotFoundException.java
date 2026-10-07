package com.exception;

public class OrderNotFoundException  extends RuntimeException {

	public OrderNotFoundException(int orderId) {
		super("Order not found ( order id: "+ orderId+" )");
	}
		
}
