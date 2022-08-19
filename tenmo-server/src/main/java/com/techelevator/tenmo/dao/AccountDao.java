package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;

public interface AccountDao {
    Account findByUserId(int userId);
    Account getByAccountId(int accountId);
    BigDecimal getBalance(int userId);
}
