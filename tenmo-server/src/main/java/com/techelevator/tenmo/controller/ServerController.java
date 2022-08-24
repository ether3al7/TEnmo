package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
//@RequestMapping(path="")
public class ServerController {
    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;

    public ServerController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    //@GetMapping(path= "")
    public BigDecimal getAccountBalance(Principal principal) {
        return accountDao.getBalance(principal.getName()); //returns String id?
        //changed AccountDao.getBalance to String id from int id
    }

    //@GetMapping(path="")
    public List<User> getAllUsers (){
        return userDao.findAll();
    }

    //need to create a transfer
    //@PostMapping(path="")
    public int createTransfer(Transfer transfer){
        return transferDao.createTransfer(transfer);
    }

    public List<Transfer> getAllTransfers(Principal principal){
        return transferDao.getTransferList(userDao.findIdByUsername(principal.getName()));
    }

    public Transfer getTransferById(int transferId) {
        return transferDao.getTransferById(transferId);
    }

    public Account updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

}
