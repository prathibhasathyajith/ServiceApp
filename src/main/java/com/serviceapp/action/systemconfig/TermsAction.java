/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.TermsInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.SessionVarlist;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class TermsAction extends ActionSupport implements ModelDriven<Object>{
    
    TermsInputBean inputBean = new TermsInputBean();
    
    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called TermsAction : execute");
        return SUCCESS;
    }
    
    public String view() {

        String result = "view";
        try {
          
            CommonDAO dao = new CommonDAO();
          
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called TermsAction :View");

        } catch (Exception ex) {
            addActionError("Terms  " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
