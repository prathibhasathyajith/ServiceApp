/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.TransactionTypeBean;
import com.serviceapp.bean.systemconfig.TransactionTypeInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.common.dao.Validation;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.dao.systemconfig.TransactionTypeDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.TransactionType;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.OracleMessage;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
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
public class TransactionTypeAction extends ActionSupport implements ModelDriven<Object>{
    TransactionTypeInputBean inputBean = new TransactionTypeInputBean();

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TransactionTypeAction : execute");
        return SUCCESS;
    }
    
    public String view() {

        String result = "view";
        try {
          
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
                inputBean.setDefaultStatus(CommonVarlist.STATUS_ACTIVE);

          
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called TransactionTypeAction :View");

        } catch (Exception ex) {
            addActionError("Transaction type " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String detail() {
        System.out.println("called TransactionTypeAction : detail");
        TransactionType tt = null;
        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                TransactionTypeDAO dao = new TransactionTypeDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

                tt = dao.findTransactionTypeById(inputBean.getTransactiontypecode());

                inputBean.setTransactiontypecode(tt.getTypecode());
                inputBean.setDescription(tt.getDescription());
                inputBean.setStatus(tt.getStatus().getStatuscode());

                try {
                    inputBean.setDescription_receiver(tt.getDescriptionReceiver());
                } catch (Exception e) {
                    inputBean.setDescription_receiver("");
                }
                inputBean.setOldvalue(inputBean.getTransactiontypecode() + "|" + inputBean.getDescription() + "|" + inputBean.getStatus());

            } else {
                inputBean.setMessage("Empty TransactionType id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("TransactionType id  " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called TransactionType : ViewPopup");
        try {
                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called TransactionTypeAction :viewpopup");

        } catch (Exception ex) {
            addActionError("TransactionType " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Add() {
        System.out.println("called TransactionTypeAction : Add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TransactionTypeDAO dao = new TransactionTypeDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                String newV = inputBean.getTransactiontypecode() + "|"
                        + inputBean.getDescription() + "|"
                        + inputBean.getStatus() + "|";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.TXN_TYPE_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Transaction type code " + inputBean.getTransactiontypecode() + " added", null, null, newV);
                message = dao.insertTransactionType(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Transaction type " + MessageVarlist.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Transaction type " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getTransactiontypecode() == null || inputBean.getTransactiontypecode().trim().isEmpty()) {
            message = MessageVarlist.TXN_TYPE_MGT_EMPTY_TT_CODE;
        } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
            message = MessageVarlist.TXN_TYPE_MGT_EMPTY_DESCRIPTION;
        } else if (inputBean.getStatus() != null && inputBean.getStatus().isEmpty()) {
            message = MessageVarlist.TXN_TYPE_MGT_EMPTY_STATUS;
        } else if (inputBean.getDescription_receiver() != null && inputBean.getDescription_receiver().isEmpty()) {
            message = MessageVarlist.TXN_TYPE_MGT_EMPTY_DESCRIPTION_RECIEVE;
        } else {
            if (!Validation.isSpecailCharacter(inputBean.getTransactiontypecode())) {
                message = MessageVarlist.TXN_TYPE_MGT_ERROR_TT_CODE_INVALID;
            } else if (!Validation.isSpecailCharacter(inputBean.getDescription())) {
                message = MessageVarlist.TXN_TYPE_MGT_ERROR_DESC_INVALID;
            }
        }
        return message;
    }

    public String List() {
        System.out.println("called TransactionTypeAction: List");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
            TransactionTypeDAO dao = new TransactionTypeDAO();
            List<TransactionTypeBean> dataList = dao.getSearchListHQL(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Transaction Type Code", inputBean.getS_transactiontypecode())
                        + checkEmptyorNullString("Description", inputBean.getS_description())
                        + checkEmptyorNullString("Status", inputBean.getS_status())
                        + checkEmptyorNullString("Description receiver", inputBean.getS_description_receiver())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.TXN_TYPE_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Transaction type search using " + searchParameters + " parameters ", null);
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
        } catch (Exception e) {
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" Transaction type " + MessageVarlist.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String Find() {
        System.out.println("called TransactionTypeAction : Find");
        TransactionType tt = null;
        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                TransactionTypeDAO dao = new TransactionTypeDAO();

                tt = dao.findTransactionTypeById(inputBean.getTransactiontypecode());

                inputBean.setTransactiontypecode(tt.getTypecode());
                inputBean.setDescription(tt.getDescription());
                inputBean.setStatus(tt.getStatus().getStatuscode());

                try {
                    inputBean.setDescription_receiver(tt.getDescriptionReceiver());
                } catch (Exception e) {
                    inputBean.setDescription_receiver("");
                }

                inputBean.setOldvalue(inputBean.getTransactiontypecode()
                        + "|" + inputBean.getDescription()
                        + "|" + inputBean.getStatus()
                );

            } else {
                inputBean.setMessage("Empty transaction type code.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Transaction type  " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String Update() {

        System.out.println("called TransactionTypeAction : Update");
        String retType = "message";

        try {
            if (inputBean.getTransactiontypecode() != null && !inputBean.getTransactiontypecode().isEmpty()) {

                inputBean.setTransactiontypecode(inputBean.getTransactiontypecode());

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    TransactionTypeDAO dao = new TransactionTypeDAO();

                    String newV = inputBean.getTransactiontypecode() 
                            + "|" + inputBean.getDescription() 
                            + "|" + inputBean.getStatus();
                    
                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.TXN_TYPE_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Transaction type code " + inputBean.getTransactiontypecode() + " updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updateTransactionType(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Transaction type " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Transaction type " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String Delete() {
        System.out.println("called TransactionTypeAction : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            TransactionTypeDAO dao = new TransactionTypeDAO();
            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.DELETE_TASK, PageVarlist.TXN_TYPE_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Transaction type code " + inputBean.getTransactiontypecode() + " deleted", null);
            message = dao.deleteTransactionType(inputBean, audit);
            if (message.isEmpty()) {
                message = "Transaction type " + MessageVarlist.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

}
