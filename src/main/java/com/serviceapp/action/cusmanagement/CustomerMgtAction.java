/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.cusmanagement;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.cusmanagement.CustomerMgtBean;
import com.serviceapp.bean.cusmanagement.CustomerMgtInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.common.dao.Validation;
import com.serviceapp.dao.cusmanagement.CustomerMgtDAO;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.mapping.MobUser;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.OracleMessage;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class CustomerMgtAction extends ActionSupport implements ModelDriven<Object> {

    CustomerMgtInputBean inputBean = new CustomerMgtInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called CustomerMgtAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {

            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getStatusListCus());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called CustomerMgtAction :View");

        } catch (Exception ex) {
            addActionError("CustomerMgtAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called CustomerMgtAction : update");
        String retType = "message";

//        BufferedImage readImage = null;
//        File destFile;
//        ServletContext context = ServletActionContext.getServletContext();
//        String destPath = context.getRealPath("/resouces/images");

        try {
            if (inputBean.getUserId() != null && !inputBean.getUserId().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    CustomerMgtDAO dao = new CustomerMgtDAO();

                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.CUS_MGT_PAGE, SectionVarlist.CUSTOMERMANAGEMENT, "User id " + inputBean.getUserId() + " updated", null, null, null);

                    message = dao.updateCustomer(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("CustomerMgtAction " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
//
//                    destFile = new File(destPath, inputBean.getOwnImageFileName() + ".jpg");
//                    FileUtils.copyFile(inputBean.getOwnImage(), destFile);
//                    readImage = ImageIO.read(new File(destPath, inputBean.getOwnImageFileName() + ".jpg"));
//
//                    int height = readImage.getHeight();
//                    int width = readImage.getWidth();
//
//                    System.out.println("height --- " + height);
//                    System.out.println("width --- " + width);
//
//                    System.err.println(height + " " + width);
//                    if (height != 92) {
//                        message = "Logo height should be 92 pixels";
//                    }
//                    if (width > 92) {
//                        message = "Logo width should be less than 280 pixels";
//                    }
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(MessageVarlist.COMMON_NOT_EXISTS);
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("CustomerMgtAction " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String find() throws Exception {
        System.out.println("called CustomerMgtAction : find");
        CustomerMgtDAO dao = new CustomerMgtDAO();
        MobUser mu = dao.findMobUserById(32);

        inputBean.setEditOwnImage(mu.getImage());
        return "list";
    }

    public String getImageLogo(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isImageLogo(file);
        }
        return msgEx;
    }

    public String detail() {
//        System.out.println("called CustomerMgtAction : detail");
//        MobUser tt = null;
//        try {
//            if (inputBean.getUserId()!= null && !inputBean.getUserId().isEmpty()) {
//
//                CustomerMgtDAO dao = new CustomerMgtDAO();
//                CommonDAO commonDAO = new CommonDAO();
//                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
//
//                tt = dao.findCustomerById(inputBean.getUserId());
//
//                inputBean.setTransactiontypecode(tt.getTypecode());
//                inputBean.setDescription(tt.getDescription());
//                inputBean.setStatus(tt.getStatus().getStatuscode());
//
//                try {
//                    inputBean.setDescription_receiver(tt.getDescriptionReceiver());
//                } catch (Exception e) {
//                    inputBean.setDescription_receiver("");
//                }
//                inputBean.setOldvalue(inputBean.getTransactiontypecode() + "|" + inputBean.getDescription() + "|" + inputBean.getStatus());
//
//            } else {
//                inputBean.setMessage("Empty TransactionType id.");
//            }
//        } catch (Exception ex) {
//            inputBean.setMessage("TransactionType id  " + MessageVarlist.COMMON_ERROR_PROCESS);
//            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return "detail";

    }

    public String ViewPopup() {
        String result = "viewpopup";
        System.out.println("called CustomerMgtAction : ViewPopup");
        try {
            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getStatusListCus());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called CustomerMgtAction :viewpopup");

        } catch (Exception ex) {
            addActionError("CustomerMgtAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Add() {
        System.out.println("called CustomerMgtAction : Add");
        String result = "message";
//        try {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            TransactionTypeDAO dao = new TransactionTypeDAO();
//            String message = this.validateInputs();
//
//            if (message.isEmpty()) {
//
//                String newV = inputBean.getTransactiontypecode() + "|"
//                        + inputBean.getDescription() + "|"
//                        + inputBean.getStatus() + "|";
//
//                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.TXN_TYPE_MGT_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Transaction type code " + inputBean.getTransactiontypecode() + " added", null, null, newV);
//                message = dao.insertTransactionType(inputBean, audit);
//
//                if (message.isEmpty()) {
//                    addActionMessage("Transaction type " + MessageVarlist.COMMON_SUCC_INSERT);
//                } else {
//                    addActionError(message);
//                }
//            } else {
//                addActionError(message);
//            }
//
//        } catch (Exception ex) {
//            addActionError("Transaction type " + MessageVarlist.COMMON_ERROR_PROCESS);
//            Logger.getLogger(TransactionTypeAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return result;
    }

    private String validateInputs() {
        String message = "";
        if (inputBean.getUserId() == null || inputBean.getUserId().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_USERID;
        }
        if (inputBean.getFirstName() == null || inputBean.getFirstName().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_FIRSTNAME;
        }
        if (inputBean.getLastName() == null || inputBean.getLastName().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_LASTNAME;
        }
        if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_EMAIL;
        }
        if (inputBean.getNic() == null || inputBean.getNic().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_NIC;
        }
        if (inputBean.getAddress() == null || inputBean.getAddress().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_ADDRESS;
        }
        if (inputBean.getArea() == null || inputBean.getArea().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_AREA;
        }
        if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_STATUS;
        }

        String messageImg = this.getImageLogo(this.inputBean.getOwnImageFileName());
        if (!messageImg.isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_USERIMG;
        }

        messageImg = this.getImageLogo(this.inputBean.getPrImageFileName());
        if (!messageImg.isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_POLICEREPORT;
        }

        messageImg = this.getImageLogo(this.inputBean.getBcImageFileName());
        if (!messageImg.isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_BIRTHCERT;
        }

        messageImg = this.getImageLogo(this.inputBean.getqImageFileName());
        if (!messageImg.isEmpty()) {
            message = MessageVarlist.CUSTOMER_MGT_EMPTY_QUALIFAYNOTE;
        }

        return message;
    }

    public String List() {
        System.out.println("called CustomerMgtAction: List");
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
            CustomerMgtDAO dao = new CustomerMgtDAO();
            List<CustomerMgtBean> dataList = dao.getSearchList(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Mobile no", inputBean.getMobileSearch())
                        + checkEmptyorNullString("NIC", inputBean.getNicSearch())
                        + checkEmptyorNullString("First Name", inputBean.getFnameSearch())
                        + checkEmptyorNullString("Lastname", inputBean.getLnameSearch())
                        + checkEmptyorNullString("Email", inputBean.getEmailSearch())
                        + checkEmptyorNullString("Status", inputBean.getStatusSearch())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.CUS_MGT_PAGE, SectionVarlist.CUSTOMERMANAGEMENT, "Transaction type search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" CustomerMgt " + MessageVarlist.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

    public String Find() {
        System.out.println("called CustomerMgtAction : Find");
        CustomerMgtBean tt = null;
//        try {
//            if (inputBean.getUserId()!= null && !inputBean.getUserId().isEmpty()) {
//
//                CustomerMgtDAO dao = new CustomerMgtDAO();
//
//                tt = dao.findCustomerById(inputBean.getUserId());
//
//                inputBean.setTransactiontypecode(tt.getTypecode());
//                inputBean.setDescription(tt.getDescription());
//                inputBean.setStatus(tt.getStatus().getStatuscode());
//
//                try {
//                    inputBean.setDescription_receiver(tt.getDescriptionReceiver());
//                } catch (Exception e) {
//                    inputBean.setDescription_receiver("");
//                }
//
//                inputBean.setOldvalue(inputBean.getTransactiontypecode()
//                        + "|" + inputBean.getDescription()
//                        + "|" + inputBean.getStatus()
//                );
//
//            } else {
//                inputBean.setMessage("Empty userid.");
//            }
//        } catch (Exception ex) {
//            inputBean.setMessage("CustomerMgtAction  " + MessageVarlist.COMMON_ERROR_PROCESS);
//            Logger.getLogger(CustomerMgtDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return "find";

    }

    public String Delete() {
        System.out.println("called CustomerMgtDAO : Delete");
        String message = null;
        String retType = "delete";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CustomerMgtDAO dao = new CustomerMgtDAO();
            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.DELETE_TASK, PageVarlist.CUS_MGT_PAGE, SectionVarlist.CUSTOMERMANAGEMENT, "Customer id " + inputBean.getUserId() + " deactivated", null);
            message = dao.deleteCustomer(inputBean, audit);
            if (message.isEmpty()) {
                message = "Transaction type " + MessageVarlist.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(CustomerMgtDAO.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

}
