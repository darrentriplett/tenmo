package com.techelevator.tenmo.controller;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfers;
@RestController
@RequestMapping(path="transfers/")
public class TransfersController
{
	@Autowired
	TransferDAO dao;
	
	@GetMapping("{accountId}")
	public List<Transfers> getTransfers(@PathVariable int accountId)
	{
		List<Transfers> transfers = dao.getTransfers(accountId);
		return transfers;
	}
	
	@PostMapping("{accountIdTo}/{accountIdFrom}/{amount}")
	public void createTransfers(@PathVariable int accountIdTo,
								@PathVariable int accountIdFrom,
								@PathVariable BigDecimal amount)
{
		dao.createTransfers(accountIdTo, accountIdFrom, amount);
}
	
	@PostMapping("requests/{accountIdTo}/{accountIdFrom}/{amount}")
	public void createRequest(@PathVariable int accountIdTo,
								@PathVariable int accountIdFrom,
								@PathVariable BigDecimal amount)
	{
		dao.createRequests(accountIdTo, accountIdFrom, amount);
	}
	
	@GetMapping("getRequest/{accountId}")
	public List<Transfers> getRequests(@PathVariable int accountId)
	{
		List<Transfers> transfers = dao.getRequests(accountId);
		return transfers;
	}
	
	@PutMapping("confirmRequest/{transferId}/{transfer_status}")
	public void confirmTransfer(@PathVariable int transferId,
								@PathVariable int transfer_status)
	{
		dao.confirmTransfer(transferId, transfer_status);
	}
}
