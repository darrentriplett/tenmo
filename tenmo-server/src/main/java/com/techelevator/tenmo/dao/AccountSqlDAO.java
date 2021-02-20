package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class AccountSqlDAO implements AccountDAO
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public BigDecimal getBalance(int accountId)
	{
		//build the query
		String sql = "SELECT balance " +
					 "FROM accounts " +
					 "WHERE account_id = ?;";
		
		// execute the sql statement
		SqlRowSet rows =  jdbcTemplate.queryForRowSet(sql, accountId);
		
		while(rows.next())
		{
			BigDecimal balance  = rows.getBigDecimal("balance");
			
			return balance;
		}
		
		return null;
		
	}
	
	public void deposit(int accountId, BigDecimal amount)
	{
		//build the query
		String sql = "UPDATE accounts " +
					 "SET balance = balance + ? " +
					 "WHERE account_id = ?;";
		
		// execute the sql statement
		jdbcTemplate.update(sql, amount, accountId);
	}

	public void withdrawal(int accountId, BigDecimal amount)
	{
		//build the query
		String sql = "UPDATE accounts " +
					 "SET balance = balance - ? " +
					 "WHERE account_id = ?;";
		
		// execute the sql statement
		jdbcTemplate.update(sql, amount, accountId);
	}
	
}
