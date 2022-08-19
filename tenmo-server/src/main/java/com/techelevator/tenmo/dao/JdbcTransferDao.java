package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao  implements TransferDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Transfer> getTransferList(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferById(int transactionId) {
        return null;
    }

    @Override
    public String sendTransfer(int userFrom, int userTo, BigDecimal amount) {
        return null;
    }
}
