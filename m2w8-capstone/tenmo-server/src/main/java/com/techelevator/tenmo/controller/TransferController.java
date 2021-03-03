package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.dao.JdbcTransferDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Account;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	private JdbcAccountDAO accountDao;
	private Account account;
	private JdbcTransferDAO transferDao;
	
	public TransferController(JdbcAccountDAO accountDao, JdbcTransferDAO transferDao) {
		this.accountDao = accountDao;
		this.transferDao = transferDao;
	}
	
	@RequestMapping( path = "accounts", method = RequestMethod.GET )  
	public double returnBalance(Principal principal) {
		return accountDao.retrieveBalance(principal.getName());
	}
	
	@RequestMapping( path = "accounts/{userId}", method = RequestMethod.POST )
	public void transferFunds(@RequestBody int userId, double amountToTransfer, Principal principal) {
		transferDao.addFundsToReceiverAccount(userId, amountToTransfer);
		transferDao.removeFundsFromSenderAccount(principal.getName(), amountToTransfer);
	}
	
}
