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
public class ChartDataBean {
    private String year;
    private String month;
    private String monthDes;
    private String dformat;
    private String completedReq;
    private String cusCancelReq;
    private String bassCancelReq;
    private String bassRejReq;
    private String cusRejReq;
    private String bassPushedReq;
    private String initReq;
    private String totalReq;
    
    private String cancelAll;
    private String rejAll;
    
    
    private String message;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthDes() {
        return monthDes;
    }

    public void setMonthDes(String monthDes) {
        this.monthDes = monthDes;
    }

    public String getDformat() {
        return dformat;
    }

    public void setDformat(String dformat) {
        this.dformat = dformat;
    }

    public String getCompletedReq() {
        return completedReq;
    }

    public void setCompletedReq(String completedReq) {
        this.completedReq = completedReq;
    }

    public String getCusCancelReq() {
        return cusCancelReq;
    }

    public void setCusCancelReq(String cusCancelReq) {
        this.cusCancelReq = cusCancelReq;
    }

    public String getBassCancelReq() {
        return bassCancelReq;
    }

    public void setBassCancelReq(String bassCancelReq) {
        this.bassCancelReq = bassCancelReq;
    }

    public String getBassRejReq() {
        return bassRejReq;
    }

    public void setBassRejReq(String bassRejReq) {
        this.bassRejReq = bassRejReq;
    }

    public String getCusRejReq() {
        return cusRejReq;
    }

    public void setCusRejReq(String cusRejReq) {
        this.cusRejReq = cusRejReq;
    }

    public String getBassPushedReq() {
        return bassPushedReq;
    }

    public void setBassPushedReq(String bassPushedReq) {
        this.bassPushedReq = bassPushedReq;
    }

    public String getInitReq() {
        return initReq;
    }

    public void setInitReq(String initReq) {
        this.initReq = initReq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotalReq() {
        return totalReq;
    }

    public void setTotalReq(String totalReq) {
        this.totalReq = totalReq;
    }

    public String getCancelAll() {
        return cancelAll;
    }

    public void setCancelAll(String cancelAll) {
        this.cancelAll = cancelAll;
    }

    public String getRejAll() {
        return rejAll;
    }

    public void setRejAll(String rejAll) {
        this.rejAll = rejAll;
    }
    
    
}
