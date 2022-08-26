package com.tenmo.controller;

import com.tenmo.dao.AccountDao;
import com.tenmo.dao.TransferDao;
import com.tenmo.dao.UserDao;
import com.tenmo.model.Account;
import com.tenmo.model.Transfer;
import com.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
//@RequestMapping
public class ServerController {
    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;

    public ServerController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }
     //Account
    @GetMapping(path= "/account/balance/{id}")
    public BigDecimal getAccountBalance(@PathVariable int userId) {
        return accountDao.getBalance(userId);
    }

    @GetMapping(path= "/account/{id}")
    public Account getByAccountId(@PathVariable int id) {
        return accountDao.getByAccountId(id);
    }

    //User
    @GetMapping(path="/user")
    public List<User> getAllUsers (){
        return userDao.findAll();
    }

    @GetMapping(path= "/user/{id}")
    public Integer getAccountId(@PathVariable int userId){return accountDao.getAccountId(userId); }

    @PostMapping(path="")
    public int createTransfer(Transfer transfer){
        return transferDao.createTransfer(transfer);
    }

    @GetMapping(path="/transfer")
    public List<Transfer> getAllTransfers(Principal principal){
        return transferDao.getTransferList(userDao.findIdByUsername(principal.getName()));
    }

    @GetMapping(path="/transfer/history/{id}")
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

    public Account updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

}
