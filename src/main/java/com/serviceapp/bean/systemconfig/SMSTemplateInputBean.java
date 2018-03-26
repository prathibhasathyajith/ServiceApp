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
public class SMSTemplateInputBean {

    private String messageId;
    private String description;
    private String txnType;
    private String lastUpdatedUser;
    private String lastUpdatedTime;
    private String createdTime;
    private List<TransactionType> txnTypeList;
    private String message;
    private boolean search;


    /*------------------------list data table  ------------------------------*/
    private List<SMSTemplateBean> gridModel;
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

    /*------------------------for search  ------------------------------*/
    private String messageIdSearch;
    private String descriptionSearch;
    private String txnTypeSearch;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<TransactionType> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<TransactionType> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<SMSTemplateBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SMSTemplateBean> gridModel) {
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

    public String getMessageIdSearch() {
        return messageIdSearch;
    }

    public void setMessageIdSearch(String messageIdSearch) {
        this.messageIdSearch = messageIdSearch;
    }

    public String getDescriptionSearch() {
        return descriptionSearch;
    }

    public void setDescriptionSearch(String descriptionSearch) {
        this.descriptionSearch = descriptionSearch;
    }

    public String getTxnTypeSearch() {
        return txnTypeSearch;
    }

    public void setTxnTypeSearch(String txnTypeSearch) {
        this.txnTypeSearch = txnTypeSearch;
    }

}
