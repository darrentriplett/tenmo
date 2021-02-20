package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers
{
	private int id;
	private String transferFrom;
	private String transferTo;
	private String transferType;
	private String transferStatus;
	private BigDecimal amount;
	
	public Transfers() {}
	
	public Transfers(int id, String transferFrom, String transferTo, String transferType,
						String transferStatus, BigDecimal amount)
	{
			this.id = id;
			this.transferFrom = transferFrom;
			this.transferTo = transferTo;
			this.transferType = transferType;
			this.transferStatus = transferStatus;
			this.amount = amount;
	}
	
	

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
	
	public int setId(int id)
	{
		return this.id = id;
	}
	
	public String setTransferFrom(String name)
	{
		return this.transferFrom = name;
	}
	
	public String setTransferTo(String name)
	{
		return this.transferTo = name;
	}
	
	public String setTransferType(String transferType)
	{
		return this.transferType = transferType;
	}
	
	public String setTransferStatus(String transferStatus) 
	{
		return this.transferStatus = transferStatus;
	}
	
	public BigDecimal setAmount(BigDecimal amount)
	{
		return this.amount = amount;
	}
}
