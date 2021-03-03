package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public class JdbcTransferDAO implements TransferDAO {

private JdbcTemplate jdbcTemplate;
	
	public JdbcTransferDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addFundsToReceiverAccount(long receiverId, double amountSent) {
		// TODO Auto-generated method stub
		
		String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
		jdbcTemplate.update(sql, amountSent, receiverId);
		
	}

	@Override
	public void removeFundsFromSenderAccount(long senderId, double amountSent) {
		// TODO Auto-generated method stub

		
		String sql = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?";
		jdbcTemplate.update(sql, amountSent, senderId);
	}

	@Override
	public List<Transfer> listOfAllTransfers() {
		// TODO Auto-generated method stub
		
		List<Transfer> allTransfers = new ArrayList<Transfer>();
		
		String sql = "SELECT transfers.transfer_id, type.transfer_type_desc, users.username, transfers.amount " + 
					 "FROM transfers " +
					 "JOIN transfer_types type ON type.transfer_type_id = transfers.transfer_type_id " + 
					 "JOIN accounts ON account.account_from = transfers.account_from " +
					 "JOIN users ON users.user_id = accounts.user_id";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while (results.next()) {
			
		}
		
		return null;
	}
	
//	@Override
//	public void sendFunds(long userId, double amount) {
//		// TODO Auto-generated method stub
//
//		User receiver = new User();
//		
//		if (receiver.getId() == userId) {
//			
//			String sql = "";
//			
//		}
//		
//		
//		
//		
//	}
	
	private Transfer mapToTransfer(SqlRowSet results) {
		
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferTypeId(results.getInt("transfer_type_id"));
		
		
		return transfer;
	}

}
