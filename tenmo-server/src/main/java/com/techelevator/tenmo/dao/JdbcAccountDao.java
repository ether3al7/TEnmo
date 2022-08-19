package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;

public class JdbcAccountDao  implements AccountDao {
    @Override
    public Account findByUserId(int userId) {
        return null;
    }

    @Override
    public Account getByAccountId(int accountId) {
        return null;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        return null;
    }
}
