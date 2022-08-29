package com.techelevator.services;

import com.techelevator.model.Transfer;
import com.techelevator.util.BasicLogger;
import com.techelevator.model.AuthenticatedUser;
import com.techelevator.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;
    //private String token;
    //    public void setToken(String token){
    //        this.token = token;
    //    }

    public Transfer[] getAllTransfers(int id) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfer/history/" + id, HttpMethod.GET, makeAuthEntity(),
                    Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferDetails(int id) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfer/history/user/" + id, HttpMethod.GET, makeAuthEntity(),
                    Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;
    }

    public boolean addTransfer(Transfer transfer) {
        boolean success = false;
        try {
            success = restTemplate.exchange(API_BASE_URL + "/transfer/" + transfer.getAccountFrom() + "/"
                    + transfer.getAccountTo(), HttpMethod.POST,makeTransferEntity(transfer),Boolean.class).getBody();
//            restTemplate.postForObject(API_BASE_URL + "/transfer/" + transfer.getAccountFrom() + "/" + transfer.getAccountTo(),
//                    makeTransferEntity(transfer), Transfer.class);
           // success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return success;
    }
    /*public Transfer addTransfer(Transfer transfer) {
        Transfer tran = null;
        try {
            tran = transfer;
            tran = restTemplate.exchange(API_BASE_URL + "/transfer/{userFrom}/{userTo}", HttpMethod.POST, makeTransferEntity(tran), Transfer.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return tran;
    }
    this is a potential alt method for the add transfer method. instead of using boolena, we instantiate an object and use it to post to url
*/
    public User[] getAllUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL + "user", HttpMethod.GET, makeAuthEntity(),User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return users;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //  headers.setBearerAuth(token);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        //   headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
