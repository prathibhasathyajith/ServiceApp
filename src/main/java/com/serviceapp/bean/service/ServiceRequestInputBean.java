/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.service;

import com.serviceapp.mapping.Status;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class ServiceRequestInputBean {

    private String serviceId;
    private String cusfname;
    private String bassfname;
    private String cusId;
    private String bassId;
    private String status;
    private String custAddress;
    private String latitude;
    private String longitude;
    private String updatedTime;
    private String createdTime;
    private String message;
    private String newValue;
    private String oldValue;

    private ServiceRequestInputBean serReq;

    //summary details
    private String statusWise_req_count;
    private String req_charge;
    private String req_cancel_reason;

    private List<Status> statusList = new ArrayList<Status>();
    //table
    private List<ServiceRequestBean> gridModel;
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
    private boolean search;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCusfname() {
        return cusfname;
    }

    public void setCusfname(String cusfname) {
        this.cusfname = cusfname;
    }

    public String getBassfname() {
        return bassfname;
    }

    public void setBassfname(String bassfname) {
        this.bassfname = bassfname;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getBassId() {
        return bassId;
    }

    public void setBassId(String bassId) {
        this.bassId = bassId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public ServiceRequestInputBean getSerReq() {
        return serReq;
    }

    public void setSerReq(ServiceRequestInputBean serReq) {
        this.serReq = serReq;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getStatusWise_req_count() {
        return statusWise_req_count;
    }

    public void setStatusWise_req_count(String statusWise_req_count) {
        this.statusWise_req_count = statusWise_req_count;
    }

    public String getReq_charge() {
        return req_charge;
    }

    public void setReq_charge(String req_charge) {
        this.req_charge = req_charge;
    }

    public String getReq_cancel_reason() {
        return req_cancel_reason;
    }

    public void setReq_cancel_reason(String req_cancel_reason) {
        this.req_cancel_reason = req_cancel_reason;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<ServiceRequestBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<ServiceRequestBean> gridModel) {
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

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

}
