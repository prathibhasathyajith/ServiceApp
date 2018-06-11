/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.service;

/**
 *
 * @author prathibha_s
 */
public class ServiceRequestBean {
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
    private long fullCount;

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

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
}
