/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.usermanagement;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.usermanagement.SystemuserBean;
import com.serviceapp.bean.usermanagement.SystemuserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.common.dao.Validation;
import com.serviceapp.dao.systemconfig.PasswordPolicyDAO;
import com.serviceapp.dao.usermanagement.SystemuserDAO;
import com.serviceapp.mapping.Passwordpolicy;
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
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author prathibha_s
 */
public class SystemuserAction extends ActionSupport implements ModelDriven<Object> {

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

            System.out.println("called SystemuserAction :view");

        } catch (Exception ex) {
            addActionError("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String changepassword() {
        System.out.println("called SystemuserAction :changePassword");
        String retType = "changepassword";
        try {
            SystemuserDAO dao = new SystemuserDAO();

            inputBean.setUsername(inputBean.getUsername());
            inputBean.setHusername(inputBean.getUsername());

            PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
            Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
            inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));

        } catch (Exception ex) {
            addActionError(" Passsword Reset " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    public String updatechangepassword() {
        System.out.println("called SystemuserAction :updatechangepassword");
        String retType = "message";

        System.err.println(inputBean.getCnewpwd());
        System.err.println(inputBean.getCrenewpwd());
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String message = this.validateChangePasswordInputs();

            if (message.isEmpty()) {
                SystemuserDAO dao = new SystemuserDAO();
                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "Password of " + inputBean.getHusername() + " reset ", null);
                inputBean.setCrenewpwd(CommonDAO.makeHash(inputBean.getCrenewpwd().trim()));
                message = dao.updatePasswordReset(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage(MessageVarlist.RESET_PASSWORD_SUCCESS);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            addActionError(MessageVarlist.COMMON_ERROR_UPDATE + " Password Reset");
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retType;
    }

    public String list() {
        System.out.println("called SystemuserAction: list");
        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            Systemuser sysUser = ((Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER));

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

            SystemuserDAO dao = new SystemuserDAO();
//            List<SystemUserDataBean> dataList = dao.getSearchList(inputBean, rows, from, sortIndex, sortOrder);
            List<SystemuserBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy, sysUser.getUsername());

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("Username", inputBean.getUsername())
                        + checkEmptyorNullString("Full Name", inputBean.getFullname())
                        + checkEmptyorNullString("Service ID", inputBean.getServiceid())
                        + checkEmptyorNullString("NIC", inputBean.getNic())
                        + checkEmptyorNullString("Email", inputBean.getEmail())
                        + checkEmptyorNullString("Contact Number", inputBean.getContactno())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "User management search using " + searchParameters + " parameters ", null);
//                SystemAuditDAO sysdao = new SystemAuditDAO();
                CommonDAO sysdao = new CommonDAO();
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
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE,
                    null, e);
            addActionError(MessageVarlist.COMMON_ERROR_PROCESS
                    + " System User Action");
        }
        return "list";
    }

    public String add() {
        System.out.println("called SystemuserAction : add");
        String result = "message";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SystemuserDAO dao = new SystemuserDAO();

            HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
            Systemuser sysUser = (Systemuser) httpSession.getAttribute(SessionVarlist.SYSTEMUSER);

            System.err.println("user nam: " + inputBean.getUsername());
            System.err.println("pass wor: " + inputBean.getPassword());
            System.err.println("cof pass: " + inputBean.getConfirmpassword());

            String message = this.validateInputs();

            if (message.isEmpty()) {

                String serid_audit = "";
                String add1_audit = "";
//                String add2_audit = "";
                String city_audit = "";

                if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                    serid_audit = "--";
                } else {
                    serid_audit = inputBean.getServiceid();
                }
                if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                    add1_audit = "--";
                } else {
                    add1_audit = inputBean.getAddress1();
                }
//                if (inputBean.getAddress2() == null || inputBean.getAddress2().isEmpty()) {
//                    add2_audit = "--";
//                } else {
//                    add2_audit = inputBean.getAddress2();
//                }
                if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                    city_audit = "--";
                } else {
                    city_audit = inputBean.getCity();
                }

                String newV = inputBean.getUsername() + "|" + inputBean.getExpirydate() + "|"
                        + "|admin|" + inputBean.getStatus() + "|" + inputBean.getFullname()
                        //                        + "|" + inputBean.getDualauthuser() + "|" + inputBean.getStatus() + "|" + inputBean.getFullname()
                        + "|" + inputBean.getContactno() + "|" + inputBean.getEmail() + "|" + inputBean.getNic()
                        + "|" + inputBean.getDateofbirth() + "|" + serid_audit + "|" + add1_audit + "|" + city_audit;

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "System user added", null, null, newV);
                message = dao.insertSystemuser(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("System user " + MessageVarlist.COMMON_SUCC_INSERT);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called SystemuserAction : update");
        String retType = "message";

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                //set username get in hidden fileds
                inputBean.setUsername(inputBean.getUsername());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {

                    HttpServletRequest request = ServletActionContext.getRequest();

                    HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
                    Systemuser sysUser = (Systemuser) httpSession.getAttribute(SessionVarlist.SYSTEMUSER);

                    SystemuserDAO dao = new SystemuserDAO();

                    String serid_audit = "";
                    String add1_audit = "";
//                    String add2_audit = "";
                    String city_audit = "";

                    if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                        serid_audit = "--";
                    } else {
                        serid_audit = inputBean.getServiceid();
                    }
                    if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                        add1_audit = "--";
                    } else {
                        add1_audit = inputBean.getAddress1();
                    }
//                    if (inputBean.getAddress2() == null || inputBean.getAddress2().isEmpty()) {
//                        add2_audit = "--";
//                    } else {
//                        add2_audit = inputBean.getAddress2();
//                    }
                    if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                        city_audit = "--";
                    } else {
                        city_audit = inputBean.getCity();
                    }

                    String newV = inputBean.getUsername() + "|"
                            + inputBean.getFullname() + "|"
                            + inputBean.getNic() + "|"
                            + inputBean.getStatus() + "|"
                            + inputBean.getContactno() + "|"
                            + inputBean.getEmail() + "|"
                            + add1_audit + "|"
                            + city_audit + "|"
                            + serid_audit + "|"
                            + inputBean.getExpirydate() + "|"
                            + inputBean.getDateofbirth();

                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "System user updated", null, inputBean.getOldvalue(), newV);
                    message = dao.updateSystemuser(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("System user " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }

                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("System user " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String delete() {
        System.out.println("called SystemuserAction : delete");
        String message = null;
        String retType = "delete";
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            Systemuser cuUser = ((Systemuser) request.getSession(false).getAttribute(SessionVarlist.SYSTEMUSER));

            SystemuserDAO dao = new SystemuserDAO();

            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.DELETE_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "System user " + inputBean.getUsername() + " deleted", null);
            message = dao.deleteSystemuser(inputBean, cuUser, audit);
            if (message.isEmpty()) {
                message = "System user " + MessageVarlist.COMMON_SUCC_DELETE;
            }
            inputBean.setMessage(message);
        } catch (Exception e) {
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
//            inputBean.setMessage(MessageVarlist.COMMON_ERROR_DELETE);
        }
        return retType;
    }

//    public String findDualAuthUsers() {
//        System.out.println("called SystemuserAction : findDualAuthUsers");
//        try {
//
//            if (inputBean.getUserrole() != null && !inputBean.getUserrole().isEmpty()) {
//
//                SystemuserDAO dao = new SystemuserDAO();
//
//                int currUserLevel = dao.getCurrUsersUserLevel(inputBean.getUserrole());
//
//                dao.findUsersByUserRole(inputBean, currUserLevel);
//
//            } else {
//                inputBean.setMessage("");
//            }
//
//        } catch (Exception ex) {
//            inputBean.setMessage("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
//            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return "findDualAuthUsers";
//
//    }
    public String find() {
        System.out.println("called UserManagementAction: find");
        Systemuser user = new Systemuser();
        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemuserDAO dao = new SystemuserDAO();

                HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
                Systemuser sysUser = (Systemuser) httpSession.getAttribute(SessionVarlist.SYSTEMUSER);

                user = dao.getSystemuserByUserName(inputBean.getUsername());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    inputBean.setUsername(user.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }

                try {
                    inputBean.setExpirydate(sdf.format(user.getExpirydate()));
                } catch (NullPointerException e) {
                    inputBean.setExpirydate("");
                }

                try {
                    inputBean.setStatus(user.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setFullname(user.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }

                try {
                    inputBean.setAddress1(user.getAddress());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }

//                try {
//                    inputBean.setAddress2(user.getAddress2());
//                } catch (NullPointerException e) {
//                    inputBean.setAddress2("");
//                }
                try {
                    inputBean.setCity(user.getCity());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }

                try {
                    inputBean.setContactno(user.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setContactno("");
                }

                try {
                    inputBean.setEmail(user.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setEmail("");
                }

                try {
                    inputBean.setNic(user.getNic());
                } catch (NullPointerException e) {
                    inputBean.setNic("");
                }

                try {
                    inputBean.setDateofbirth(sdf.format(user.getDateofbirth()));
                } catch (NullPointerException e) {
                    inputBean.setDateofbirth("");
                }

                String serid_audit = "";
                String add1_audit = "";
//                String add2_audit = "";
                String city_audit = "";

                if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                    serid_audit = "--";
                } else {
                    serid_audit = inputBean.getServiceid();
                }
                if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                    add1_audit = "--";
                } else {
                    add1_audit = inputBean.getAddress1();
                }
//                if (inputBean.getAddress2() == null || inputBean.getAddress2().isEmpty()) {
//                    add2_audit = "--";
//                } else {
//                    add2_audit = inputBean.getAddress2();
//                }
                if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                    city_audit = "--";
                } else {
                    city_audit = inputBean.getCity();
                }

                inputBean.setOldvalue(inputBean.getUsername() + "|" + inputBean.getExpirydate() + "|"
                        + "admin|" + inputBean.getStatus() + "|" + inputBean.getFullname()
                        + "|" + inputBean.getContactno() + "|" + inputBean.getEmail() + "|" + inputBean.getNic()
                        + "|" + inputBean.getDateofbirth() + "|" + serid_audit + "|" + add1_audit + "|" + city_audit);

            } else {
                inputBean.setMessage("Empty system user id.");

                // set password expiry period (inserted)
                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Calendar cal = Calendar.getInstance();
                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                cal.setTime(CommonDAO.getSystemDateLogin());

                long l = passPolicy.getPasswordexpiryperiod();
                int i = (int) l;

                cal.add(Calendar.DAY_OF_MONTH,i);
                inputBean.setExpirydate(formatter.format(cal.getTime()));

            }
        } catch (Exception ex) {
            inputBean.setMessage("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";
    }

    //start newly chnged
    public String activate() {
        System.out.println("called UserManagementAction : activate");
        String message = null;
        String retType = "activate";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SystemuserDAO dao = new SystemuserDAO();
            message = this.validateInputs();
            if (message.isEmpty()) {
                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.SYSTEM_USER, SectionVarlist.USERMANAGEMENT, "Username " + inputBean.getUsername() + " updated", null);
                message = dao.activateUser(inputBean, audit);
                if (message.isEmpty()) {
                    message = "User " + MessageVarlist.COMMON_SUCC_ACTIVATE;
                }
                inputBean.setMessage(message);
            } else {
                addActionError(message);
            }

        } catch (Exception e) {
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(MessageVarlist.COMMON_ERROR_ACTIVATE);
        }
        return retType;
    }

    //end newly changed
    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getUsername() == null || inputBean.getUsername().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_USERNAME;
            } else if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getNic() == null || inputBean.getNic().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isAlphaNumeric(inputBean.getNic())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isValidateNIC(inputBean.getNic())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_NIC;
            } else if (inputBean.getPassword() == null || inputBean.getPassword().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_PASSWORD;
            } else if (!inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                message = MessageVarlist.SYSUSER_MGT_PASSWORD_MISSMATCH;
//            } else if (inputBean.getExpirydate() == null || inputBean.getExpirydate().trim().isEmpty()) {
//                message = MessageVarlist.SYSUSER_MGT_EMPTY_EXPIRYDATE;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_STATUS;
            } //            else if (inputBean.getServiceid() == null || inputBean.getServiceid().trim().isEmpty()) {
            //                message = MessageVarlist.SYSUSER_MGT_EMPTY_SERVICEID;
            //            } 
            //            else if (inputBean.getAddress1() == null || inputBean.getAddress1().trim().isEmpty()) {
            //                 message = MessageVarlist.SYSUSER_MGT_EMPTY_ADDRESS1;
            //            } 
            //            else if (inputBean.getAddress2() == null || inputBean.getAddress2().trim().isEmpty()) {
            //                message = MessageVarlist.SYSUSER_MGT_EMPTY_ADDRESS2;
            //            } 
            //            else if (inputBean.getCity() == null || inputBean.getCity().trim().isEmpty()) {
            //                message = MessageVarlist.SYSUSER_MGT_EMPTY_CITY;
            //            } 
            else if (inputBean.getContactno() == null || inputBean.getContactno().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_COANTACTNO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.validLocalPhoneno(inputBean.getContactno())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.isNumeric(inputBean.getContactno())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getEmail().isEmpty() && !Validation.isValidEmail(inputBean.getEmail())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_EMAIL;

            } else if (!inputBean.getDateofbirth().isEmpty() && inputBean.getDateofbirth() != null) {
                if (!Validation.isValidateDoBwithNIC(inputBean.getDateofbirth(), inputBean.getNic())) {
                    message = MessageVarlist.SYSUSER_MGT_INVALID_NIC_DOB;
                }
            }

//            else if (inputBean.getDateofbirth() == null || inputBean.getDateofbirth().trim().isEmpty()) {
//                message = MessageVarlist.SYSUSER_MGT_EMPTY_DATEOFBIRTH;
//            }
            if (inputBean.getPassword().equals(inputBean.getConfirmpassword())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getPassword());
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
            SystemuserDAO dao = new SystemuserDAO();

            Passwordpolicy passwordpolicy = dao.getPasswordPolicyDetails();
            if (passwordpolicy != null) {

                String msg = this.CheckPasswordValidity(password, passwordpolicy);

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
                msg = MessageVarlist.SYSUSER_PASSWORD_TOO_SHORT + bean.getMinimumlength();
            } else if (password.length() > bean.getMaximumlength()) {
                flag = false;
                msg = MessageVarlist.SYSUSER_PASSWORD_TOO_LARGE + bean.getMaximumlength();
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
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS + bean.getMinimumlowercasecharacters();
                    flag = false;
                } else if (upper < bean.getMinimumuppercasecharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS + bean.getMinimumuppercasecharacters();
                    flag = false;
                } else if (digits < bean.getMinimumnumericalcharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS + bean.getMinimumnumericalcharacters();
                    flag = false;
                } else if (special < bean.getMinimumspecialcharacters().intValue()) {
                    msg = MessageVarlist.SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS + bean.getMinimumspecialcharacters();
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
                        msg = MessageVarlist.SYSUSER_PASSWORD_MORE_CHAR_REPEAT + bean.getRepeatcharactersallow();
                        break;
                    }

                } while (!list.isEmpty());

            }

        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    private String validateUpdateInputs() throws Exception {
        String message = "";
        try {
            if (inputBean.getFullname() == null || inputBean.getFullname().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_FULLNAME;
            } else if (inputBean.getNic() == null || inputBean.getNic().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isAlphaNumeric(inputBean.getNic())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_NIC;
            } else if (!inputBean.getNic().isEmpty() && !Validation.isValidateNIC(inputBean.getNic())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_NIC;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_STATUS;
            } else if (inputBean.getContactno() == null || inputBean.getContactno().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_COANTACTNO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.isNumeric(inputBean.getContactno())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (!inputBean.getContactno().isEmpty() && !Validation.validLocalPhoneno(inputBean.getContactno())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_CONTACT_NO;
            } else if (inputBean.getEmail() == null || inputBean.getEmail().trim().isEmpty()) {
                message = MessageVarlist.SYSUSER_MGT_EMPTY_EMAIL;
            } else if (!inputBean.getEmail().isEmpty() && !Validation.isValidEmail(inputBean.getEmail().trim())) {
                message = MessageVarlist.SYSUSER_MGT_INVALID_EMAIL;
            } else if (!inputBean.getDateofbirth().isEmpty() && inputBean.getDateofbirth() != null) {
                if (!Validation.isValidateDoBwithNIC(inputBean.getDateofbirth(), inputBean.getNic())) {
                    message = MessageVarlist.SYSUSER_MGT_INVALID_NIC_DOB;
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    private String validateChangePasswordInputs() throws Exception {
        String message = "";
        try {

            if (inputBean.getCnewpwd() == null || inputBean.getCnewpwd().trim().isEmpty()) {
                message = MessageVarlist.PASSWORDRESET_EMPTY_NEW_PASSWORD;
//            } else if (inputBean.getNewpwd() == null || inputBean.getNewpwd().trim().isEmpty()) {
//                message = MessageVarlist.PASSWORDRESET_EMPTY_NEW_PASSWORD;
            } else if (inputBean.getCrenewpwd() == null || inputBean.getCrenewpwd().trim().isEmpty()) {
                message = MessageVarlist.PASSWORDRESET_EMPTY_COM_PASSWORD;
            } else if (!inputBean.getCnewpwd().trim().contentEquals(inputBean.getCrenewpwd().trim())) {
                message = MessageVarlist.PASSWORDRESET_MATCH_PASSWORD;
            }
            if (inputBean.getCnewpwd().trim().equals(inputBean.getCrenewpwd().trim())) {
                String msg = "";
                msg = this.checkPolicy(inputBean.getCnewpwd().trim());
                if (message.equals("")) {
                    message = msg;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String viewpopup() {
        String result = "viewpopup";
        System.out.println("called SystemUser : ViewPopup");
        try {

                HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
                Systemuser sysUser = (Systemuser) httpSession.getAttribute(SessionVarlist.SYSTEMUSER);

                CommonDAO dao = new CommonDAO();
                SystemuserDAO sysdao = new SystemuserDAO();
                PasswordPolicyDAO passPolicydao = new PasswordPolicyDAO();
                Passwordpolicy passPolicy = passPolicydao.getPasswordPolicyDetails();

//                MsUserParam userparam = null;
//                userparam = sysdao.setTooltip("PWTOOLTIP");
                inputBean.setPwtooltip(passPolicydao.generateToolTipMessage(passPolicy));
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

        } catch (Exception e) {
            addActionError("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String detail() {
        System.out.println("called SystemUser: detail");
        System.err.println("detail " + inputBean.getUsername());
        Systemuser user = null;

        try {
            if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {

                SystemuserDAO dao = new SystemuserDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

                HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
                Systemuser sysUser = (Systemuser) httpSession.getAttribute(SessionVarlist.SYSTEMUSER);

                user = dao.getSystemuserByUserName(inputBean.getUsername());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    inputBean.setUsername(user.getUsername());
                } catch (NullPointerException e) {
                    inputBean.setUsername("");
                }

                try {
                    inputBean.setExpirydate(sdf.format(user.getExpirydate()).substring(0, 19));
                } catch (NullPointerException e) {
                    inputBean.setExpirydate("");
                }

       

//                try {
////                    inputBean.setDualauthuser(user.getDualauthuserrole().getUsername());
//                    inputBean.setDualauthuser(user.getDualauthuserrole());
//                } catch (NullPointerException e) {
//                    inputBean.setDualauthuser("");
//                }
                try {
                    inputBean.setStatus(user.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setFullname(user.getFullname());
                } catch (NullPointerException e) {
                    inputBean.setFullname("");
                }

               
                try {
                    inputBean.setAddress1(user.getAddress());
                } catch (NullPointerException e) {
                    inputBean.setAddress1("");
                }

//                try {
//                    inputBean.setAddress2(user.getAddress2());
//                } catch (NullPointerException e) {
//                    inputBean.setAddress2("");
//                }
                try {
                    inputBean.setCity(user.getCity());
                } catch (NullPointerException e) {
                    inputBean.setCity("");
                }

                try {
                    inputBean.setContactno(user.getMobile());
                } catch (NullPointerException e) {
                    inputBean.setContactno("");
                }

                try {
                    inputBean.setEmail(user.getEmail());
                } catch (NullPointerException e) {
                    inputBean.setEmail("");
                }

                try {
                    inputBean.setNic(user.getNic());
                } catch (NullPointerException e) {
                    inputBean.setNic("");
                }

                try {
                    inputBean.setDateofbirth(sdf.format(user.getDateofbirth()));
                } catch (NullPointerException e) {
                    inputBean.setDateofbirth("");
                }

                String serid_audit = "";
                String add1_audit = "";
//                String add2_audit = "";
                String city_audit = "";

                if (inputBean.getServiceid() == null || inputBean.getServiceid().isEmpty()) {
                    serid_audit = "--";
                } else {
                    serid_audit = inputBean.getServiceid();
                }
                if (inputBean.getAddress1() == null || inputBean.getAddress1().isEmpty()) {
                    add1_audit = "--";
                } else {
                    add1_audit = inputBean.getAddress1();
                }
//                if (inputBean.getAddress2() == null || inputBean.getAddress2().isEmpty()) {
//                    add2_audit = "--";
//                } else {
//                    add2_audit = inputBean.getAddress2();
//                }
                if (inputBean.getCity() == null || inputBean.getCity().isEmpty()) {
                    city_audit = "--";
                } else {
                    city_audit = inputBean.getCity();
                }

                inputBean.setOldvalue(inputBean.getUsername() + "|" + inputBean.getExpirydate() + "|"
                        + "admin|" + inputBean.getStatus() + "|" + inputBean.getFullname()
                        + "|" + inputBean.getContactno() + "|" + inputBean.getEmail() + "|" + inputBean.getNic()
                        + "|" + inputBean.getDateofbirth() + "|" + serid_audit + "|" + add1_audit + "|" + city_audit);

            } else {
                inputBean.setMessage("Empty ID.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("System user " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SystemuserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "detail";

    }

}
