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
		
		String sql = "SELECT transfers.* " + 
					 "FROM transfers " + 
					 "JOIN accounts ON accounts.account_id = transfers.account_from " +
					 "JOIN users ON users.user_id = accounts.user_id;";
					 
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		
		while (results.next()) {
			Transfer singleTransfer = mapToTransfer(results);
			allTransfers.add(singleTransfer);
		}
		
		return allTransfers;
	}
	
	public Transfer listTransferDetails(int transferId) {
		
		
		String sql = "SELECT transfers.* " +
					 "FROM transfers " +
					 "JOIN accounts ON accounts.account_id = transfers.account_from " + 
					 "OR accounts.account_id = transfers.account_to " + 
					 "JOIN users ON users.user_id = accounts.user_id " + 
					 "WHERE transfer_id = ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
		
		result.next();
		Transfer singleTransfer = mapToTransfer(result);
		
		return singleTransfer;
		
	}
	
	private Transfer mapToTransfer(SqlRowSet results) {
		
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferTypeId(results.getInt("transfer_type_id"));
		transfer.setTransferStatusId(results.getInt("transfer_status_id"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setAmount(results.getDouble("amount"));
		
		return transfer;
	}
	
	

}
