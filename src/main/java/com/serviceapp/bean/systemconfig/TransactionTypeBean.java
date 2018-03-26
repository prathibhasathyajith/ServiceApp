/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

/**
 *
 * @author prathibha_s
 */
public class TransactionTypeBean {
    private String transactiontypecode;
    private String description;
    private String status;
    private String description_receiver;
    private long fullCount;

    public String getTransactiontypecode() {
        return transactiontypecode;
    }

    public void setTransactiontypecode(String transactiontypecode) {
        this.transactiontypecode = transactiontypecode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription_receiver() {
        return description_receiver;
    }

    public void setDescription_receiver(String description_receiver) {
        this.description_receiver = description_receiver;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
}
