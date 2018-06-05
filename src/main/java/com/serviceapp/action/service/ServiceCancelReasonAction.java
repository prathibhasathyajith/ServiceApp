/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.service;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.service.ServiceCancelReasonInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.dao.service.ServiceCancelReasonDAO;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.SessionVarlist;
import java.util.logging.Level;
import java.util.logging.Logger;
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

}
