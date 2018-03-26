/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

import com.serviceapp.mapping.TransactionType;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class EmailTemplateInputBean {
    private String message;

    private List<EmailTemplateBean> gridModel;

    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private boolean loadonce = false;

    private String messageid;
    private String transactiontype;
    private String subject;
    private String createdtime;
    private String messageEmail;
    
    private List<TransactionType> transactiontypeList;
    private String s_messageid;
    private String s_subject;
    private String transactiontypesearch;
    private boolean search;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EmailTemplateBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<EmailTemplateBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

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

    public List<TransactionType> getTransactiontypeList() {
        return transactiontypeList;
    }

    public void setTransactiontypeList(List<TransactionType> transactiontypeList) {
        this.transactiontypeList = transactiontypeList;
    }

    public String getS_messageid() {
        return s_messageid;
    }

    public void setS_messageid(String s_messageid) {
        this.s_messageid = s_messageid;
    }

    public String getS_subject() {
        return s_subject;
    }

    public void setS_subject(String s_subject) {
        this.s_subject = s_subject;
    }

    public String getTransactiontypesearch() {
        return transactiontypesearch;
    }

    public void setTransactiontypesearch(String transactiontypesearch) {
        this.transactiontypesearch = transactiontypesearch;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    
}
