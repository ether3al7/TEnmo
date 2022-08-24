package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

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

    public BigDecimal getAccountBalance(Principal principal) {
//        return accountDao.getBalance();
        return null;
    }
}
