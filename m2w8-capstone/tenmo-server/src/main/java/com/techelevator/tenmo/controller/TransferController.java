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
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	private AccountDAO accountDao;
	private Account account;
	private TransferDAO transferDao;
	private UserDAO userDao;
	
	public TransferController(AccountDAO accountDao, TransferDAO transferDao, UserDAO userDao) {
		this.accountDao = accountDao;
		this.transferDao = transferDao;
		this.userDao = userDao;
	}
	
	@RequestMapping( path = "accounts", method = RequestMethod.GET )  
	public double returnBalance(Principal principal) {
		int id = userDao.findIdByUsername(principal.getName());
		return accountDao.retrieveBalance(id);
	}
	
	@RequestMapping( path = "allUsers", method = RequestMethod.GET)
	public List<User> listAllUsers(){
		List<User> allUsers = userDao.findAll();
		return allUsers;
	}
	
	@RequestMapping( path = "transfer", method = RequestMethod.POST )
	public void transferFunds(@RequestBody TransferRequest toAccount, Principal principal) {
		//transferDao.addFundsToReceiverAccount(receiverId, amountToTransfer);
		//transferDao.removeFundsFromSenderAccount(principal.getName(), amountToTransfer);
		int senderId = userDao.findIdByUsername(principal.getName());
		transferDao.sendTransfer(toAccount, senderId);
	}
	
	@RequestMapping( path = "transfers", method = RequestMethod.GET )
	public List<Transfer> listAllTransfers() {
		return transferDao.listOfAllTransfers();
	}
	
	@RequestMapping( path = "transfers/{transferId}", method = RequestMethod.GET )
	public Transfer listTransferDetails(@PathVariable int transferId) {
		return transferDao.listTransferDetails(transferId);
	}
	
	
}
