/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.cusmanagement;

/**
 *
 * @author prathibha_s
 */
public class SuggestedUserBean {

    private String id;
    private String mobUser;
    private String status;
    private String firstName;
    private String lastName;
    private String email;
    private String area;
    private String serviceRole;
    private String mobile;
    private String createdTime;
    
    //referrer user
    private String id_user;
    private String status_user;
    private String statuscode_user;
    private String mobile_user;
    private String firstName_user;
    private String email_user;
    private String lastName_user;
    private Boolean isMale_user;
    private String nic_user;
    
    private long fullCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobUser() {
        return mobUser;
    }

    public void setMobUser(String mobUser) {
        this.mobUser = mobUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getStatus_user() {
        return status_user;
    }

    public void setStatus_user(String status_user) {
        this.status_user = status_user;
    }

    public String getStatuscode_user() {
        return statuscode_user;
    }

    public void setStatuscode_user(String statuscode_user) {
        this.statuscode_user = statuscode_user;
    }

    public String getMobile_user() {
        return mobile_user;
    }

    public void setMobile_user(String mobile_user) {
        this.mobile_user = mobile_user;
    }

    public String getFirstName_user() {
        return firstName_user;
    }

    public void setFirstName_user(String firstName_user) {
        this.firstName_user = firstName_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getLastName_user() {
        return lastName_user;
    }

    public void setLastName_user(String lastName_user) {
        this.lastName_user = lastName_user;
    }

    public Boolean getIsMale_user() {
        return isMale_user;
    }

    public void setIsMale_user(Boolean isMale_user) {
        this.isMale_user = isMale_user;
    }

    public String getNic_user() {
        return nic_user;
    }

    public void setNic_user(String nic_user) {
        this.nic_user = nic_user;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
}
