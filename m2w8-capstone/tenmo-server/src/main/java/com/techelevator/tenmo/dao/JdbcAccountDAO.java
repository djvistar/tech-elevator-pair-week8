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
	public double retrieveBalance(int id) { // Calls the database to retrieve the balance listed for the current user 
											// (based on the Principal of the logged in user passed from the controller)
		// TODO Auto-generated method stub

		double currentBalance = 0;
		String sql = "SELECT balance FROM accounts " +
		             "WHERE user_id = ?;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		
		results.next(); 
		currentBalance = results.getDouble("balance");

		return currentBalance;
	}
	
}
