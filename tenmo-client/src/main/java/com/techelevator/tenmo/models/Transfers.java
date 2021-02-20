package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfers
{
	private int id;
	private String transferFrom;
	private String transferTo;
	private String transferType;
	private String transferStatus;
	private BigDecimal amount;
	
	public int getId()
	{
		return id;
	}
	
	public String getTransferFrom()
	{
		return transferFrom;
	}
	
	public String getTransferTo()
	{
		return transferTo;
	}
	
	public String getTransferType()
	{
		return transferType;
	}
	
	public String getTransferStatus() 
	{
		return transferStatus;
	}
	
	public BigDecimal getAmount()
	{
		return amount;
	}
}
