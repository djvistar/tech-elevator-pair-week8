package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.model.Account;

public class JdbcAccountDAOTest {

	private JdbcAccountDAO dao;
	private static SingleConnectionDataSource dataSource;


	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/*
	 * This method provides access to the DataSource for subclasses so that they can
	 * instantiate a DAO for testing
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Before
	public void setup() {
		dao = new JdbcAccountDAO(dataSource);
	}
	
	@Test
	public void test_if_returns_account_balance() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		String insertUser = "INSERT INTO users (username, password_hash) VALUES (Barack Obama, qweproiuasdnfakl);";
		jdbcTemplate.update(insertUser);

		String getUserId = "SELECT user_id FROM users WHERE username = 'Barack Obama';";
		SqlRowSet queryResults = jdbcTemplate.queryForRowSet(getUserId);
		queryResults.next();
		int testUserId = queryResults.getInt("user_id");
		
		Account testAccount = new Account();
		testAccount.setAccountId(10);
		testAccount.setAccountBalance(987654.00);
		testAccount.setUserId(testUserId);
		String addAccountToDB = "INSERT INTO accounts (account_id, user_id, balance) VALUES (10, ?, 987654.00);";
		jdbcTemplate.update(addAccountToDB, testUserId);

		double result = dao.retrieveBalance(testUserId);
		
		
		Assert.assertEquals(testAccount.getAccountBalance(), result);
		
	}
	
//	private void assertBalancesEqual(Account expected, Account actual) {
//		assertEquals(expected.getAccountBalance(), actual.getAccountBalance());
//	}
	
}
