/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.bean.systemaudit;

import com.serviceapp.mapping.Systemuser;
import com.serviceapp.object.Page;
import com.serviceapp.object.Section;
import com.serviceapp.object.Task;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prathibha_s
 */
public class SystemAuditInputBean {
    private List<Systemuser> userList =  new ArrayList<Systemuser>();
    private List<Section> sectionList = new ArrayList<Section>();
    private List<Page> pageList = new ArrayList<Page>();
    private List<Task> taskList = new ArrayList<Task>();
    
    private boolean search;
    private List<SystemAuditBean> gridModel;
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;
    private long fullCount;

    private String auditId;
    private String user;
    private String section;
    private String description;
    private String sdblpage;
    private String task;
    private String fdate;
    private String tdate;
    private SystemAuditBean auditDataBean;
    
    private String reporttype;
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;

    public List<Systemuser> getUserList() {
        return userList;
    }

    public void setUserList(List<Systemuser> userList) {
        this.userList = userList;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public List<SystemAuditBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SystemAuditBean> gridModel) {
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

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSdblpage() {
        return sdblpage;
    }

    public void setSdblpage(String sdblpage) {
        this.sdblpage = sdblpage;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public SystemAuditBean getAuditDataBean() {
        return auditDataBean;
    }

    public void setAuditDataBean(SystemAuditBean auditDataBean) {
        this.auditDataBean = auditDataBean;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
    }

    public ByteArrayInputStream getZipStream() {
        return zipStream;
    }

    public void setZipStream(ByteArrayInputStream zipStream) {
        this.zipStream = zipStream;
    }
    
    
}
