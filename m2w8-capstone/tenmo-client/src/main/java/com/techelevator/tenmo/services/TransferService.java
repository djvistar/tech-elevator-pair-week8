package com.techelevator.tenmo.services;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.view.ConsoleService;



public class TransferService {
	
	public static String AUTH_TOKEN = "";
	
	private  RestTemplate restTemplate = new RestTemplate();
	private String API_BASE_URL;
	
	
	public TransferService(String url) {
		API_BASE_URL = url;
	  }
	
	
	//viewCurrentBalance
	//viewTransferHistory()
	//sendBucks();
	
	
	public Account viewCurrentBalance() {
		Account account = null;
	      try{
	    	  account= restTemplate.exchange(API_BASE_URL + "accounts",  HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
	      }catch (RestClientResponseException ex) {
	  		System.out.println(ex);
	  	} catch (ResourceAccessException ex) {
	  		System.out.println(ex);
	  	} 

		
		return account;
	}
	
	
	
	
	
	
	
	
	  
	private HttpEntity makeAuthEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }


}
