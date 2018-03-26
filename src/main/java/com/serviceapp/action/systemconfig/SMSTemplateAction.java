/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.SMSTemplateBean;
import com.serviceapp.bean.systemconfig.SMSTemplateInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.dao.systemconfig.SMSTemplateDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.WebSmsTemplate;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class SMSTemplateAction extends ActionSupport implements ModelDriven<Object> {

    SMSTemplateInputBean inputBean = new SMSTemplateInputBean();
    SMSTemplateDAO smsOutputDAO = new SMSTemplateDAO();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called SMSTemplateAction : execute");

        return SUCCESS;
    }

    public String view() {
        String result = "view";

        try {

            CommonDAO dao = new CommonDAO();
            inputBean.setTxnTypeList(dao.getTxnTypeList());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called SMSTemplateAction: view");

        } catch (Exception e) {
            addActionError("SMSTemplate " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SMSTemplateAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String list() {

        System.out.println("called SMSTemplateAction : list");
        try {
            ArrayList<SMSTemplateBean> dataList = new ArrayList<SMSTemplateBean>();

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            dataList = smsOutputDAO.getSearchList(inputBean, rows, from);

            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Message Id", inputBean.getMessageIdSearch())
                        + checkEmptyorNullString("Transaction Type", inputBean.getTxnTypeSearch())
                        + checkEmptyorNullString("Message", inputBean.getDescriptionSearch())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.SMS_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "SMS template search using " + searchParameters + " parameters ", null);
                SystemAuditDAO sysdao = new SystemAuditDAO();
                sysdao.saveAudit(audit);
            }

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                inputBean.setRecords(records);
                inputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                inputBean.setTotal(total);

            } else {
                inputBean.setRecords(0L);
                inputBean.setTotal(0);
            }
        } catch (Exception ex) {
            addActionError("SMSTemplate " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SMSTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "list";
    }

    public String detail() {
        System.out.println("called SMSTemplateAction : detail");
        WebSmsTemplate webSmsoutTemplate = null;

        try {
            if (inputBean.getMessageId() != null && !inputBean.getMessageId().isEmpty()) {

                CommonDAO commonDAO = new CommonDAO();

                inputBean.setTxnTypeList(commonDAO.getTxnTypeList());

                webSmsoutTemplate = smsOutputDAO.findTemplateById(inputBean.getMessageId());

                try {
                    inputBean.setMessageId(webSmsoutTemplate.getMessageid().toString());
                } catch (NullPointerException e) {
                    inputBean.setMessageId("");
                }

                try {
                    inputBean.setTxnType(webSmsoutTemplate.getTransactionType().getTypecode());
                } catch (NullPointerException e) {
                    inputBean.setTxnType("");
                }

                try {
                    inputBean.setDescription(webSmsoutTemplate.getMessage());
                } catch (NullPointerException e) {
                    inputBean.setDescription("");
                }

            }

        } catch (Exception ex) {
            addActionError("SMSTemplate " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SMSTemplateAction.class.getName()).log(Level.SEVERE, null, ex);

        }

        return "detail";

    }

    public String Find() {

        System.out.println("called SMSTemplateAction: find");
        WebSmsTemplate webSmsoutTemplate = null;
        try {
            if (inputBean.getMessageId() != null && !inputBean.getMessageId().isEmpty()) {

                webSmsoutTemplate = smsOutputDAO.findTemplateById(inputBean.getMessageId());

                inputBean.setMessageId(webSmsoutTemplate.getMessageid().toString());
                inputBean.setTxnType(webSmsoutTemplate.getTransactionType().getTypecode());
                inputBean.setDescription(webSmsoutTemplate.getMessage());

            }
        } catch (Exception ex) {
            addActionError("SMSTemplate " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SMSTemplateAction.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "find";
    }

    public String update() {
        System.out.println("called SMSTemplateAction : update");
        String retType = "message";

        try {
            if (inputBean.getMessageId() != null && !inputBean.getMessageId().isEmpty()) {

                String message = this.validateupdateData();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.SMS_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "SMS template " + inputBean.getMessageId() + " updated", null, null, null);
                    message = smsOutputDAO.addUpdateData(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("SMS template " + MessageVarlist.COMMON_SUCC_UPDATE);
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
            if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarlist.SMS_TEMPLATE_MESSAGE;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }
}
