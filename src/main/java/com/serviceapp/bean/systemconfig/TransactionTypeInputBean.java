/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

import com.serviceapp.mapping.Status;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class TransactionTypeInputBean {
    private String transactiontypecode;
    private String description;
    private String status;
    private String description_receiver;

    private List<Status> statusList;

    private String defaultStatus;
    private String message;
    private String newvalue;
    private String oldvalue;

    /*------ for search ----------*/
    private String s_transactiontypecode;
    private String s_description;
    private String s_status;
    private String s_description_receiver;


    /*------------------------list data table  ------------------------------*/
    private List<TransactionTypeBean> gridModel;
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

    private String SearchAudit;
    private boolean search;

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

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getS_transactiontypecode() {
        return s_transactiontypecode;
    }

    public void setS_transactiontypecode(String s_transactiontypecode) {
        this.s_transactiontypecode = s_transactiontypecode;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }

    public String getS_status() {
        return s_status;
    }

    public void setS_status(String s_status) {
        this.s_status = s_status;
    }

    public String getS_description_receiver() {
        return s_description_receiver;
    }

    public void setS_description_receiver(String s_description_receiver) {
        this.s_description_receiver = s_description_receiver;
    }

    public List<TransactionTypeBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<TransactionTypeBean> gridModel) {
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

    public String getSearchAudit() {
        return SearchAudit;
    }

    public void setSearchAudit(String SearchAudit) {
        this.SearchAudit = SearchAudit;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
    
    
}
