/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

import com.serviceapp.mapping.Status;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha
 */
public class TermsInputBean {

    private String status;
    private String statusadd;
    private String statusAct;

    private String logoutTime;

    private String description;
    private String descriptionadd;
    private String descriptionadd2;
    private String versionno;
    private String versionnoadd;

    private String oldvalue;
    private String newvalue;
    private String message;

    private boolean empty;

    private List<Status> StatusList;
    public List<TermsVersionBean> versionList;
    private List<TermsVersionBean> versionMap = new ArrayList<TermsVersionBean>();

    private List<TermsBean> gridModel;
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

    private String defaultStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusadd() {
        return statusadd;
    }

    public void setStatusadd(String statusadd) {
        this.statusadd = statusadd;
    }

    public String getStatusAct() {
        return statusAct;
    }

    public void setStatusAct(String statusAct) {
        this.statusAct = statusAct;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionadd() {
        return descriptionadd;
    }

    public void setDescriptionadd(String descriptionadd) {
        this.descriptionadd = descriptionadd;
    }

    public String getDescriptionadd2() {
        return descriptionadd2;
    }

    public void setDescriptionadd2(String descriptionadd2) {
        this.descriptionadd2 = descriptionadd2;
    }

    public String getVersionno() {
        return versionno;
    }

    public void setVersionno(String versionno) {
        this.versionno = versionno;
    }

    public String getVersionnoadd() {
        return versionnoadd;
    }

    public void setVersionnoadd(String versionnoadd) {
        this.versionnoadd = versionnoadd;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public List<Status> getStatusList() {
        return StatusList;
    }

    public void setStatusList(List<Status> StatusList) {
        this.StatusList = StatusList;
    }

    public List<TermsVersionBean> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<TermsVersionBean> versionList) {
        this.versionList = versionList;
    }

    public List<TermsVersionBean> getVersionMap() {
        return versionMap;
    }

    public void setVersionMap(List<TermsVersionBean> versionMap) {
        this.versionMap = versionMap;
    }

    public List<TermsBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<TermsBean> gridModel) {
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

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

}
