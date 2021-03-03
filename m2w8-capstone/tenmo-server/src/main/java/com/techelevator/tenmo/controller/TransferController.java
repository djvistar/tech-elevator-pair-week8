package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Account;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	private AccountDAO accountDao;
	private Account account;
	private TransferDAO transferDao;
	
	public TransferController() {
		this.accountDao = accountDao;
		this.transferDao = transferDao;
	}
	
	@RequestMapping( path = "/accounts", method = RequestMethod.GET )  
	public double returnBalance() {
		return accountDao.retrieveBalance(account.getAccountId());
	}
	
	@RequestMapping( path = "/accounts/{userId}", method = RequestMethod.PUT)
	public void transferFunds(@PathVariable int userId, double amountToTransfer, Principal principal) {
		transferDao.addFundsToReceiverAccount(userId, amountToTransfer);
		//transferDao.removeFundsFromSenderAccount(principal, amountToTransfer);
	}
	
}
