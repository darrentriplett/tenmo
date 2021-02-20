package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;

@RestController
@RequestMapping(path="account/")
public class AccountController
{
	@Autowired
	AccountDAO dao;
	@Autowired
	UserDAO userDAO;
	
	@GetMapping("balance/{accountId}")
	public BigDecimal getBalance(@PathVariable int accountId)
	{
		BigDecimal balance = dao.getBalance(accountId);
		return balance;
	}
	
	@PutMapping("balance/deposit/{accountId}/{amount}")
	public void deposit(@PathVariable int accountId, @PathVariable BigDecimal amount)
	{
		dao.deposit(accountId, amount);
	}
	
	@PutMapping("balance/withdraw/{accountId}/{amount}")
	public void withdrawal(@PathVariable int accountId, @PathVariable BigDecimal amount)
	{
		dao.withdrawal(accountId, amount);
	}

}
