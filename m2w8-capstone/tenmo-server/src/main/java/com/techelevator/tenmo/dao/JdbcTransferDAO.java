package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

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

}
