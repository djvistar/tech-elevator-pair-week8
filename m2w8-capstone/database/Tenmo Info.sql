UPDATE accounts
SET balance = 5000
WHERE user_id = 1002;

SELECT user_id FROM users WHERE username = 'victor';
SELECT balance FROM accounts WHERE user_id = 1001;

SELECT * FROM accounts;

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (5000, 2, 2, 2001, 2002, 250.00);
INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (5001, 2, 2, 2003, 2002, 25.00);
INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (5002, 2, 2, 2003, 2001, 50.00);
INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (5003, 2, 2, 2002, 2001, 20.00);

SELECT t.*, u.username AS userFrom, v.username AS userTo 
FROM transfers t  
JOIN accounts a ON t.account_from = a.account_id 
JOIN accounts b ON t.account_to = b.account_id 
JOIN users u ON a.user_id = u.user_id 
JOIN users v ON b.user_id = v.user_id  
WHERE a.user_id = 1001 OR b.user_id = 1002;

SELECT transfers.* FROM transfers
JOIN accounts ON accounts.account_id = transfers.account_from 
--OR accounts.account_id = transfers.account_to
JOIN users ON users.user_id = accounts.user_id;

SELECT transfers.* 
FROM transfers
JOIN accounts ON accounts.account_id = transfers.account_from
JOIN users ON users.user_id = accounts.user_id 
WHERE transfer_id = 5000;

SELECT t.transfer_id, u.username AS userFrom, v.username AS userTo, ts.transfer_status_desc, tt.transfer_type_desc, t.amount FROM transfers t
JOIN accounts a ON t.account_from = a.account_id
JOIN accounts b ON t.account_to = b.account_id
JOIN users u ON a.user_id = u.user_id
JOIN users v ON b.user_id = v.user_id
JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id
JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id 
WHERE t.transfer_id = 5000;

ROLLBACK;