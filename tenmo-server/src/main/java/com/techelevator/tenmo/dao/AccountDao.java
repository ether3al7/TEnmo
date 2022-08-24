package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;

public interface AccountDao {
    Account findByUserId(int userId);
    Account getByAccountId(int accountId);
    BigDecimal getBalance(String username);
    BigDecimal getBalance(int userId); //added this for JdbcTransferDao sendTransfer method
    Account updateAccount(Account account);
}
