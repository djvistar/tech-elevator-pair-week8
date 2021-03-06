package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.dao.JdbcTransferDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;

public class JdbcTransferDAOTest {

	private JdbcTransferDAO dao;
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
		dao = new JdbcTransferDAO(dataSource);
	}

	@Test
	public void test_return_list_of_all_transfers() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlDropTransfers = "DELETE FROM transfers;";
		jdbcTemplate.update(sqlDropTransfers);

		String sqlDropAccounts = "DELETE FROM accounts;";
		jdbcTemplate.update(sqlDropAccounts);

		String sqlDropUsers = "DELETE FROM users;";
		jdbcTemplate.update(sqlDropUsers);

		String sqlInsertUser1 = "INSERT INTO users (user_id, username, password_hash) VALUES (1, 'lebron', '123456789');";
		jdbcTemplate.update(sqlInsertUser1);

		String sqlInsertUser2 = "INSERT INTO users (user_id, username, password_hash) VALUES (2, 'kobe', '987654321');";
		jdbcTemplate.update(sqlInsertUser2);

		String sqlInsertAccount1 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (10, 1, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount1);

		String sqlInsertAccount2 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (11, 2, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount2);

		String sqlInsertTransfer1 = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, "
				+ "account_to, amount) VALUES (800, 2, 2, 10, 11, 100.00);";
		jdbcTemplate.update(sqlInsertTransfer1);

		String sqlInsertTransfer2 = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, "
				+ "account_to, amount) VALUES (801, 2, 2, 11, 10, 200.00);";
		jdbcTemplate.update(sqlInsertTransfer2);

		String sqlInsertTransfer3 = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, "
				+ "account_to, amount) VALUES (802, 2, 2, 10, 11, 300.00);";
		jdbcTemplate.update(sqlInsertTransfer3);

		Transfer testTransfer1 = new Transfer();
		testTransfer1.setTransferId(800);
		testTransfer1.setTransferTypeId(2);
		testTransfer1.setTransferStatusId(2);
		testTransfer1.setAccountFrom(10);
		testTransfer1.setAccountTo(11);
		testTransfer1.setAmount(100.00);

		Transfer testTransfer2 = new Transfer();
		testTransfer2.setTransferId(801);
		testTransfer2.setTransferTypeId(2);
		testTransfer2.setTransferStatusId(2);
		testTransfer2.setAccountFrom(11);
		testTransfer2.setAccountTo(10);
		testTransfer2.setAmount(200.00);

		Transfer testTransfer3 = new Transfer();
		testTransfer3.setTransferId(802);
		testTransfer3.setTransferTypeId(2);
		testTransfer3.setTransferStatusId(2);
		testTransfer3.setAccountFrom(10);
		testTransfer3.setAccountTo(11);
		testTransfer3.setAmount(300.00);

		List<Transfer> results = dao.listOfAllTransfers();

		assertEquals(3, results.size());
		assertTransfersEqual(testTransfer3, results.get(results.size() - 1));

	}

	@Test
	public void test_list_all_transfer_details() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlDropTransfers = "DELETE FROM transfers;";
		jdbcTemplate.update(sqlDropTransfers);

		String sqlDropAccounts = "DELETE FROM accounts;";
		jdbcTemplate.update(sqlDropAccounts);

		String sqlDropUsers = "DELETE FROM users;";
		jdbcTemplate.update(sqlDropUsers);

		String sqlInsertUser1 = "INSERT INTO users (user_id, username, password_hash) VALUES (1, 'lebron', '123456789');";
		jdbcTemplate.update(sqlInsertUser1);
		
		String sqlInsertUser2 = "INSERT INTO users (user_id, username, password_hash) VALUES (2, 'kobe', '987654321');";
		jdbcTemplate.update(sqlInsertUser2);
		
		String sqlInsertAccount1 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (10, 1, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount1);
		
		String sqlInsertAccount2 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (11, 2, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount2);
		
		String sqlInsertTransfer1 = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, "
				+ "account_to, amount) VALUES (800, 2, 2, 10, 11, 100.00);";
		jdbcTemplate.update(sqlInsertTransfer1);
		
		Transfer testDetailedTransfer = new Transfer();
		testDetailedTransfer.setTransferId(800);
		testDetailedTransfer.setTransferType("Send");
		testDetailedTransfer.setTransferStatus("Approved");
		testDetailedTransfer.setUserFrom("lebron");
		testDetailedTransfer.setUserTo("kobe");
		testDetailedTransfer.setAmount(100.00);
		
		Transfer result = dao.listTransferDetails(800);
		
		assertDetailsEqual(testDetailedTransfer, result);
		
	}
	
	@Test
	public void test_if_transfer_works() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sqlDropTransfers = "DELETE FROM transfers;";
		jdbcTemplate.update(sqlDropTransfers);

		String sqlDropAccounts = "DELETE FROM accounts;";
		jdbcTemplate.update(sqlDropAccounts);

		String sqlDropUsers = "DELETE FROM users;";
		jdbcTemplate.update(sqlDropUsers);
		
		String sqlInsertUser1 = "INSERT INTO users (user_id, username, password_hash) VALUES (1, 'lebron', '123456789');";
		jdbcTemplate.update(sqlInsertUser1);
		
		String sqlInsertUser2 = "INSERT INTO users (user_id, username, password_hash) VALUES (2, 'kobe', '987654321');";
		jdbcTemplate.update(sqlInsertUser2);
		
		String sqlInsertAccount1 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (10, 1, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount1);
		
		String sqlInsertAccount2 = "INSERT INTO accounts (account_id, user_id, balance) VALUES (11, 2, 1000.00);";
		jdbcTemplate.update(sqlInsertAccount2);
		
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setReceiverId(2);
		transferRequest.setDestinationId(11);
		transferRequest.setAmount(100.00);
		
		Transfer testDetailedTransfer = new Transfer();
		testDetailedTransfer.setTransferId(800);
		testDetailedTransfer.setTransferType("Send");
		testDetailedTransfer.setTransferStatus("Approved");
		testDetailedTransfer.setUserFrom("lebron");
		testDetailedTransfer.setUserTo("kobe");
		testDetailedTransfer.setAmount(100.00);
		
		String result = dao.sendTransfer(transferRequest, 1);
		
		assertEquals("Transfer Complete", result);
		
	}

	private void assertTransfersEqual(Transfer expected, Transfer actual) {
		assertEquals(expected.getTransferId(), actual.getTransferId());
		assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
		assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
		assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
		assertEquals(expected.getAccountTo(), actual.getAccountTo());
		assertEquals(expected.getAmount(), actual.getAmount(), 0);
	}
	
	private void assertDetailsEqual(Transfer expected, Transfer actual) {
		assertEquals(expected.getTransferId(), actual.getTransferId());
		assertEquals(expected.getTransferType(), actual.getTransferType());
		assertEquals(expected.getTransferStatus(), actual.getTransferStatus());
		assertEquals(expected.getUserFrom(), actual.getUserFrom());
		assertEquals(expected.getUserTo(), actual.getUserTo());
		assertEquals(expected.getAmount(), actual.getAmount(), 0);
	}

}
