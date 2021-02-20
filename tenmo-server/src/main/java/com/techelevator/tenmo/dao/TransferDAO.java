package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;

public interface TransferDAO
{
	List<Transfers> getTransfers(int accountId);
	
	void createTransfers(int accountIdTo, int accountIdFrom, BigDecimal amount);
	
	void createRequests(int accountIdTo, int accountIdFrom, BigDecimal amount);
	
	List<Transfers> getRequests(int accountId);
	
	void confirmTransfer(int transferId, int transfer_status);
}
