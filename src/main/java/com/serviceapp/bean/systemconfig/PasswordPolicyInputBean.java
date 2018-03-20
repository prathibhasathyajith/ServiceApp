/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemconfig;

import com.serviceapp.mapping.Passwordpolicy;
import java.util.List;

/**
 *
 * @author prathibha
 */
public class PasswordPolicyInputBean {

    private String passwordpolicyid;
    private String minimumlength;
    private String maximumlength;
    private String minimumspecialcharacters;
    private String minimumuppercasecharacters;
    private String minimumnumericalcharacters;
    private String minimumlowercasecharacters;
    private String repeatcharactersallow;
    private String initialpasswordexpirystatus;
    private String passwordexpiryperiod;
    private String noofhistorypassword;
    private String minimumpasswordchangeperiod;
    private String idleaccountexpiryperiod;
    private String noofinvalidloginattempt;
    private String message;
    private String oldvalue;
//    private List<Status> statusList;
    /**
     * ******** user input data **********************
     */
    private boolean policyid;

    /*------------------------list data table  ------------------------------*/
    private List<PasswordPolicyBean> gridModel;
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

    Passwordpolicy ppbean;

    public String getPasswordpolicyid() {
        return passwordpolicyid;
    }

    public void setPasswordpolicyid(String passwordpolicyid) {
        this.passwordpolicyid = passwordpolicyid;
    }

    public String getMinimumlength() {
        return minimumlength;
    }

    public void setMinimumlength(String minimumlength) {
        this.minimumlength = minimumlength;
    }

    public String getMaximumlength() {
        return maximumlength;
    }

    public void setMaximumlength(String maximumlength) {
        this.maximumlength = maximumlength;
    }

    public String getMinimumspecialcharacters() {
        return minimumspecialcharacters;
    }

    public void setMinimumspecialcharacters(String minimumspecialcharacters) {
        this.minimumspecialcharacters = minimumspecialcharacters;
    }

    public String getMinimumuppercasecharacters() {
        return minimumuppercasecharacters;
    }

    public void setMinimumuppercasecharacters(String minimumuppercasecharacters) {
        this.minimumuppercasecharacters = minimumuppercasecharacters;
    }

    public String getMinimumnumericalcharacters() {
        return minimumnumericalcharacters;
    }

    public void setMinimumnumericalcharacters(String minimumnumericalcharacters) {
        this.minimumnumericalcharacters = minimumnumericalcharacters;
    }

    public String getMinimumlowercasecharacters() {
        return minimumlowercasecharacters;
    }

    public void setMinimumlowercasecharacters(String minimumlowercasecharacters) {
        this.minimumlowercasecharacters = minimumlowercasecharacters;
    }

    public String getRepeatcharactersallow() {
        return repeatcharactersallow;
    }

    public void setRepeatcharactersallow(String repeatcharactersallow) {
        this.repeatcharactersallow = repeatcharactersallow;
    }

    public String getInitialpasswordexpirystatus() {
        return initialpasswordexpirystatus;
    }

    public void setInitialpasswordexpirystatus(String initialpasswordexpirystatus) {
        this.initialpasswordexpirystatus = initialpasswordexpirystatus;
    }

    public String getPasswordexpiryperiod() {
        return passwordexpiryperiod;
    }

    public void setPasswordexpiryperiod(String passwordexpiryperiod) {
        this.passwordexpiryperiod = passwordexpiryperiod;
    }

    public String getNoofhistorypassword() {
        return noofhistorypassword;
    }

    public void setNoofhistorypassword(String noofhistorypassword) {
        this.noofhistorypassword = noofhistorypassword;
    }

    public String getMinimumpasswordchangeperiod() {
        return minimumpasswordchangeperiod;
    }

    public void setMinimumpasswordchangeperiod(String minimumpasswordchangeperiod) {
        this.minimumpasswordchangeperiod = minimumpasswordchangeperiod;
    }

    public String getIdleaccountexpiryperiod() {
        return idleaccountexpiryperiod;
    }

    public void setIdleaccountexpiryperiod(String idleaccountexpiryperiod) {
        this.idleaccountexpiryperiod = idleaccountexpiryperiod;
    }

    public String getNoofinvalidloginattempt() {
        return noofinvalidloginattempt;
    }

    public void setNoofinvalidloginattempt(String noofinvalidloginattempt) {
        this.noofinvalidloginattempt = noofinvalidloginattempt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public boolean isPolicyid() {
        return policyid;
    }

    public void setPolicyid(boolean policyid) {
        this.policyid = policyid;
    }
    

    public List<PasswordPolicyBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<PasswordPolicyBean> gridModel) {
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

    public Passwordpolicy getPpbean() {
        return ppbean;
    }

    public void setPpbean(Passwordpolicy ppbean) {
        this.ppbean = ppbean;
    }

}
