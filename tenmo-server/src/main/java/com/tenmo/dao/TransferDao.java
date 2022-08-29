package com.tenmo.dao;

import com.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface TransferDao {
    public List<Transfer> getTransferList(int userId);
    public Transfer getTransferById(int transactionId);
  //  boolean sendTransfer(Transfer transfer, int userFrom, int userTo)  throws Exception;

    public boolean createTransfer(@Valid @RequestBody Transfer transfer); //might need this

//    public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
//    public List<Transfer> getPendingRequests(int userId);
//    public String updateTransferRequest(Transfer transfer, int statusId);
}
