/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.usermanagement;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.usermanagement.SystemuserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.SessionVarlist;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class SystemuserAction extends ActionSupport implements ModelDriven<Object>{
    SystemuserInputBean inputBean = new SystemuserInputBean();
    
    public Object getModel() {
        return inputBean;
    }
    
    public String view() {

        String result = "view";
        try {
          

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

                // set password expiry period (inserted)
//                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
//                Calendar cal = Calendar.getInstance();
//                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                cal.setTime(CommonDAO.getSystemDateLogin());
//                cal.add(Calendar.DAY_OF_MONTH, passPolicy.getPasswordexpiryperiod());

                // set expiry date to session
//                HttpServletRequest request = ServletActionContext.getRequest();
//                request.getSession(false).setAttribute(SessionVarlist.PASSWORD_EXPIRY_PERIOD, formatter.format(cal.getTime()));

//                inputBean.setExpirydate(formatter.format(cal.getTime()));

//                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));

           
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called SystemUserAction :view");

        } catch (Exception ex) {
            addActionError("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
