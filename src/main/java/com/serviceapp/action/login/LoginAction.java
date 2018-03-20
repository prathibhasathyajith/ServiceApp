/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.login;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.serviceapp.dao.login.LoginDAO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.login.LoginBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class LoginAction extends ActionSupport implements ModelDriven<Object> {

    LoginBean loginBean = new LoginBean();
    Systemuser sysUser = new Systemuser();
    Passwordpolicy passwordpolicy = null;
    Calendar cal2 = Calendar.getInstance();
    Calendar cal4 = Calendar.getInstance();
    String lastLoggedDate = "";
    String currentDate = "";

    @Override
    public Object getModel() {
        return loginBean;
    }

    public String execute() {
        System.out.println("called LoginAction : execute");
        return SUCCESS;
    }

    public String Check() {
        String result = "message";
        String warnmsg = "";
        try {
            String message = this.validateUser();
            if (message.isEmpty()) {

                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                if (sessionPrevious != null) {
                    sessionPrevious.invalidate();
                }
                //set user to the session
                HttpSession session = ServletActionContext.getRequest().getSession(true);
                session.setAttribute(SessionVarlist.SYSTEMUSER, sysUser);
                session.setAttribute(SessionVarlist.LAST_LOGGED_DATE, lastLoggedDate);
                session.setAttribute(SessionVarlist.CURRENT_DATE_TIME, currentDate);

                if (daysBetween(cal2, cal4) <= passwordpolicy.getMinimumpasswordchangeperiod()) {
                    if (daysBetween(cal2, cal4) == 0) {
                        warnmsg = MessageVarlist.COMMON_WARN_CHANGE_PASSWORD + "today.";
                    } else {
                        warnmsg = MessageVarlist.COMMON_WARN_CHANGE_PASSWORD + "in " + daysBetween(cal2, cal4) + " days.";

                    }
                    session.setAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD, warnmsg);
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 0);
                }

                if (Integer.parseInt(sysUser.getInitialloginstatus()) == 0) {
                    result = "firstlogin";
                } else if (daysBetween(cal2, cal4) == -1) {
                    result = "firstlogin";
                } else {
                    result = SUCCESS;
                }
                addActionMessage("Last logged date : " + lastLoggedDate);
            } else {
                loginBean.setErrormessage(message);
                addActionError(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateUser() {
        String message = "";
        try {
            LoginDAO loginDao = new LoginDAO();
            HttpServletRequest request = ServletActionContext.getRequest();

            if ((loginBean.getUsername() == null || loginBean.getUsername().isEmpty()) && (loginBean.getPassword() == null || loginBean.getPassword().isEmpty())) {
                message = MessageVarlist.LOGIN_EMPTY_USERNAME_PASSWORD;
            } else if (loginBean.getUsername() == null || loginBean.getUsername().isEmpty()) {
                message = MessageVarlist.LOGIN_EMPTY_USERNAME;
            } else if (loginBean.getPassword() == null || loginBean.getPassword().isEmpty()) {
                message = MessageVarlist.LOGIN_EMPTY_PASSWORD;
            } else {
                //check user validity from db
                try {
                    sysUser = loginDao.findUserbyUsername(loginBean.getUsername());

                    String hPass = CommonDAO.makeHash(loginBean.getPassword());

                    passwordpolicy = loginDao.findPasswordPolicy();
                    try {
                        //set last logged date and current date
                        lastLoggedDate = CommonDAO.getFormattedDateForLogin(sysUser.getLoggeddate());
                        Date date = new Date();
                        currentDate = CommonDAO.getFormattedDateForLogin(date);

                        Calendar cal3 = Calendar.getInstance();
                        cal3.setTime(sysUser.getLoggeddate());
                        cal4.setTime(sysUser.getExpirydate());

                        // get current date from DB                            
                        cal2.setTime(CommonDAO.getSystemDateLogin());
                        //cal2.setTime(new Date());

                        if (sysUser == null) {
                            message = MessageVarlist.LOGIN_INVALID;
                        } else if (!sysUser.getStatus().getStatuscode().equals(CommonVarlist.STATUS_ACTIVE)) {
                            message = MessageVarlist.LOGIN_DEACTIVE;
                        } else if (Integer.parseInt(sysUser.getNoofinvlidattempt()) >= passwordpolicy.getNoofinvalidloginattempt()) {
                            loginBean.setAttempts(Integer.parseInt(sysUser.getNoofinvlidattempt()));
                            loginBean.setStatus(CommonVarlist.STATUS_DEACTIVE);

                            Systemaudit audit = CommonDAO.makeAudittrace(request, sysUser, TaskVarlist.LOGIN_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Login de-activated", null);
                            loginDao.updateUser(loginBean, audit, false);
                            message = MessageVarlist.LOGIN_DEACTIVE;

                        } else if (daysBetween(cal3, cal2) >= passwordpolicy.getIdleaccountexpiryperiod()) {

                            loginBean.setAttempts(Integer.parseInt(sysUser.getNoofinvlidattempt()));
                            loginBean.setStatus(CommonVarlist.STATUS_DEACTIVE);

                            Systemaudit audit = CommonDAO.makeAudittrace(request, sysUser, TaskVarlist.LOGIN_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Login de-activated", null);
                            loginDao.updateUser(loginBean, audit, false);
                            message = MessageVarlist.LOGIN_DEACTIVE;

                        } else if (!hPass.equals(sysUser.getPassword())) {

                            if (sysUser.getNoofinvlidattempt() == null || Integer.parseInt(sysUser.getNoofinvlidattempt()) == 0) {
                                sysUser.setNoofinvlidattempt("0");
                            }
                            int attempts = Integer.parseInt(sysUser.getNoofinvlidattempt());
                            attempts++;
                            loginBean.setAttempts(attempts);
                            loginBean.setStatus(sysUser.getStatus().getStatuscode());

                            Systemaudit audit = CommonDAO.makeAudittrace(request, sysUser, TaskVarlist.LOGIN_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Login attempted ", null);
                            loginDao.updateUser(loginBean, audit, false);
                            message = MessageVarlist.LOGIN_ERROR_INVALID;

                        } else if (message.isEmpty()) {

                            loginBean.setAttempts(new Integer("0"));
                            loginBean.setStatus(sysUser.getStatus().getStatuscode());

                            Systemaudit audit = CommonDAO.makeAudittrace(request, sysUser, TaskVarlist.LOGIN_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Login successfully", null);
                            loginDao.updateUser(loginBean, audit, true);
                        }

                    } catch (Exception ex) {
                        message = MessageVarlist.LOGIN_ERROR_INVALID;
                    }
                } catch (Exception ex) {
                    message = MessageVarlist.LOGIN_ERROR_INVALID;
                }
            }
        } catch (Exception ex) {
            message = MessageVarlist.LOGIN_ERROR_LOAD;
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
    
    public String Logout() {
        String result = "message";
        System.out.println("called LoginAction : Logout");

        try {
            //invalidate session
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session != null) {
                //error messages
                if (loginBean.getMessage() != null && !loginBean.getMessage().isEmpty()) {
                    String message = loginBean.getMessage();
                    if (message.equals("error1")) {
                        loginBean.setErrormessage("Access denied. Please login again.");
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged in from another machine. Access denied.");
                        loginBean.setErrormessage("You have been logged in from another machine. Access denied.");
                    } else if (message.equals("error3")) {
                    } else if (message.equals("success1")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                        loginBean.setErrormessage("Your password changed successfully. Please login with the new password.");
                    }
                } else//if loginbean not set check for the session message
                {
                    if (session != null) {
                        String msg = String.valueOf(session.getAttribute("intercept"));
                        if (msg.equalsIgnoreCase("ERROR_ACCESS")) {
                            addActionError(MessageVarlist.ERROR_ACCESS);
                            loginBean.setErrormessage(MessageVarlist.ERROR_ACCESS);
                        } else if (msg.equals("ERROR_ACCESSPOINT")) {
                            addActionError(MessageVarlist.ERROR_ACCESSPOINT);
                            loginBean.setErrormessage(MessageVarlist.ERROR_ACCESSPOINT);
                        } else if (msg.equals("ERROR_USER_INFO")) {
                            addActionError(MessageVarlist.ERROR_USER_INFO);
                            loginBean.setErrormessage(MessageVarlist.ERROR_USER_INFO);
                        } else if (msg.equals("PASSWORD_CHANGE_SUCCESS")) {
                            loginBean.setErrormessage(MessageVarlist.PASSWORD_CHANGE_SUCCESS);
                            addActionMessage(MessageVarlist.PASSWORD_CHANGE_SUCCESS);
                        } else {
                            addActionError(MessageVarlist.ERROR_SESSION);
                            loginBean.setErrormessage(MessageVarlist.ERROR_SESSION);
                        }
                    }
                }

                sysUser = ((Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER));

                if (sysUser != null) {

                    LoginDAO loginDao = new LoginDAO();

                    Systemaudit audit = CommonDAO.makeAudittrace(ServletActionContext.getRequest(), sysUser, TaskVarlist.LOGOUT_TASK, PageVarlist.LOGIN_PAGE, SectionVarlist.DEFAULT_SECTION, "Log out successfully", null);
                    loginBean.setStatus(sysUser.getStatus().getStatuscode());
                    loginBean.setUsername(sysUser.getUsername());

                    loginDao.updateUser(loginBean, audit, false);
                }

                session.invalidate();

            }

        } catch (Exception ex) {
            Logger.getLogger(LoginAction.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = -1;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }


}
