package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer  {
    private int userID;
    private int transactionId;
    private int userFrom;
    private int userTo;
    private BigDecimal amount;
    private Transfer transfer;
    private int statusId;

    public Transfer(int userID, int transactionId, int userFrom, int userTo, BigDecimal amount, Transfer transfer, int statusId) {
        this.userID = userID;
        this.transactionId = transactionId;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
        this.transfer = transfer;
        this.statusId = statusId;
    }

    public Transfer() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
