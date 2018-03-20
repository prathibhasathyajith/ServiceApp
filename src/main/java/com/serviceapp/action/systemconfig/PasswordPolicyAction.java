/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.PasswordPolicyInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.dao.systemconfig.PasswordPolicyDAO;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class PasswordPolicyAction extends ActionSupport implements ModelDriven<Object> {

    PasswordPolicyInputBean inputBean = new PasswordPolicyInputBean();

    @Override
    public String execute() throws Exception {
        System.out.println("called PasswordPolicyAction : execute");
        return SUCCESS;
    }

    public Object getModel() {
        return inputBean;
    }

    public String view() {

        String result = "view";
        try {

            CommonDAO dao = new CommonDAO();
//                inputBean.setStatusList(dao.getDefultStatusList(CommonVarList.STATUS_CATEGORY_GENERAL));

            this.loaddetails();
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called PasswordPolicyAction : view");

        } catch (Exception ex) {
            addActionError("Password policy action " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void loaddetails() throws Exception {
        PasswordPolicyDAO dao = new PasswordPolicyDAO();
        Passwordpolicy passwordpolicy = dao.getPasswordPolicyDetails();
        inputBean.setPolicyid(true);

        inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
        inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
        inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
        inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
        inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
        inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
        inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
        inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
        inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
        inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
        inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
        inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
        inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
        inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

        inputBean.setOldvalue(
                inputBean.getPasswordpolicyid() + "|"
                + inputBean.getMinimumlength() + "|"
                + inputBean.getMaximumlength() + "|"
                + inputBean.getMinimumspecialcharacters() + "|"
                + inputBean.getMinimumuppercasecharacters() + "|"
                + inputBean.getMinimumlowercasecharacters() + "|"
                + inputBean.getMinimumnumericalcharacters() + "|"
                + inputBean.getRepeatcharactersallow() + "|"
                + inputBean.getPasswordexpiryperiod() + "|"
                + inputBean.getNoofhistorypassword() + "|"
                + inputBean.getMinimumpasswordchangeperiod() + "|"
                + inputBean.getIdleaccountexpiryperiod() + "|"
                + inputBean.getNoofinvalidloginattempt()
        );
    }

    public String find() {
        System.out.println("called PasswordPolicyAction: Find");
        Passwordpolicy passwordpolicy = new Passwordpolicy();
        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                PasswordPolicyDAO dao = new PasswordPolicyDAO();

                passwordpolicy = dao.findPasswordPolicyById(inputBean.getPasswordpolicyid());

                inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
                inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
                inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
                inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
                inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
                inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
                inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
                inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
                inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
                inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
                inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
                inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
                inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
                inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "find";

    }

    public String add() {
        System.out.println("called PasswordPolicyAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            PasswordPolicyDAO dao = new PasswordPolicyDAO();
            String message = this.validateInputs();

            if (message.isEmpty()) {

                String newV = inputBean.getPasswordpolicyid() + "|"
                        + inputBean.getMinimumlength() + "|"
                        + inputBean.getMaximumlength() + "|"
                        + inputBean.getMinimumspecialcharacters() + "|"
                        + inputBean.getMinimumuppercasecharacters() + "|"
                        + inputBean.getMinimumlowercasecharacters() + "|"
                        + inputBean.getMinimumnumericalcharacters() + "|"
                        + inputBean.getRepeatcharactersallow() + "|"
                        + inputBean.getPasswordexpiryperiod() + "|"
                        + inputBean.getNoofhistorypassword() + "|"
                        + inputBean.getMinimumpasswordchangeperiod() + "|"
                        + inputBean.getIdleaccountexpiryperiod() + "|"
                        + inputBean.getNoofinvalidloginattempt();
                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.PASSWORD_POLICY, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Password policy details added", null, null, newV);

                message = dao.insertPasswordPolicy(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Password policy  " + MessageVarlist.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("Password policy " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called PasswordPolicyAction : update");
        String retType = "message";

        try {
            if (inputBean.getPasswordpolicyid() != null && !inputBean.getPasswordpolicyid().isEmpty()) {

                String message = this.validateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();
                    PasswordPolicyDAO dao = new PasswordPolicyDAO();

                    String newV = inputBean.getPasswordpolicyid() + "|"
                            + inputBean.getMinimumlength() + "|"
                            + inputBean.getMaximumlength() + "|"
                            + inputBean.getMinimumspecialcharacters() + "|"
                            + inputBean.getMinimumuppercasecharacters() + "|"
                            + inputBean.getMinimumlowercasecharacters() + "|"
                            + inputBean.getMinimumnumericalcharacters() + "|"
                            + inputBean.getRepeatcharactersallow() + "|"
                            + inputBean.getPasswordexpiryperiod() + "|"
                            + inputBean.getNoofhistorypassword() + "|"
                            + inputBean.getMinimumpasswordchangeperiod() + "|"
                            + inputBean.getIdleaccountexpiryperiod() + "|"
                            + inputBean.getNoofinvalidloginattempt();

                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK,
                            PageVarlist.PASSWORD_POLICY, SectionVarlist.SYSTEMCONFIGMANAGEMENT,
                            "Password policy details updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updatePasswordPolicy(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("Password policy " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Password policy " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateInputs() {
        String message = "";

        if (inputBean.getPasswordpolicyid() == null || inputBean.getPasswordpolicyid().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_POLICYID_EMPTY;
        } else if (inputBean.getMinimumlength() == null || inputBean.getMinimumlength().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MINLEN_EMPTY;
        } else if (inputBean.getMaximumlength() == null || inputBean.getMaximumlength().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MAXLEN_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumlength()) >= Integer.parseInt(inputBean.getMaximumlength())) {
            message = MessageVarlist.PASSPOLICY_MIN_MAX_LENGHT_DIFF;
        } else if (inputBean.getMinimumspecialcharacters() == null || inputBean.getMinimumspecialcharacters().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_SPECCHARS_EMPTY;
        } else if (Integer.parseInt(inputBean.getMinimumspecialcharacters()) > 6) {
            message = MessageVarlist.PASSPOLICY_SPECCHARS_SHOULD_BE_LESS + "7";
        } else if (inputBean.getMinimumuppercasecharacters() == null || inputBean.getMinimumuppercasecharacters().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MINUPPER_EMPTY;
        } else if (inputBean.getMinimumlowercasecharacters() == null || inputBean.getMinimumlowercasecharacters().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MINLOWER_EMPTY;
        } else if (inputBean.getMinimumnumericalcharacters() == null || inputBean.getMinimumnumericalcharacters().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MINNUM_EMPTY;

        } else if (inputBean.getRepeatcharactersallow() == null || inputBean.getRepeatcharactersallow().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_REPEATE_CHARACTERS_EMPTY;
        } else if (Integer.parseInt(inputBean.getRepeatcharactersallow()) == 0) {
            message = MessageVarlist.PASSPOLICY_REPEATE_CHARACTERS_ZERO;
        } //        else if (inputBean.getInitialpasswordexpirystatus() == null || inputBean.getInitialpasswordexpirystatus().isEmpty()) {
        //            message = MessageVarList.PASSPOLICY_INIT_PASSWORD_EXPIRY_STATUS_EMPTY;
        //        } 
        else if (inputBean.getPasswordexpiryperiod() == null || inputBean.getPasswordexpiryperiod().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_EMPTY;
        } else if (inputBean.getMinimumpasswordchangeperiod() == null || inputBean.getMinimumpasswordchangeperiod().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_MIN_PASSWORD_CHANGE_PERIOD_EMPTY;
        } else if (inputBean.getNoofhistorypassword() == null || inputBean.getNoofhistorypassword().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_NO_OF_HISTORY_PASSWORD_EMPTY;
        } else if (inputBean.getIdleaccountexpiryperiod() == null || inputBean.getIdleaccountexpiryperiod().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_EMPTY;
        } else if (inputBean.getNoofinvalidloginattempt() == null || inputBean.getNoofinvalidloginattempt().isEmpty()) {
            message = MessageVarlist.PASSPOLICY_NO_OF_INVALID_LOGIN_ATTEMPTS_EMPTY;
        }

        if (inputBean.getMinimumlowercasecharacters() != null && !inputBean.getMinimumlowercasecharacters().isEmpty()
                && inputBean.getMinimumnumericalcharacters() != null && !inputBean.getMinimumnumericalcharacters().isEmpty()
                && inputBean.getMinimumspecialcharacters() != null && !inputBean.getMinimumspecialcharacters().isEmpty()
                && inputBean.getMinimumuppercasecharacters() != null && !inputBean.getMinimumuppercasecharacters().isEmpty()) {

            Integer minLower = Integer.parseInt(inputBean.getMinimumlowercasecharacters());
            Integer minNumerical = Integer.parseInt(inputBean.getMinimumnumericalcharacters());
            Integer minSpecial = Integer.parseInt(inputBean.getMinimumspecialcharacters());
            Integer minUpper = Integer.parseInt(inputBean.getMinimumuppercasecharacters());
//        Integer minVal=Integer.parseInt(inputBean.get());

            Integer minLength = minLower + minNumerical + minSpecial + minUpper;
//            Integer maxLength = minLength + 3;
            Integer maxLength = minLength;

            System.err.println("minLength " + minLength);
            System.err.println("maxLength " + maxLength);
            try {

                if (Integer.parseInt(inputBean.getMinimumlength()) < minLength) { //4
                    if (message.equals("")) {
                        message = MessageVarlist.PASSPOLICY_MINLEN_INVALID + (minLength);
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarlist.PASSPOLICY_MINLEN_INVALID + (minLength);
                }

            }
            try {
                if (Integer.parseInt(inputBean.getMaximumlength()) <= maxLength) { // 10 //12 now    11>12
                    if (message.equals("")) {
                        message = MessageVarlist.PASSPOLICY_MAXLEN_INVALID + (maxLength);
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarlist.PASSPOLICY_MAXLEN_INVALID + (maxLength);
                }
            }
            //check noofhistorypassword is 1(one) or above
            try {
                if (Integer.parseInt(inputBean.getNoofhistorypassword()) <= 0) {
                    if (message.equals("")) {
                        message = MessageVarlist.PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarlist.PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID;
                }

            }
            //check Password Expiry Period is 10 or above
            try {
                if (Integer.parseInt(inputBean.getPasswordexpiryperiod()) < 1) {
                    if (message.equals("")) {
                        message = MessageVarlist.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarlist.PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID;
                }

            }
            //check Idle Account Expiry Period is 10 or above
            try {
                if (Integer.parseInt(inputBean.getIdleaccountexpiryperiod()) < 1) {
                    if (message.equals("")) {
                        message = MessageVarlist.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID;
                    }
                }
            } catch (Exception e) {
                if (message.equals("")) {
                    message = MessageVarlist.PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID;
                }

            }

        }
        return message;
    }

    public String reset() {
        System.out.println("called PasswordPolicyAction: Reset");

        Passwordpolicy passwordpolicy;
        PasswordPolicyDAO dao = new PasswordPolicyDAO();
        try {
            if (dao.isExistPasswordPolicy()) {

                passwordpolicy = dao.getPasswordPolicyDetails();
                inputBean.setPpbean(passwordpolicy);
                System.err.println(passwordpolicy.getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");
                System.err.println(inputBean.getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");
                System.err.println(inputBean.getPpbean().getPasswordexpiryperiod() + "<<<<<<<<<<<<<<");

                inputBean.setOldvalue(
                        inputBean.getPpbean().getPasswordpolicyid() + "|"
                        + inputBean.getPpbean().getMinimumlength() + "|"
                        + inputBean.getPpbean().getMaximumlength() + "|"
                        + inputBean.getPpbean().getMinimumspecialcharacters() + "|"
                        + inputBean.getPpbean().getMinimumuppercasecharacters() + "|"
                        + inputBean.getPpbean().getMinimumlowercasecharacters() + "|"
                        + inputBean.getPpbean().getMinimumnumericalcharacters() + "|"
                        + inputBean.getPpbean().getRepeatcharactersallow() + "|"
                        + inputBean.getPpbean().getPasswordexpiryperiod() + "|"
                        + inputBean.getPpbean().getNoofhistorypassword() + "|"
                        + inputBean.getPpbean().getMinimumpasswordchangeperiod() + "|"
                        + inputBean.getPpbean().getIdleaccountexpiryperiod() + "|"
                        + inputBean.getPpbean().getNoofinvalidloginattempt()
                );

                System.out.println("------------------- " + inputBean.getOldvalue());

                inputBean.setPasswordpolicyid(String.valueOf(passwordpolicy.getPasswordpolicyid()));
                inputBean.setMinimumlength(String.valueOf(passwordpolicy.getMinimumlength()));
                inputBean.setMaximumlength(String.valueOf(passwordpolicy.getMaximumlength()));
                inputBean.setMinimumspecialcharacters(String.valueOf(passwordpolicy.getMinimumspecialcharacters()));
                inputBean.setMinimumuppercasecharacters(String.valueOf(passwordpolicy.getMinimumuppercasecharacters()));
                inputBean.setMinimumnumericalcharacters(String.valueOf(passwordpolicy.getMinimumnumericalcharacters()));
                inputBean.setMinimumlowercasecharacters(String.valueOf(passwordpolicy.getMinimumlowercasecharacters()));
                inputBean.setRepeatcharactersallow(String.valueOf(passwordpolicy.getRepeatcharactersallow()));
                inputBean.setInitialpasswordexpirystatus(String.valueOf(passwordpolicy.getInitialpasswordexpirystatus()));
                inputBean.setPasswordexpiryperiod(String.valueOf(passwordpolicy.getPasswordexpiryperiod()));
                inputBean.setNoofhistorypassword(String.valueOf(passwordpolicy.getNoofhistorypassword()));
                inputBean.setMinimumpasswordchangeperiod(String.valueOf(passwordpolicy.getMinimumpasswordchangeperiod()));
                inputBean.setIdleaccountexpiryperiod(String.valueOf(passwordpolicy.getIdleaccountexpiryperiod()));
                inputBean.setNoofinvalidloginattempt(String.valueOf(passwordpolicy.getNoofinvalidloginattempt()));

            } else {
                inputBean.setMessage("Empty password policy id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("Password policy " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(PasswordPolicyAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "reset";

    }
}
