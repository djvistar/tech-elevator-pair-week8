package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Component
public class JdbcTransferDAO implements TransferDAO {

private JdbcTemplate jdbcTemplate;
	
	public JdbcTransferDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addFundsToReceiverAccount(long receiverId, double amountSent) {
		// TODO Auto-generated method stub
		
		String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?;";
		jdbcTemplate.update(sql, amountSent, receiverId);
		
	}

	@Override
	public void removeFundsFromSenderAccount(String username, double amountSent) {
		// TODO Auto-generated method stub

		String sqlSelect = "SELECT user_id FROM users WHERE username = ?;";
		SqlRowSet usernameResult = jdbcTemplate.queryForRowSet(sqlSelect, username);
		int senderId = usernameResult.getInt("user_id");
		
		String sqlUpdate = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?;";
		jdbcTemplate.update(sqlUpdate, amountSent, senderId);
	}

	@Override
	public List<Transfer> listOfAllTransfers() {
		// TODO Auto-generated method stub
		
		List<Transfer> allTransfers = new ArrayList<Transfer>();
		
		String sql = "SELECT * " + 
					 "FROM transfers " + 
					 "JOIN accounts ON accounts.account_from = transfers.account_from " +
					 "JOIN users ON users.user_id = accounts.user_id;";
					 
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while (results.next()) {
			Transfer singleTransfer = mapToTransfer(results);
			allTransfers.add(singleTransfer);
		}
		
		return allTransfers;
	}
	
	private Transfer mapToTransfer(SqlRowSet results) {
		
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferTypeId(results.getInt("transfer_type_id"));
		transfer.setTransferStatusId(results.getInt("transfer_status_id"));
		transfer.setAmount(results.getDouble("amount"));
		
		Account fromAccount = new Account();
		
		fromAccount.setAccountId(results.getInt("account_id"));
		fromAccount.setAccountBalance(results.getDouble("balance"));

		transfer.setAccountFrom(fromAccount.getAccountId());
		
		Account toAccount = new Account();
		
		toAccount.setAccountId(results.getInt("account_id"));
		toAccount.setAccountBalance(results.getDouble("balance"));
		
		transfer.setAccountTo(toAccount.getAccountId());
		
		return transfer;
	}
	
	

}
