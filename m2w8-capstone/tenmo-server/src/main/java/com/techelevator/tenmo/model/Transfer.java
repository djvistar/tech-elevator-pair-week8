package com.techelevator.tenmo.model;

public class Transfer {

	private int transferId;
	private int transferTypeId;
	private int transferStatusId;
	private double amount;
	private int accountFrom;
	private int accountTo;
	private String transferType;
	private String transferStatus;
	private String userFrom;
	private String userTo;
	
//	public Transfer() {
//		this.accountTo = accountTo;
//		this.amount = amount;
//	}
	
	
	public int getTransferId() {
		return transferId;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	public String getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(String userFrom) {
		this.userFrom = userFrom;
	}
	public String getUserTo() {
		return userTo;
	}
	public void setUserTo(String userTo) {
		this.userTo = userTo;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public int getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

}
