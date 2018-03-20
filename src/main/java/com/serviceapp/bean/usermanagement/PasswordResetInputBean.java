/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.usermanagement;

/**
 *
 * @author prathibha
 */
public class PasswordResetInputBean {
    private String username;
    private String pwtooltip;
    private String userrole;
    private String currpwd;
    private String newpwd;
    private String renewpwd;
    private String husername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwtooltip() {
        return pwtooltip;
    }

    public void setPwtooltip(String pwtooltip) {
        this.pwtooltip = pwtooltip;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getCurrpwd() {
        return currpwd;
    }

    public void setCurrpwd(String currpwd) {
        this.currpwd = currpwd;
    }

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

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }
    
    
    
}
