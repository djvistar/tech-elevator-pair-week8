package com.techelevator.tenmo.model;

public class TransferRequest {

	private int destinationId;
	private double amount;
	
	public int getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
