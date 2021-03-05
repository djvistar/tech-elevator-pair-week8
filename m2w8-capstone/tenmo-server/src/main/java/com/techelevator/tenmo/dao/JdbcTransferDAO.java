package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;
import com.techelevator.tenmo.model.User;

@Component
public class JdbcTransferDAO implements TransferDAO {

	@Autowired
	private AccountDAO accountDAO;

	private JdbcTemplate jdbcTemplate;

	public JdbcTransferDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

//	@Override
//	public void addFundsToReceiverAccount(long receiverId, double amountSent) {
//		// TODO Auto-generated method stub
//		
//		String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?;";
//		jdbcTemplate.update(sql, amountSent, receiverId);
//		
//	}
//
//	@Override
//	public void removeFundsFromSenderAccount(String username, double amountSent) {
//		// TODO Auto-generated method stub
//
//		String sqlSelect = "SELECT user_id FROM users WHERE username = ?;";
//		SqlRowSet usernameResult = jdbcTemplate.queryForRowSet(sqlSelect, username);
//		int senderId = usernameResult.getInt("user_id");
//		
//		String sqlUpdate = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?;";
//		jdbcTemplate.update(sqlUpdate, amountSent, senderId);
//	}

	@Override
	public List<Transfer> listOfAllTransfers() {
		// TODO Auto-generated method stub

		List<Transfer> allTransfers = new ArrayList<Transfer>();

//		String sql = "SELECT transfers.* " + 
//					 "FROM transfers " + 
//					 "JOIN accounts ON accounts.account_id = transfers.account_from " +
//					 "JOIN users ON users.user_id = accounts.user_id;";
		String sql = "SELECT t.*, u.username AS userFrom, v.username AS userTo " + "FROM transfers t "
				+ "JOIN accounts a ON t.account_from = a.account_id "
				+ "JOIN accounts b ON t.account_to = b.account_id " + "JOIN users u ON a.user_id = u.user_id "
				+ "JOIN users v ON b.user_id = v.user_id ";
//				"WHERE a.user_id = ? OR b.user_id = ? ";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

		while (results.next()) {
			Transfer singleTransfer = mapToTransfer(results);
			allTransfers.add(singleTransfer);
		}

		return allTransfers;
	}

	public Transfer listTransferDetails(int transferId) {

//		
//		String sql = "SELECT transfers.* " +
//					 "FROM transfers " +
//					 "JOIN accounts ON accounts.account_id = transfers.account_from " + 
//					 "OR accounts.account_id = transfers.account_to " + 
//					 "JOIN users ON users.user_id = accounts.user_id " + 
//					 "WHERE transfer_id = ?;";
		String sql = "SELECT t.*, u.username AS userFrom, v.username AS userTo, ts.transfer_status_desc, tt.transfer_type_desc FROM transfers t "
				+ "JOIN accounts a ON t.account_from = a.account_id "
				+ "JOIN accounts b ON t.account_to = b.account_id " + "JOIN users u ON a.user_id = u.user_id "
				+ "JOIN users v ON b.user_id = v.user_id "
				+ "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id "
				+ "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id " + "WHERE t.transfer_id = ?";

		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

		result.next();
		Transfer singleTransfer = mapToTransfer(result);

		return singleTransfer;

	}

	@Override
	public String sendTransfer(TransferRequest transferRequest, int senderId) {
		// TODO Auto-generated method stub
		try {
			String sqlSelect = "SELECT accounts.account_id, accounts.balance FROM accounts "
					+ "JOIN users ON accounts.user_id = users.user_id " +
					// "JOIN transfers ON transfers.account_from = accounts.account_id"
					"WHERE users.user_id = ?;";
			SqlRowSet usernameResult = jdbcTemplate.queryForRowSet(sqlSelect, senderId);

			usernameResult.next();
			TransferRequest fromAccount = mapToRequest(usernameResult);

			if (fromAccount.getDestinationId() == transferRequest.getDestinationId()) {
				return "You can not send money to your self.";
			} // getAmount() is pulling the balance due to the SQL statement above
			else if (fromAccount.getAmount() < transferRequest.getAmount() || fromAccount.getAmount() < 0) {
				return "Insufficient Funds";
			} else if (transferRequest.getAmount() < 0) {
				return "Cannot send negative funds";
			} else {
				String sql = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) "
						+ "VALUES (2,2,?,?,?) ";
				jdbcTemplate.update(sql, fromAccount.getDestinationId(), transferRequest.getDestinationId(),
						transferRequest.getAmount());

				// Call the accountDao and get the current balance for the user and add the transfer request and use that amount to update the balance
				// Don't do math in SQL, Spring doesn't like that
				
				double updatedSenderBalance = fromAccount.getAmount() - transferRequest.getAmount();
				
				double updatedReceiverBalance = accountDAO.retrieveBalance(transferRequest.getDestinationId()) + transferRequest.getAmount();
				
				String sqlToAccount = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
				jdbcTemplate.update(sqlToAccount, updatedReceiverBalance, transferRequest.getDestinationId());

				String sqlFromAccount = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
				jdbcTemplate.update(sqlFromAccount, updatedSenderBalance, fromAccount.getDestinationId());

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "Transfer Complete";
	}

	private Transfer mapToTransfer(SqlRowSet results) {

		Transfer transfer = new Transfer();

		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferTypeId(results.getInt("transfer_type_id"));
		transfer.setTransferStatusId(results.getInt("transfer_status_id"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setAmount(results.getDouble("amount"));

		try {
			transfer.setUserFrom(results.getString("userFrom"));
			transfer.setUserTo(results.getString("userTo"));
		} catch (Exception e) {
		}
		try {
			transfer.setTransferType(results.getString("transfer_type_desc"));
			transfer.setTransferStatus(results.getString("transfer_status_desc"));
		} catch (Exception e) {
		}

		return transfer;
	}

	private TransferRequest mapToRequest(SqlRowSet results) {
		TransferRequest request = new TransferRequest();

		request.setDestinationId(results.getInt("account_id"));
		request.setAmount(results.getDouble("balance"));

		return request;
	}

}
