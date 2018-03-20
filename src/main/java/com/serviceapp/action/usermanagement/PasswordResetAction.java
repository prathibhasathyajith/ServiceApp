/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.usermanagement;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.usermanagement.PasswordResetInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.dao.systemconfig.PasswordPolicyDAO;
import com.serviceapp.dao.usermanagement.PasswordResetDAO;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class PasswordResetAction extends ActionSupport implements ModelDriven<Object>{
    private PasswordResetInputBean inputBean = new PasswordResetInputBean();
    Passwordpolicy passPolicy;

    public Object getModel() {
        return inputBean;
    }

    public String execute() {
        System.out.println("called passwordresetAction : execute");
        return SUCCESS;
    }
    
    //view server operating houts
    public String View() {
        System.out.println("called PasswordResetAction :View");
        String retType = "view";
        try {
            

                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession(false);

                Systemuser user = (Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);

                inputBean.setUsername(user.getUsername());
                inputBean.setHusername(user.getUsername());

                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Passwordpolicy passPo = passPolicydao.getPasswordPolicyDetails();
                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPo));

           
        } catch (Exception ex) {
            addActionError(" Password reset " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordResetAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    //update password
    public String Update() {
        System.out.println("called PasswordResetAction :Update");
        String retType = "message";
        PasswordResetDAO dao = new PasswordResetDAO();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            String message = this.validateInputs();

            if (message.isEmpty()) {

                Systemuser users = dao.findUserById(inputBean.getHusername());

                if (users.getPassword().trim().equals(CommonDAO.makeHash(inputBean.getCurrpwd().trim()))) {
                    Systemaudit audit = CommonDAO.makeAudittrace(request, users, TaskVarlist.UPDATE_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Password changed ", null);
                    inputBean.setRenewpwd(CommonDAO.makeHash(inputBean.getRenewpwd().trim()));
                    inputBean.setCurrpwd(CommonDAO.makeHash(inputBean.getCurrpwd().trim()));
                    message = dao.UpdatePasswordReset(inputBean, audit, passPolicy);

                    if (message.isEmpty()) {
                        retType = "resetpassword";
                        session.setAttribute("intercept", "PASSWORD_CHANGE_SUCCESS");
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(MessageVarlist.PASSWORDRESET_INVALID_CURR_PWD);
                }
            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            addActionError(" Password reset " + MessageVarlist.COMMON_ERROR_UPDATE);
            Logger.getLogger(PasswordResetAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    //validation of the input fields
    private String validateInputs() throws Exception {
        String message = "";
        try {
            if (inputBean.getCurrpwd() == null || inputBean.getCurrpwd().trim().isEmpty()) {
                message = MessageVarlist.PASSWORDRESET_EMPTY_PASSWORD;
            } else if (inputBean.getNewpwd() == null || inputBean.getNewpwd().trim().isEmpty()) {
                message = MessageVarlist.PASSWORDRESET_EMPTY_NEW_PASSWORD;
            } else if (inputBean.getRenewpwd() == null || inputBean.getRenewpwd().trim().isEmpty()) {
                message = MessageVarlist.PASSWORDRESET_EMPTY_COM_PASSWORD;
            } else if (!inputBean.getNewpwd().trim().contentEquals(inputBean.getRenewpwd().trim())) {
                message = MessageVarlist.PASSWORDRESET_MATCH_PASSWORD;
            }
            if (inputBean.getNewpwd().trim().equals(inputBean.getRenewpwd().trim())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getNewpwd().trim());
                if (message.equals("")) {
                    message = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }
    
    private String checkPolicy(String password) throws Exception {
        String errorMsg = "";
        try {
            PasswordPolicyDAO dao = new PasswordPolicyDAO();

            passPolicy = dao.getPasswordPolicyDetails();
            if (passPolicy != null) {

                String msg = this.CheckPasswordValidity(password, passPolicy);

                if (!msg.equals("")) {
                    errorMsg = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return errorMsg;
    }

    public String CheckPasswordValidity(String password, Passwordpolicy bean) throws Exception {
        String msg = "";
        boolean flag = true;
        Set<Character> set = new HashSet<Character>();;
        List<Character> list = new ArrayList<Character>();
        int x = 0;

        try {
            if (password.length() < bean.getMinimumlength()) {
                flag = false;
                msg = MessageVarlist.SYSUSER_PASSWORD_TOO_SHORT + " " + bean.getMinimumlength().toString();
            } else if (password.length() > bean.getMaximumlength()) {
                flag = false;
                msg = MessageVarlist.SYSUSER_PASSWORD_TOO_LARGE + " " + bean.getMaximumlength();
            }

            if (flag) {
                int digits = 0;
                int upper = 0;
                int lower = 0;
                int special = 0;

                for (int i = 0; i < password.length(); i++) {
                    char c = password.charAt(i);
                    list.add(c);
                    if (Character.isDigit(c)) {
                        digits++;
                    } else if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    } else {
                        special++;
                    }
                }

                if (lower < bean.getMinimumlowercasecharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS + " " + bean.getMinimumlowercasecharacters();
                    flag = false;
                } else if (upper < bean.getMinimumuppercasecharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS + " " + bean.getMinimumuppercasecharacters();
                    flag = false;
                } else if (digits < bean.getMinimumnumericalcharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS + " " + bean.getMinimumnumericalcharacters();
                    flag = false;
                } else if (special < bean.getMinimumspecialcharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS + " " + bean.getMinimumspecialcharacters();
                    flag = false;
                }
            }

            if (flag) {
                do {
                    Character[] rechar = list.toArray(new Character[0]);
                    list.clear();
                    set.clear();
                    for (Character c : rechar) {
                        if (!set.add(c)) {
                            list.add(c);
                        }
                    }
                    x++;
                    if (bean.getRepeatcharactersallow() < x) {
                        msg = MessageVarlist.SYSUSER_PASSWORD_MORE_CHAR_REPEAT + " " + bean.getRepeatcharactersallow();
                        break;
                    }

                } while (!list.isEmpty());

            }

        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    public String LogOutUser() {
        System.out.println("called PasswordResetAction : LogOutUser");
        return "logoutuser";
    }

}
