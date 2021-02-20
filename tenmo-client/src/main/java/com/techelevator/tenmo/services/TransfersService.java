package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;

public class TransfersService extends ApiServiceBase {
	private AuthenticatedUser currentUser;

	public TransfersService(String url, AuthenticatedUser currentUser) {
		super(url);
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}

	public List<Transfers> getTransfers(int accountId) {
		List<Transfers> transfers = new ArrayList<Transfers>();

		try {
			// getForObject will not work because we are using authentication
			Transfers[] transfersArray = restTemplate
					.exchange(BASE_URL + "transfers/" + accountId, HttpMethod.GET, makeEntity(), Transfers[].class)
					.getBody();
			
			transfers = Arrays.asList(transfersArray);
//			for (Transfers transfer : transfersArray) {
//				System.out.println("--------------------------------");
//				System.out.println("Id: " + transfer.getId());
//				System.out.println("From: " + transfer.getTransferFrom());
//				System.out.println("To: " + transfer.getTransferTo());
//				System.out.println("Type: " + transfer.getTransferType());
//				System.out.println("Status: " + transfer.getTransferStatus());
//				System.out.println("Amount: " + transfer.getAmount());
//			}
		} catch (RestClientException e) {
			System.out.println("Transfers not available");
		}
		return transfers;
	}

	public void createTransfers(int accountIdTo, int accountIdFrom, BigDecimal amount) {
		String plus_url = accountIdTo + "/" + accountIdFrom + "/" + amount;
		try {
			restTemplate.exchange(BASE_URL + "transfers/" + plus_url, HttpMethod.POST, makeEntity(), Transfers.class);
		} catch (RestClientException e) {
			System.out.println("The transaction could not be processed.");
		}
	}

	public void createRequests(int accountIdTo, int accountIdFrom, BigDecimal amount) {
		String plus_url = accountIdTo + "/" + accountIdFrom + "/" + amount;
		try {
			restTemplate.exchange(BASE_URL + "/transfers/requests/" + plus_url, HttpMethod.POST, makeEntity(),
					Transfers.class);
		} catch (RestClientException e) {
			System.out.println("The transaction could not be processed.");
		}
	}

	public List<Transfers> getRequests(int accountId) {
		List<Transfers> transfers = new ArrayList<Transfers>();

		try {
			// getForObject will not work because we are using authentication
			Transfers[] transfersArray = restTemplate.exchange(BASE_URL + "transfers/getRequest/" + accountId,
					HttpMethod.GET, makeEntity(), Transfers[].class).getBody();
			transfers = Arrays.asList(transfersArray);
		} catch (RestClientException e) {
			System.out.println("Transfers not available");
		}
		return transfers;
	}

	public void confirmTransfer(int transferId, int transfer_status) {
		try {
			restTemplate.exchange(BASE_URL + "transfers/confirmRequest/" + transferId + "/" + transfer_status,
					HttpMethod.PUT, makeEntity(), Transfers.class).getBody();
		} catch (Exception e) {
			System.out.println("There was an error in confirming the transfer");
		}
	}



	private HttpEntity makeEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
