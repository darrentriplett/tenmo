package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDAO
{
	BigDecimal getBalance(int accountId);
	
	void withdrawal(int userId, BigDecimal withdrawal);
	
	void deposit(int userId, BigDecimal deposit);
}
