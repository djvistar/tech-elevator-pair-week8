package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	//public void sendFunds(long userId, double amount);
	
	void addFundsToReceiverAccount(long receiverId, double amountSent);
	void removeFundsFromSenderAccount(long senderId, double amountSent);
	
	List<Transfer> listOfAllTransfers();
	
}
