/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemaudit;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemaudit.MobKeyVal;
import com.serviceapp.bean.systemaudit.MobLoginHistoryBean;
import com.serviceapp.bean.systemaudit.MobLoginHistoryInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.systemaudit.MobLoginHistoryDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author prathibha_s
 */
public class MobLoginHistoryAction extends ActionSupport implements ModelDriven<Object> {

    private MobLoginHistoryInputBean loginhistoryInputBean = new MobLoginHistoryInputBean();
    List<MobLoginHistoryBean> dataList = new ArrayList<MobLoginHistoryBean>();
    Map parameterMap = new HashMap();
    InputStream fileInputStream = null;

    public Map getParameterMap() {
        return parameterMap;
    }

    public List<MobLoginHistoryBean> getDataList() {
        return dataList;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String execute() {
        System.out.println("called MobLoginHistoryAction: execute");
        return SUCCESS;
    }

    public Object getModel() {
        return loginhistoryInputBean;
    }
    
    public String view() {

        String result = "view";
        try {

            
                CommonDAO dao = new CommonDAO();

                loginhistoryInputBean.setUserTypeList(this.getUserTypeList());
//                loginhistoryInputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_LOGIN_HISTORY));
//                loginhistoryInputBean.setTaskList(this.getTaskList());
//                loginhistoryInputBean.setLogintypeList(dao.getDefultLogintypeList());
            

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called MobLoginHistoryAction :view");

        } catch (Exception ex) {
            addActionError("Login history " + MessageVarlist.COMMON_ERROR_PROCESS);
        }
        return result;
    }
    
    public String list() {
        System.out.println("called MobLoginHistoryAction : List");
        try {
            if (loginhistoryInputBean.isSearch()) {

                int rows = loginhistoryInputBean.getRows();
                int page = loginhistoryInputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String sortIndex = "";
                String sortOrder = "";

                List<MobLoginHistoryBean> dataList = null;

                if (!loginhistoryInputBean.getSidx().isEmpty()) {
                    sortIndex = loginhistoryInputBean.getSidx();
                    sortOrder = loginhistoryInputBean.getSord();
                }
                String orderBy = "";
                if (!loginhistoryInputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + loginhistoryInputBean.getSidx() + " " + loginhistoryInputBean.getSord();
                }
                HttpServletRequest request = ServletActionContext.getRequest();
                MobLoginHistoryDAO dao = new MobLoginHistoryDAO();

                dataList = dao.getSearchList(loginhistoryInputBean, rows, from, orderBy);

                String searchParameters = "["
                        + checkEmptyorNullString("From Date", loginhistoryInputBean.getFromdate())
                        + checkEmptyorNullString("To Date", loginhistoryInputBean.getTodate())
                        + checkEmptyorNullString("ID", loginhistoryInputBean.getId())
                        + checkEmptyorNullString("User ID", loginhistoryInputBean.getUserid())
                        + checkEmptyorNullString("User Name", loginhistoryInputBean.getUsername())
                        + checkEmptyorNullString("User Type", loginhistoryInputBean.getType())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.MOB_LOGIN_HISTORY, SectionVarlist.SYSTEM_AUDIT, "Mobile Login history search using " + searchParameters + " parameters ", null, null, null);
                CommonDAO.saveAudit(audit);

                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    loginhistoryInputBean.setRecords(records);
                    loginhistoryInputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    loginhistoryInputBean.setTotal(total);

                    HttpSession session = ServletActionContext.getRequest().getSession(false);
                    session.setAttribute(SessionVarlist.AUDIT_SEARCHBEAN, loginhistoryInputBean);

                } else {
                    loginhistoryInputBean.setRecords(0L);
                    loginhistoryInputBean.setTotal(0);
                }
            }

        } catch (Exception e) {
//            this.loadPageData();
            Logger.getLogger(MobLoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError("Login history " + MessageVarlist.COMMON_ERROR_PROCESS);
            return "message";

        }
        return "list";
    }

    public String reportGenerate() {

        System.out.println("called MobLoginHistoryAction : reportGeneration");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm a");
        JRSwapFileVirtualizer virtualizer = null;
        JasperPrint jasperPrint = null;
        byte[] outputFile;
        Session hSession = null;
        String retMsg = "view";
        try {
            if (loginhistoryInputBean.getReporttype().equals("pdf")) {

                MobLoginHistoryDAO dao = new MobLoginHistoryDAO();
                CommonDAO commonDAO = new CommonDAO();

                cal.setTime(CommonDAO.getSystemDateLogin());
//            connection = CommonDAO.getConnection();

                HttpSession session = ServletActionContext.getRequest().getSession(false);
                MobLoginHistoryInputBean searchBean = (MobLoginHistoryInputBean) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);

                //get path
                ServletContext context = ServletActionContext.getServletContext();
                String imgPath = context.getRealPath("/resouces/images/cf.png");

                if (searchBean.getId() != null && !searchBean.getId().isEmpty()) {
//                SystemAuditDAO dao = new SystemAuditDAO();                
//                parameterMap.put("taskdes",dao.findAuditById("",searchBean.getWalletid(),"").getTask());
                    parameterMap.put("id", searchBean.getId().trim());
                } else {
//                parameterMap.put("task", "--");
                    parameterMap.put("id", "--");
                }

                if (searchBean.getUserid() != null && !searchBean.getUserid().isEmpty()) {
                    parameterMap.put("userid", searchBean.getUserid().trim());
                } else {
                    parameterMap.put("userid", "--");
                }

//            if (searchBean.getCustomerid()!= null && !searchBean.getCustomerid().isEmpty()) {
//                parameterMap.put("customerid", searchBean.getCustomerid().trim());
//            } else {
//                parameterMap.put("customerid", "--");
//            }
                if (searchBean.getUsername() != null && !searchBean.getUsername().isEmpty()) {
                    parameterMap.put("username", searchBean.getUsername().trim());
                } else {
                    parameterMap.put("username", "--");
                }

                if (searchBean.getType() != null && !searchBean.getType().isEmpty()) {
                    parameterMap.put("usertype", searchBean.getType().trim());
                } else {
                    parameterMap.put("usertype", "--");
                }

//                if (searchBean.getMobilenumber() != null && !searchBean.getMobilenumber().isEmpty()) {
//                    parameterMap.put("mobilenumber", searchBean.getMobilenumber().trim());
//                } else {
//                    parameterMap.put("mobilenumber", "--");
//                }
//            if (searchBean.getXcoordinate()!= null && !searchBean.getXcoordinate().isEmpty()) {
//                parameterMap.put("xcoordinate", searchBean.getXcoordinate().trim());
//            } else {
//                parameterMap.put("xcoordinate", "--");
//            }
//            if (searchBean.getYcoordinate()!= null && !searchBean.getYcoordinate().isEmpty()) {
//                parameterMap.put("ycoordinate", searchBean.getYcoordinate().trim());
//            } else {
//                parameterMap.put("ycoordinate", "--");
//            }
//                if (searchBean.getStatus() != null && !searchBean.getStatus().isEmpty()) {
//
//                    parameterMap.put("status", searchBean.getStatus().trim());
//                    parameterMap.put("statusDes", dao.getStatusDescriptionByCode(searchBean.getStatus().trim()));
//                } else {
//                    parameterMap.put("status", "--");
//                    parameterMap.put("statusDes", "--");
//                }
                if (searchBean.getFromdate() != null && !searchBean.getFromdate().isEmpty()) {
                    parameterMap.put("fromdate", searchBean.getFromdate().trim());

                } else {
                    parameterMap.put("fromdate", "--");
                }

                if (searchBean.getTodate() != null && !searchBean.getTodate().isEmpty()) {
                    parameterMap.put("todate", searchBean.getTodate().trim());
                    String startDateString = searchBean.getTodate();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date toDate;
                    try {
                        toDate = df.parse(startDateString);
                        int day = toDate.getDate();
                        day = day + 1;  // set 1 day earlier
                        toDate.setDate(day);
                        String newDateString = df.format(toDate);
                        parameterMap.put("SQL_tdate", newDateString.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    parameterMap.put("todate", "--");

                }

//                if (searchBean.getTask() != null && !searchBean.getTask().isEmpty()) {
//
//                    parameterMap.put("taskDes", dao.getTaskDescriptionByCode(searchBean.getTask().trim()));
//                    parameterMap.put("task", searchBean.getTask());
//
//                } else {
//                    parameterMap.put("taskDes", "--");
//                    parameterMap.put("task", "--");
//                }
//
//                if (searchBean.getLogintype() != null && !searchBean.getLogintype().isEmpty()) {
//
//                    parameterMap.put("typeDes", dao.getTypeDescriptionByCode(searchBean.getLogintype().trim()));
//                    parameterMap.put("type", searchBean.getLogintype());
//
//                } else {
//                    parameterMap.put("typeDes", "--");
//                    parameterMap.put("type", "--");
//                }
                parameterMap.put("bankaddressheader", CommonVarlist.REPORT_PBANK_ADD_HEADER);
//            parameterMap.put("totalrecordcount", new Long(searchBean.getFullCount()).toString());
                parameterMap.put("printeddate", sdf.format(cal.getTime()));
                parameterMap.put("bankaddress", CommonVarlist.REPORT_PBANK_ADD);
                parameterMap.put("banktel", CommonVarlist.REPORT_PBANK_TEL);
                parameterMap.put("bankmail", CommonVarlist.REPORT_PBANK_MAIL);
                parameterMap.put("imageurl", imgPath);

                // Virtualizer 
                String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
                File file = new File(directory);
                if (!file.exists()) {
                    file.mkdirs();
                }
                JRSwapFile swapFile = new JRSwapFile(directory, 4096, 200);
                virtualizer = new JRSwapFileVirtualizer(300, swapFile, true);
                parameterMap.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

                String reportLocation = context.getRealPath("WEB-INF/pages/systemaudit/report/mobileloginhistory/login_history_report.jasper");

                hSession = HibernateInit.sessionFactory.openSession();
                SessionImplementor sim = (SessionImplementor) hSession;

                jasperPrint = JasperFillManager.fillReport(reportLocation, parameterMap, sim.connection());

                if (virtualizer != null) {
                    virtualizer.setReadOnly(true);
                }

                outputFile = JasperExportManager.exportReportToPdf(jasperPrint);
                fileInputStream = new ByteArrayInputStream(outputFile);

                HttpServletRequest request = ServletActionContext.getRequest();
                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.GENERATE_TASK, PageVarlist.MOB_LOGIN_HISTORY, SectionVarlist.SYSTEM_AUDIT, "Login history PDF report generated", null);
                CommonDAO.saveAudit(audit);

                retMsg = "download";
            } else if (loginhistoryInputBean.getReporttype().trim().equalsIgnoreCase("exel")) {
//                 System.err.println("EXEL printing");
                MobLoginHistoryDAO dao = new MobLoginHistoryDAO();
                retMsg = "excelreport";
                ByteArrayOutputStream outputStream = null;
                try {

                    HttpSession session = ServletActionContext.getRequest().getSession(false);

                    MobLoginHistoryInputBean searchBean = (MobLoginHistoryInputBean) session.getAttribute(SessionVarlist.AUDIT_SEARCHBEAN);
//                    Audittrace audittrace = Common.makeAudittrace(request, TaskVarlist.REPORT_TASK, PageVarlist.EXCEPTIONS_RPT_PAGE, this.getSearchParam() + " excel report viewed", null);
//                    Object object = new Object();
                    Object object = dao.generateExcelReport(searchBean);
                    if (object instanceof SXSSFWorkbook) {
                        SXSSFWorkbook workbook = (SXSSFWorkbook) object;
                        outputStream = new ByteArrayOutputStream();
                        workbook.write(outputStream);
                        loginhistoryInputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));

                    } else if (object instanceof ByteArrayOutputStream) {
                        outputStream = (ByteArrayOutputStream) object;
                        loginhistoryInputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                        retMsg = "zip";
                    }

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.GENERATE_TASK, PageVarlist.MOB_LOGIN_HISTORY, SectionVarlist.SYSTEM_AUDIT, "Login history excel report generated ", null);
                    CommonDAO.saveAudit(audit);

                } catch (Exception e) {
                    addActionError(MessageVarlist.COMMON_ERROR_PROCESS + " exception detail excel report");
                    Logger.getLogger(MobLoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
//                    this.loadPageData();
                    retMsg = "view";
                    throw e;
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }

                    } catch (IOException ex) {
                        //do nothing
                    }
                }
            }
        } catch (Exception e) {
//            this.loadPageData();
            Logger.getLogger(MobLoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarlist.COMMON_ERROR_PROCESS + " Login history ");

            return "message";
        } finally {
            if (virtualizer != null) {
                virtualizer.cleanup();
            }
//            try{
//                 connection.close();
////                 fileInputStream.close();
//            }catch(Exception ex){
//                
//        }
            if (hSession != null) {
                hSession.close();
            }
        }

//        return "excelreport";
        return retMsg;
    }

    // only to display error msg at exception
//    private void loadPageData() {
//        try {
//            CommonDAO dao = new CommonDAO();
//            loginhistoryInputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_OTP_REQUEST_INITIATE));
//        } catch (Exception e) {
//            addActionError("Login history " + MessageVarlist.COMMON_ERROR_PROCESS);
//            Logger.getLogger(MobLoginHistoryAction.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
    
    private List<MobKeyVal> getUserTypeList() {
        MobKeyVal usertype = new MobKeyVal();
        List<MobKeyVal> userTypeList = new ArrayList<MobKeyVal>();

        usertype.setKey("DRIVER");
        usertype.setValue("Driver");

        userTypeList.add(usertype);
        usertype = new MobKeyVal();

        usertype.setKey("CUSTOMER");
        usertype.setValue("Customer");

        userTypeList.add(usertype);
        
        return userTypeList;

    }

}
