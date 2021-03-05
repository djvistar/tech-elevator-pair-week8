package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
 
@Component
public class JdbcAccountDAO implements AccountDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JdbcAccountDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}	
	
	
	
	@Override
	public double retrieveBalance(int id) {
		// TODO Auto-generated method stub

		double currentBalance = 0;
		String sql = "SELECT balance FROM accounts " +
		             "WHERE user_id = ?;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		
		results.next(); 
		currentBalance = results.getDouble("balance");

		return currentBalance;
	}
	
//	private Account mapToAccount(SqlRowSet results) {
//		Account account = new Account();
//		
//		account.setAccountBalance(results.getDouble("balance"));
//		account.setAccountId(results.getLong("account_id"));
//		
//		
//		
//		return account;
//		
//	}
	
//	private User mapToUser(SqlRowSet results) {
//		User user = new User();
//		
//		user.setId(results.getLong("user_id"));
//		user.setUsername(results.getString("username"));
//		//user.setPassword(results.getString("password_hash"));
//		
//		return user;
//	}
	
	

}
