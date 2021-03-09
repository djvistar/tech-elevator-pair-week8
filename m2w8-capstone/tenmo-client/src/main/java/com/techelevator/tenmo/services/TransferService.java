package com.techelevator.tenmo.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferRequest;
import com.techelevator.view.ConsoleService;

public class TransferService {

	public static String AUTH_TOKEN = "";

	private RestTemplate restTemplate = new RestTemplate();
	private String API_BASE_URL;

	private AuthenticatedUser currentUser;

	public TransferService(String url) {
		API_BASE_URL = url;
	}



	public Double viewCurrentBalance() { // Communicates with the server/controller to retrieve the current balance
		Double account = 0.0; // If no account is found, balance will remain zero
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
	
	public Transfer[] viewTransferHistory(){ // Communicates with the server/controller to retrieve the transfer history
		Transfer[] allTransfers = null; // If no account is found, there is no history, so returns null
	   
	    try {
	        allTransfers = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
       
	    } catch (RestClientResponseException ex) {
	    	System.out.println(ex);
	      } catch (ResourceAccessException ex) {
	    	System.out.println(ex);  
	      }

		return allTransfers;
		
	}
	

 public Transfer viewTransferDetails(int transferId) { // Communicates with server/controller to retrieve details of user-specified transfer
	  Transfer transferDetails = null; // If no transfer is found, there are no details, so remains null
	
  	try {
	        transferDetails = restTemplate.exchange(API_BASE_URL + "transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
       } catch (RestClientResponseException ex) {
 	     System.out.println(ex);
      } catch (ResourceAccessException ex) {
 	    System.out.println(ex);  
   }
	return transferDetails; 
 }
	
 
 public void sendBucks(TransferRequest transferRequest) { // Initiates the transfer on the client side, passes info between Service and Controller
	 
	 HttpHeaders headers = new HttpHeaders(); // Creates the entity specific to Object TransferRequest
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<TransferRequest> entity = new HttpEntity<>(transferRequest, headers);
	 
	 try {
		ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, entity, Transfer.class);

	 } catch (RestClientResponseException ex) {
 	     System.out.println(ex);
      } catch (ResourceAccessException ex) {
 	    System.out.println(ex);  
   }
 
 }
 
 public User[] listOfUsers() { // Retrieves the list of all Tenmo users
	 User[] users = null; // If no users are found, remains null
	 
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
