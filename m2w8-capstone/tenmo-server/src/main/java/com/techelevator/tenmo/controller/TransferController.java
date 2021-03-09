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
	public double returnBalance(Principal principal) { // Returns the balance of the current user based on login information
		int id = userDao.findIdByUsername(principal.getName()); // Selects the id from the current user based on username
		return accountDao.retrieveBalance(id); // Passes this information to the JDBCAccountDAO to retrieve the balance for the logged in user
	}
	
	@RequestMapping( path = "allUsers", method = RequestMethod.GET)
	public List<User> listAllUsers(){ // Returns all users of the application
		List<User> allUsers = userDao.findAll();
		return allUsers;
	}
	
	@RequestMapping( path = "transfer", method = RequestMethod.POST )
	public void transferFunds(@RequestBody TransferRequest toAccount, Principal principal) {
		int senderId = userDao.findIdByUsername(principal.getName()); // Selects the logged in user's Id based on the username
		transferDao.sendTransfer(toAccount, senderId); // Passes in the current user and the userId they want to send funds to
	}
	
	@RequestMapping( path = "transfers", method = RequestMethod.GET )
	public List<Transfer> listAllTransfers() { // Lists all transfers for the logged in user
		return transferDao.listOfAllTransfers();
	}
	
	@RequestMapping( path = "transfers/{transferId}", method = RequestMethod.GET )
	public Transfer listTransferDetails(@PathVariable int transferId) { // Lists details for the transfer selected by the user
		return transferDao.listTransferDetails(transferId);
	}
	
	
}
