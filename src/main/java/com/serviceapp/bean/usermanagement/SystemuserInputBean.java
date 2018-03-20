/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.usermanagement;

import com.serviceapp.mapping.Status;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class SystemuserInputBean {
    
    private String newpwd;
    private String renewpwd;
    private String cnewpwd;
    private String crenewpwd;
    private String pwtooltip;
    private String username;
    private String husername;
    private String password;
    private String confirmpassword;
    private String status;
    private String serviceid; //empid
    private String expirydate;
    private String fullname;
    private String address1;
    private String address2;
    private String city;
    private String contactno;
    private String nic;
    private String email;
    private String dateofbirth;
    private HashMap<String, String> dualAuthUserMap = new HashMap<String, String>();
    private List<Status> statusList;
    private String message;
    private String newvalue;
    private String oldvalue;
    /* ---------user inputs data-----  */

    /*--------list data table  ----------*/
    private List<SystemuserBean> gridModel;
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
    /*--------list data table--------*/

    private String SearchAudit;
    private boolean search;

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getRenewpwd() {
        return renewpwd;
    }

    public void setRenewpwd(String renewpwd) {
        this.renewpwd = renewpwd;
    }

    public String getCnewpwd() {
        return cnewpwd;
    }

    public void setCnewpwd(String cnewpwd) {
        this.cnewpwd = cnewpwd;
    }

    public String getCrenewpwd() {
        return crenewpwd;
    }

    public void setCrenewpwd(String crenewpwd) {
        this.crenewpwd = crenewpwd;
    }

    public String getPwtooltip() {
        return pwtooltip;
    }

    public void setPwtooltip(String pwtooltip) {
        this.pwtooltip = pwtooltip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public HashMap<String, String> getDualAuthUserMap() {
        return dualAuthUserMap;
    }

    public void setDualAuthUserMap(HashMap<String, String> dualAuthUserMap) {
        this.dualAuthUserMap = dualAuthUserMap;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
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

    public List<SystemuserBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SystemuserBean> gridModel) {
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
