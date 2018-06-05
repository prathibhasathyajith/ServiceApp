/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.service;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.service.ServiceCancelReasonBean;
import com.serviceapp.bean.service.ServiceCancelReasonInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.cusmanagement.SuggestedUserDAO;
import com.serviceapp.dao.service.ServiceCancelReasonDAO;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
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
public class ServiceCancelReasonAction extends ActionSupport implements ModelDriven<Object> {

    ServiceCancelReasonInputBean inputBean = new ServiceCancelReasonInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called ServiceCancelReasonAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {

            CommonDAO dao = new CommonDAO();
            ServiceCancelReasonDAO sdao = new ServiceCancelReasonDAO();
            

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called ServiceCancelReasonAction :View");

        } catch (Exception ex) {
            addActionError("ServiceCancelReasonAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ServiceCancelReasonAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String list() {
        System.out.println("called ServiceCancelReasonAction: List");
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
            ServiceCancelReasonDAO dao = new ServiceCancelReasonDAO();
            List<ServiceCancelReasonBean> dataList = dao.getSearchList(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("From date", inputBean.getFdate())
                        + checkEmptyorNullString("To date", inputBean.getTdate())
                        + checkEmptyorNullString("Service ID", inputBean.getServiceId())
                        + checkEmptyorNullString("Initiated User", inputBean.getInitUser())
                        + checkEmptyorNullString("Reason", inputBean.getReason())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.SERVICE_CANCEL_REASON_PAGE, SectionVarlist.SERVICE_MGT, "Service cancel reason search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(ServiceCancelReasonAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" Service Cancel Reason  " + MessageVarlist.COMMON_ERROR_PROCESS);
        }
        return "list";
    }

}
