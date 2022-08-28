package com.techelevator.services;

import com.techelevator.model.Account;
import com.techelevator.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;
    //private String token;
    //    public void setToken(String token){
    //        this.token = token;
    //    }

    public Account[] getAllAccounts() {
        Account[] accounts = null;
        try {
            ResponseEntity<Account[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public Account getByAccountId(int id) {
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET,
                    makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public Integer getAccountId(int id) {
        Integer accountId = null;
        try {
            accountId = restTemplate.exchange(API_BASE_URL + "user/" + id, HttpMethod.GET, makeAuthEntity(), Integer.class).getBody();
//            ResponseEntity<Integer> response = restTemplate.exchange(API_BASE_URL + "user/" + userId, HttpMethod.GET,
//                    makeAuthEntity(), Integer.class);
//            accountId = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountId;
    }

//    public Integer getUserId(int id) {
//        Integer userId = null;
//        try {
//            userId = restTemplate.exchange(API_BASE_URL + "user/" + id, HttpMethod.GET, makeAuthEntity(), Integer.class).getBody();
//
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return userId;
//    }

    public Account getByUserId(int id) {
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET,
                    makeAuthEntity(), Account.class);
            account = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public BigDecimal getBalance(AuthenticatedUser authenticatedUser) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(API_BASE_URL + "account/balance/" + authenticatedUser.getUser().getId(),
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();

        } catch (RestClientResponseException |ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public boolean update(Account updatedAccount) {
        boolean success = false;
        HttpEntity<Account> entity = makeAccountEntity(updatedAccount);
        try {
            restTemplate.put(API_BASE_URL + "account/balance/" + updatedAccount.getAccountId(), entity);
            success = true;

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public String getUsername(int userId) {
        String username = null;
        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(API_BASE_URL + "username/" + userId, HttpMethod.GET,makeAuthEntity(), String.class);
            username = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return username;
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
     //   headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

}
