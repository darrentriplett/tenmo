package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfers;

@Component
public class TransferSQLDAO implements TransferDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Transfers> getTransfers(int accountId)
	{
		List<Transfers> transfers = new ArrayList<Transfers>();
		
		String sql = "SELECT t.transfer_id " +
			       ", amount " +
			       ", u.username as from_name " +
			       ", uu.username as to_name " +
			       ", tt.transfer_type_desc as transfer_type " +
			       ", ts.transfer_status_desc " +
			"FROM transfers AS t " +
			"INNER JOIN users AS u " +
			"ON t.account_from = u.user_id " +
			"INNER JOIN users AS uu " +
			"ON t.account_to = uu.user_id " +
			"INNER JOIN transfer_types AS tt " +
			"USING(transfer_type_id) " +
			"INNER JOIN transfer_statuses AS ts " +
			"USING(transfer_status_id) " +
			"WHERE u.user_id = ? OR uu.user_id = ?;";
		
		SqlRowSet rows =  jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		
		while(rows.next())
		{
			Transfers transfer = new Transfers();
			int id = rows.getInt("transfer_id");
			BigDecimal amount = rows.getBigDecimal("amount");
			String fromName = rows.getString("from_name");
			String toName = rows.getString("to_name");
			String transferType = rows.getString("transfer_type");
			String transferStatus = rows.getString("transfer_status_desc");
		
			transfer.setId(id);
			transfer.setAmount(amount);
			transfer.setTransferFrom(fromName);
			transfer.setTransferTo(toName);
			transfer.setTransferType(transferType);
			transfer.setTransferStatus(transferStatus);
			
			transfers.add(transfer);
		}
		return transfers; 
	}
	           
		public void createTransfers(int accountIdTo, int accountIdFrom, BigDecimal amount)
		{
			String sql = "INSERT INTO transfers " +
						 "(" +
					         "transfer_type_id" +
					        ", transfer_status_id" +
					        ", account_from" +
					        ", account_to" +
					        ", amount" +
					        ") " +
					     "VALUES" +
					     "(" +
					     "2" +
					     ", 2" +
					     ", ?" +
					     ", ?" +
					     ", ?" + 
					     ");"; 
		
			jdbcTemplate.update(sql, accountIdTo, accountIdFrom, amount);
		}
		public void createRequests(int accountIdTo, int accountIdFrom, BigDecimal amount)
	{
			String sql = "INSERT INTO transfers " +
					 "(" +
			         "transfer_type_id" +
			        ", transfer_status_id" +
			        ", account_to" +
			        ", account_from" +
			        ", amount" +
			        ") " +
			     "VALUES" +
			     "(" +
			     "1" +
			     ", 1" +
			     ", ?" +
			     ", ?" +
			     ", ?" + 
			     ");";
			
			jdbcTemplate.update(sql, accountIdTo, accountIdFrom, amount);
	}
		public void confirmTransfer(int transferId, int transfer_status)
		{
			String sql = "UPDATE transfers" + 
						 " SET transfer_status_id = ?" + 
						 " WHERE transfer_id = ?;";
		
		
			jdbcTemplate.update(sql, transfer_status, transferId );
		}
		public List<Transfers> getRequests(int accountId)
		{
			List<Transfers> transfers = new ArrayList<>();
			
			String sql = " SELECT * " + 
						 " FROM transfers" + 
					     " WHERE transfer_status_id = 1 AND account_from = ?;";
		
							 
			SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, accountId);
			
			while(rows.next())
			{
				Transfers transfer = new Transfers();
				int id = rows.getInt("transfer_id");
				BigDecimal amount = rows.getBigDecimal("amount");
				String fromName = rows.getString("account_from");
				String toName = rows.getString("account_to");
				String transferType = rows.getString("transfer_type_id");
				String transferStatus = rows.getString("transfer_status_id");
			
				transfer.setId(id);
				transfer.setAmount(amount);
				transfer.setTransferFrom(fromName);
				transfer.setTransferTo(toName);
				transfer.setTransferType(transferType);
				transfer.setTransferStatus(transferStatus);
				
				transfers.add(transfer);
			}
			return transfers;
		}
}
