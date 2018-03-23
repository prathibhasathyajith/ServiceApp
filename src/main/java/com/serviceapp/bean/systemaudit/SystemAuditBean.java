/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemaudit;

import com.serviceapp.mapping.Systemaudit;

/**
 *
 * @author prathibha_s
 */
public class SystemAuditBean {
    private String id;
    private String section;
    private String sectionDes;
    private String user;
    private String sdblpage;
    private String page;
    private String pageDes;
    private String task;
    private String taskDes;
    private String lastUpdatedDate;
    private String description;
    private String oldvalue;
    private String newvalue;
    private String createtime;
    private long fullCount;

     public SystemAuditBean() {
    }

    public SystemAuditBean(Systemaudit audit) {
        id = String.valueOf(audit.getSystemauditid());
//        user = audit.getUserrolecode().getUsername();
        user = audit.getLastupdateduser();
        description = audit.getDescription();
        section = audit.getSection();
        sdblpage = audit.getPage();
        task = audit.getTask();
        lastUpdatedDate = audit.getLastupdatedtime().toString();
        if (audit.getOldvalue() == null || audit.getOldvalue().isEmpty()) {
            oldvalue = "--";
        } else {
            oldvalue = audit.getOldvalue();
        }
        if (audit.getNewvalue() == null || audit.getNewvalue().isEmpty()) {
            newvalue = "--";
        } else {
            newvalue = audit.getNewvalue();
        }

    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionDes() {
        return sectionDes;
    }

    public void setSectionDes(String sectionDes) {
        this.sectionDes = sectionDes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSdblpage() {
        return sdblpage;
    }

    public void setSdblpage(String sdblpage) {
        this.sdblpage = sdblpage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageDes() {
        return pageDes;
    }

    public void setPageDes(String pageDes) {
        this.pageDes = pageDes;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public void setTaskDes(String taskDes) {
        this.taskDes = taskDes;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    
}
