package com.techelevator.tenmo.services;



import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;



import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;



public class AccountService extends ApiServiceBase {
	private AuthenticatedUser currentUser;

	public AccountService(String url, AuthenticatedUser currentUser) {
		super(url);
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}
	

	public BigDecimal getBalance() {
		BigDecimal balance = new BigDecimal(0);
		
		try {
			//getForObject will not work because we are using authentication
			balance = restTemplate.exchange(BASE_URL + "account/balance/" + currentUser.getUser().getId(), 
					HttpMethod.GET, makeEntity(), BigDecimal.class).getBody();
			//System.out.println(balance.toString());
		} catch (RestClientException e) {
			System.out.println("Balance not available");
		}
		return balance;
	}
	
	 public void deposit(int userId, BigDecimal amount) {
	        try 
	        {
	            restTemplate.exchange(BASE_URL + "account/balance/deposit/" + 
	            		userId + "/" + amount, 
	            		HttpMethod.PUT, makeEntity(), BigDecimal.class);
	        } 
	        catch(RestClientException e)
	        {
	        	System.out.println("The transaction could not be processed.");
	        }
	 }
	 
	 public void withdraw(int userId, BigDecimal amount) {
	        
	        try 
	        {
	            restTemplate.exchange(BASE_URL + "account/balance/withdraw/" + 
	            		userId + "/" + amount, 
	            		HttpMethod.PUT, makeEntity(), BigDecimal.class);
	        } 
	        catch(RestClientException e)
	        {
	        	System.out.println("The transaction could not be processed.");
	        }
	 }

	 private HttpEntity makeEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(currentUser.getToken());
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
}}
