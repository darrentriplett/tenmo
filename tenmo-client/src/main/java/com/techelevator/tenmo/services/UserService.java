package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;


import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.models.AuthenticatedUser;

public class UserService extends ApiServiceBase {
	private AuthenticatedUser currentUser;

	public UserService(String url, AuthenticatedUser currentUser) {
		super(url);
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}
	
	public List<User> findAllUsers() {
		
		List<User> users;
		
		//User[] usersArray = restTemplate.getForObject(BASE_URL, User[].class);		
		
		User[] usersArray = restTemplate.exchange(BASE_URL + "users", 
				HttpMethod.GET, makeEntity(), User[].class).getBody();
		users = Arrays.asList(usersArray);
		
//		for(User user: temp)
//		{
//			System.out.println(user.getId() + " " + user.getUsername());
//		}
		return users;
}
	private HttpEntity makeEntity() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(currentUser.getToken());
	    HttpEntity entity = new HttpEntity<>(headers);
	    return entity;
}
}