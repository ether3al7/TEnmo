package com.tenmo.dao;

import com.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;
    public JdbcTransferDao(AccountDao accountDao, DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountDao = accountDao;
    }

    @Override
    public List<Transfer> getTransferList(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "
                + "FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public Transfer getTransferById(int transactionId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "
                + "FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public boolean sendTransfer(Transfer transfer, int userFrom, int userTo)  throws Exception {
        boolean success = false;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
                + "VALUES (?, ?, ?, ?, ?)";
        if (transfer.getAmount().doubleValue() > accountDao.getBalance(userFrom).doubleValue()) {
            throw new Exception("Transfer failed due to a lack of funds");
        } else {
            jdbcTemplate.update(sql,transfer.getTransferID(),transfer.getTransferStatusId(), userFrom, userTo, transfer.getAmount());

            String addSql = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
            jdbcTemplate.update(addSql, transfer.getAmount(), userTo);
            String subtractSql = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
            jdbcTemplate.update(subtractSql, transfer.getAmount(), userFrom);
            success = true;
        }
        return success;                     // success is always true? CHECK
    }

    @Override
    public int createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to,amount) "
        + "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";

            Integer id = jdbcTemplate.update(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        return id;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferID(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
