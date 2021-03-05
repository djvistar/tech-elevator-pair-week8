package com.techelevator.tenmo.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.view.ConsoleService;

public class TransferService {

	public static String AUTH_TOKEN = "";

	private RestTemplate restTemplate = new RestTemplate();
	private String API_BASE_URL;

	private AuthenticatedUser currentUser;

	public TransferService(String url) {
		API_BASE_URL = url;
	}

	// viewCurrentBalance
	// viewTransferHistory()
	// sendBucks();

	public Double viewCurrentBalance() {
		Double account = 0.0;
		try {
			account = restTemplate.exchange(API_BASE_URL + "accounts",
					HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		} catch (RestClientResponseException ex) {
			System.out.println(ex);
		} catch (ResourceAccessException ex) {
			System.out.println(ex);
		}
		return account;
	}
	
	public Transfer[] viewTransferHistory(){
		Transfer[] allTransfers = null;
	   
	    try {
	        allTransfers = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
       
	    } catch (RestClientResponseException ex) {
	    	System.out.println(ex);
	      } catch (ResourceAccessException ex) {
	    	System.out.println(ex);  
	      }

		return allTransfers;
		
	}
	

 public Transfer viewTransferDetails(int transferId) {
	  Transfer transferDetails = null;
	
  	try {
	        transferDetails = restTemplate.exchange(API_BASE_URL + "transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
       } catch (RestClientResponseException ex) {
 	     System.out.println(ex);
      } catch (ResourceAccessException ex) {
 	    System.out.println(ex);  
   }
	return transferDetails; 
 }
	
 
 public Transfer sendBucks(Transfer transfer) {
	 
	 //Transfer transfer = ;
	 
	 try {
		 transfer =	restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, makeAuthEntity(), Transfer.class).getBody();

	 } catch (RestClientResponseException ex) {
 	     System.out.println(ex);
      } catch (ResourceAccessException ex) {
 	    System.out.println(ex);  
   }
	return transfer; 
 }
 
 public User[] listOfUsers() {
	 User[] users = null;
	 
	 try {
	        users = restTemplate.exchange(API_BASE_URL + "allUsers", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();

	 }catch (RestClientResponseException ex) {
 	     System.out.println(ex);
      } catch (ResourceAccessException ex) {
 	    System.out.println(ex);  
   }
	 
	 
	 return users;
 }
	

	public void setAUTH_TOKEN(String aUTH_TOKEN) {
		AUTH_TOKEN = aUTH_TOKEN;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
