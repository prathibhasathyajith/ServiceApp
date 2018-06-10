/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.service;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.action.usermanagement.SystemuserAction;
import com.serviceapp.bean.service.ServiceCategoryBean;
import com.serviceapp.bean.service.ServiceCategoryInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.common.dao.Validation;
import com.serviceapp.dao.service.ServiceCategoryDAO;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.dao.systemconfig.PasswordPolicyDAO;
import com.serviceapp.dao.usermanagement.SystemuserDAO;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Roles;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.OracleMessage;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class ServiceCategoryAction extends ActionSupport implements ModelDriven<Object> {

    ServiceCategoryInputBean inputBean = new ServiceCategoryInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called ServiceCategoryAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
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

            System.out.println("called ServiceCategoryAction :View");

        } catch (Exception ex) {
            addActionError("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {
        System.out.println("called ServiceCategoryAction: list");
        try {

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;

            String sortIndex = "";
            String sortOrder = "";
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                sortIndex = inputBean.getSidx();
                sortOrder = inputBean.getSord();
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            ServiceCategoryDAO dao = new ServiceCategoryDAO();
            List<ServiceCategoryBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Service Type", inputBean.getRoleType())
                        + checkEmptyorNullString("Description", inputBean.getDescription())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.SERVICE_CATEGORY_PAGE, SectionVarlist.SERVICE_MGT, "Service category search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarlist.COMMON_ERROR_PROCESS + " Service Category Action");
        }
        return "list";
    }

    public String viewpopup() {
        String result = "viewpopup";
        System.out.println("called ServiceCategoryAction : ViewPopup");
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

        } catch (Exception e) {
            addActionError("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String detail() {
        System.out.println("called ServiceCategoryAction: detail");
        Roles role = new Roles();

        try {
            if (inputBean.getRoleType() != null && !inputBean.getRoleType().isEmpty()) {

                ServiceCategoryDAO dao = new ServiceCategoryDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

                role = dao.getServiceCatByRole(inputBean.getRoleType());

                try {
                    inputBean.setRoleType(role.getRoleType());
                } catch (NullPointerException e) {
                    inputBean.setRoleType("");
                }

                try {
                    inputBean.setDescription(role.getDescription());
                } catch (NullPointerException e) {
                    inputBean.setDescription("");
                }

                try {
                    inputBean.setStatus(role.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                inputBean.setOldvalue(inputBean.getRoleType() + "|" + inputBean.getDescription() + "|"
                        + inputBean.getStatus());

            } else {
                inputBean.setMessage("Empty role.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }
    
    public String find() {
        System.out.println("called ServiceCategoryAction: find");
        Roles role = new Roles();
        try {
            if (inputBean.getRoleType()!= null && !inputBean.getRoleType().isEmpty()) {

                ServiceCategoryDAO dao = new ServiceCategoryDAO();

                role = dao.getServiceCatByRole(inputBean.getRoleType());

                try {
                    inputBean.setRoleType(role.getRoleType());
                } catch (NullPointerException e) {
                    inputBean.setRoleType("");
                }

                try {
                    inputBean.setDescription(role.getDescription());
                } catch (NullPointerException e) {
                    inputBean.setDescription("");
                }

                try {
                    inputBean.setStatus(role.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                inputBean.setOldvalue(inputBean.getRoleType() + "|" + inputBean.getDescription() + "|"
                        + inputBean.getStatus());

            } else {
                inputBean.setMessage("Empty role");

            }
        } catch (Exception ex) {
            inputBean.setMessage("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";
    }
    
    public String add() {
        System.out.println("called ServiceCategoryAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            ServiceCategoryDAO dao = new ServiceCategoryDAO();

            

            String message = this.validateInputs();

            if (message.isEmpty()) {

                String newV = inputBean.getRoleType() + "|" + inputBean.getDescription() + "|"
                        + inputBean.getStatus();

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.SERVICE_CATEGORY_PAGE, SectionVarlist.SERVICE_MGT, "Service category added", null, null, newV);
                message = dao.insertServiceCategory(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Service category " + MessageVarlist.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called ServiceCategoryAction : update");
        String retType = "message";

        try {
            if (inputBean.getRoleType()!= null && !inputBean.getRoleType().isEmpty()) {

                //set role get in hidden fileds
                inputBean.setRoleType(inputBean.getRoleType());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    
                    ServiceCategoryDAO dao = new ServiceCategoryDAO();

                    String newV = inputBean.getRoleType()+ "|"
                            + inputBean.getDescription()+ "|"
                            + inputBean.getStatus();

                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.SERVICE_CATEGORY_PAGE, SectionVarlist.SERVICE_MGT, "Service category updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updateServiceCategory(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Service category " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("ServiceCategoryAction " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
//        must delete from all foreign keys from all tables
        System.out.println("called ServiceCategoryAction : delete");
        String message = null;
        String retType = "delete";
        try {

            HttpServletRequest request = ServletActionContext.getRequest();

            ServiceCategoryDAO dao = new ServiceCategoryDAO();

            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.DELETE_TASK, PageVarlist.SERVICE_CATEGORY_PAGE, SectionVarlist.SERVICE_MGT, "Service category " + inputBean.getRoleType()+ " deleted", null);
            message = dao.deleteServiceCategory(inputBean, audit);
            if (message.isEmpty()) {
                message = "Service category " + MessageVarlist.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(ServiceCategoryAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";
        try {
            if (inputBean.getRoleType()== null || inputBean.getRoleType().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_ROLE;
            } else if (inputBean.getDescription()== null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_DES;
            } else if (inputBean.getStatus()== null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_STATUS;
            } 
            
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }

    private String validateUpdateInputs() {
        String message = "";
        try {
            if (inputBean.getRoleType()== null || inputBean.getRoleType().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_ROLE;
            } else if (inputBean.getDescription()== null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_DES;
            } else if (inputBean.getStatus()== null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.SERVICE_CATEGORY_EMPTY_STATUS;
            } 
            
        }catch(Exception e){
            System.out.println(e);
        }
        return message;
    }
}
