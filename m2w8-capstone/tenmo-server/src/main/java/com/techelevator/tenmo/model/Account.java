package com.techelevator.tenmo.model;

public class Account {

	private int accountId;
	private double accountBalance;
	private int userId;
	
//	public Account() {
//		this.accountId = accountId;
//		this.accountBalance = accountBalance;
//	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
