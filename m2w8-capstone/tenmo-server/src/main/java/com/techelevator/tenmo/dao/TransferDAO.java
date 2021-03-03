package com.techelevator.tenmo.dao;

public interface TransferDAO {

	//public void sendFunds(long userId, double amount);
	
	public void addFundsToReceiverAccount(long receiverId, double amountSent);
	public void removeFundsFromSenderAccount(long senderId, double amountSent);
	
}
