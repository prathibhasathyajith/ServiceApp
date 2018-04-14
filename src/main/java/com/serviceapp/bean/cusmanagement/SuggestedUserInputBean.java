/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.cusmanagement;

import com.serviceapp.mapping.Roles;
import com.serviceapp.mapping.Status;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author prathibha_s
 */
public class SuggestedUserInputBean {
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
    
    //search fields
    //referrer
    private String search_name_user;
    private String search_mobile_user;
    private String search_email_user;
    
    //referrer pic
    private byte[] prifIamge;
    private String prifImg;
    
    //suggest user search
    private String search_firstname;
    private String search_lastname;
    private String search_email;
    private String search_mobile;
    private String search_status;
    private String search_role;
    
    private String message;
    private String oldValue;
    private String newValue;
    
    //lists
    private List<Status> statusList;
    private List<Roles> serviceroleList;
    
    //table
    private List<SuggestedUserBean> gridModel;
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

    public String getSearch_name_user() {
        return search_name_user;
    }

    public void setSearch_name_user(String search_name_user) {
        this.search_name_user = search_name_user;
    }

    public String getSearch_mobile_user() {
        return search_mobile_user;
    }

    public void setSearch_mobile_user(String search_mobile_user) {
        this.search_mobile_user = search_mobile_user;
    }

    public String getSearch_email_user() {
        return search_email_user;
    }

    public void setSearch_email_user(String search_email_user) {
        this.search_email_user = search_email_user;
    }

    public byte[] getPrifIamge() {
        return prifIamge;
    }

    public void setPrifIamge(byte[] prifIamge) {
        this.prifIamge = prifIamge;
    }

    public String getPrifImg() {
        try {
            byte[] blobAsBytes = getPrifIamge();
            blobAsBytes = Base64.encodeBase64(blobAsBytes);
            this.prifImg = new String(blobAsBytes);
        } catch (Exception e) {
            this.prifImg = "";
        }
        return prifImg;
    }

    public void setPrifImg(String prifImg) {
        this.prifImg = prifImg;
    }
    

    public String getSearch_firstname() {
        return search_firstname;
    }

    public void setSearch_firstname(String search_firstname) {
        this.search_firstname = search_firstname;
    }

    public String getSearch_lastname() {
        return search_lastname;
    }

    public void setSearch_lastname(String search_lastname) {
        this.search_lastname = search_lastname;
    }

    public String getSearch_email() {
        return search_email;
    }

    public void setSearch_email(String search_email) {
        this.search_email = search_email;
    }

    public String getSearch_mobile() {
        return search_mobile;
    }

    public void setSearch_mobile(String search_mobile) {
        this.search_mobile = search_mobile;
    }

    public String getSearch_status() {
        return search_status;
    }

    public void setSearch_status(String search_status) {
        this.search_status = search_status;
    }

    public String getSearch_role() {
        return search_role;
    }

    public void setSearch_role(String search_role) {
        this.search_role = search_role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public List<Roles> getServiceroleList() {
        return serviceroleList;
    }

    public void setServiceroleList(List<Roles> serviceroleList) {
        this.serviceroleList = serviceroleList;
    }

    public List<SuggestedUserBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SuggestedUserBean> gridModel) {
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
