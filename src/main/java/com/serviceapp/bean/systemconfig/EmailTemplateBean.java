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
public class EmailTemplateBean {
    private String messageid;
    private String transactiontype;
    private String subject;
    private String createdtime;
    private String messageEmail;
    private long fullCount;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getMessageEmail() {
        return messageEmail;
    }

    public void setMessageEmail(String messageEmail) {
        this.messageEmail = messageEmail;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
}
