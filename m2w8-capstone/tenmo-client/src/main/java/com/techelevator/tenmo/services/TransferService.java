package com.techelevator.tenmo.services;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.view.ConsoleService;

public class TransferService {

	public static String AUTH_TOKEN = "";

	private RestTemplate restTemplate = new RestTemplate();
	private String API_BASE_URL;
<<<<<<< HEAD
	 private AuthenticatedUser currentUser;
	
	public TransferService(String url, AuthenticatedUser currentUser) {
		API_BASE_URL = url;
	  }
	
	
	//viewCurrentBalance
	//viewTransferHistory()
	//sendBucks();
	
	
	public Account viewCurrentBalance() {
		Account account = null;
	      try{
	    	  account= restTemplate.exchange(API_BASE_URL + "accounts/balance" + currentUser.getUser().getId() ,  HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
	      }catch (RestClientResponseException ex) {
	  		System.out.println(ex);
	  	} catch (ResourceAccessException ex) {
	  		System.out.println(ex);
	  	} 
=======

	public TransferService(String url) {
		API_BASE_URL = url;
	}
>>>>>>> fbc768dc73f37fe34ebe06831c721443ae2b56a1

	// viewCurrentBalance
	// viewTransferHistory()
	// sendBucks();

	public double viewCurrentBalance() {
		double balance = 0;
		try {
			balance = restTemplate.exchange(API_BASE_URL + "accounts", HttpMethod.GET, makeAuthEntity(), double.class)
					.getBody();
		} catch (RestClientResponseException ex) {
			System.out.println(ex);
		} catch (ResourceAccessException ex) {
			System.out.println(ex);
		}

		return balance;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
