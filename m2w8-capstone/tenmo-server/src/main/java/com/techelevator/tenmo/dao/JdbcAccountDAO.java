package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcAccountDAO implements AccountDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JdbcAccountDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}	
	
	
	
	@Override
	public double retrieveBalance(long accountId) {
		// TODO Auto-generated method stub
		double currentBalance = 0;
		String sql = "SELECT balance FROM accounts"+
		             "WHERE user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
		
		if (results.next()) {
			currentBalance = results.getDouble("balance");
		}
		
		
		return 0;
	}

}
