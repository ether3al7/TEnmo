package com.tenmo.dao;

import com.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    public List<Transfer> getTransferList(int userId);
    public Transfer getTransferById(int transactionId);
    public boolean sendTransfer(int userFrom, int userTo, BigDecimal amount)  throws Exception;

    public int createTransfer(@Valid @RequestBody Transfer transfer); //might need this
//    public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
//    public List<Transfer> getPendingRequests(int userId);
//    public String updateTransferRequest(Transfer transfer, int statusId);
}
