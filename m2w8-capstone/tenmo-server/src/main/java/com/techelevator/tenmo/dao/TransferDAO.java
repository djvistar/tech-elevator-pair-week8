package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;

public interface TransferDAO {

	 List<Transfer> listOfAllTransfers();
	 Transfer listTransferDetails(int transferId);
	 public String sendTransfer(TransferRequest transferRequest, int senderId);
	
}
