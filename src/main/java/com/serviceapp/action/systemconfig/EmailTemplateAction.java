/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.EmailTemplateBean;
import com.serviceapp.bean.systemconfig.EmailTemplateInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.dao.systemconfig.EmailTemplateDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.WebEmailTemplate;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class EmailTemplateAction extends ActionSupport implements ModelDriven<Object> {

    EmailTemplateInputBean emailTemplateInputBean = new EmailTemplateInputBean();
    EmailTemplateDAO emailTemplateDAO = new EmailTemplateDAO();

    public Object getModel() {
        return emailTemplateInputBean;
    }

    @Override
    public String execute() {
        System.out.println("called EmailTemplateAction : execute");

        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called EmailTemplateAction: view");
        try {

            CommonDAO dao = new CommonDAO();
            emailTemplateInputBean.setTransactiontypeList(dao.getTxnTypeList());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

        } catch (Exception e) {
            addActionError("EmailTemplateAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(EmailTemplateAction.class.getName()).log(Level.SEVERE, null, e);

        }
        return result;
    }

    public String list() {

        System.out.println("called EmailTemplateAction : list");
        try {
            ArrayList<EmailTemplateBean> dataList = new ArrayList<EmailTemplateBean>();

            int rows = emailTemplateInputBean.getRows();
            int page = emailTemplateInputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            dataList = emailTemplateDAO.getEmail(emailTemplateInputBean, rows, from);

            if (emailTemplateInputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Message Id", emailTemplateInputBean.getS_messageid())
                        + checkEmptyorNullString("Transaction Type", emailTemplateInputBean.getTransactiontypesearch())
                        + checkEmptyorNullString("Subject", emailTemplateInputBean.getS_subject())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.EMAIL_TEMPLATE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Email template search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                emailTemplateInputBean.setRecords(records);
                emailTemplateInputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                emailTemplateInputBean.setTotal(total);

            } else {
                emailTemplateInputBean.setRecords(0L);
                emailTemplateInputBean.setTotal(0);
            }
        } catch (Exception ex) {
            addActionError("EmailTemplateAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(EmailTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "list";
    }

    public String detail() {
        System.out.println("called EmailTemplateAction : detail");
        WebEmailTemplate webEmailtemplate = null;

        try {
            if (emailTemplateInputBean.getMessageid() != null && !emailTemplateInputBean.getMessageid().isEmpty()) {

                CommonDAO commonDAO = new CommonDAO();

                emailTemplateInputBean.setTransactiontypeList(commonDAO.getTxnTypeList());

                webEmailtemplate = emailTemplateDAO.getEmailById(emailTemplateInputBean.getMessageid());

                try {
                    emailTemplateInputBean.setMessageid(webEmailtemplate.getMessageid().toString());
                } catch (NullPointerException e) {
                    emailTemplateInputBean.setMessageid("");
                }

                try {
                    emailTemplateInputBean.setTransactiontype(webEmailtemplate.getTransactionType().getTypecode());
                } catch (NullPointerException e) {
                    emailTemplateInputBean.setTransactiontype("");
                }

                try {
                    emailTemplateInputBean.setSubject(webEmailtemplate.getSubject().toString());
                } catch (NullPointerException e) {
                    emailTemplateInputBean.setSubject("");
                }

                try {
                    emailTemplateInputBean.setMessageEmail(webEmailtemplate.getMessage().toString());
                } catch (NullPointerException e) {
                    emailTemplateInputBean.setMessageEmail("");
                }

            }

        } catch (Exception ex) {
            addActionError("EmailTemplateAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(EmailTemplateAction.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "detail";

    }

    public String Find() {

        System.out.println("called EmailTemplateAction: find");
        WebEmailTemplate webEmailtemplate = null;
        try {
            if (emailTemplateInputBean.getMessageid() != null && !emailTemplateInputBean.getMessageid().isEmpty()) {

                webEmailtemplate = emailTemplateDAO.getEmailById(emailTemplateInputBean.getMessageid());

                emailTemplateInputBean.setMessageid(webEmailtemplate.getMessageid().toString());
                emailTemplateInputBean.setTransactiontype(webEmailtemplate.getTransactionType().getTypecode());
                emailTemplateInputBean.setSubject(webEmailtemplate.getSubject());
                emailTemplateInputBean.setMessageEmail(webEmailtemplate.getMessage());

            }
        } catch (Exception ex) {
            addActionError("EmailTemplateAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(EmailTemplateAction.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "find";
    }

    public String update() {
        System.out.println("called EmailTemplateAction : update");
        String retType = "message";

        try {
            if (emailTemplateInputBean.getMessageid() != null && !emailTemplateInputBean.getMessageid().isEmpty()) {

                String message = this.validateupdateData();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.EMAIL_TEMPLATE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Email template " + emailTemplateInputBean.getMessageid() + " updated", null, null, null);
                    message = emailTemplateDAO.addUpdateData(emailTemplateInputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Email template " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }

            }
        } catch (Exception ex) {
            addActionError("EmailTemplateAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(EmailTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    private String validateupdateData() throws Exception {
        String message = "";

        try {
            if (emailTemplateInputBean.getMessageid() == null || emailTemplateInputBean.getMessageid().trim().isEmpty()) {
                message = MessageVarlist.EMAIL_TEMPLATE_MESSAGEID;
            } else if (emailTemplateInputBean.getSubject() == null || emailTemplateInputBean.getSubject().trim().isEmpty()) {
                message = MessageVarlist.EMAIL_TEMPLATE_SUBJECT;
            } else if (emailTemplateInputBean.getTransactiontype() == null || emailTemplateInputBean.getTransactiontype().trim().isEmpty()) {
                message = MessageVarlist.EMAIL_TEMPLATE_TYPECODE;
            } else if (emailTemplateInputBean.getMessageEmail() == null || emailTemplateInputBean.getMessageEmail().trim().isEmpty()) {
                message = MessageVarlist.EMAIL_TEMPLATE_MESSAGE;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }
}
