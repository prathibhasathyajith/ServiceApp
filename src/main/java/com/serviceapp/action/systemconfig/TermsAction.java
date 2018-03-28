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
import com.serviceapp.dao.systemconfig.TermsDAO;
import com.serviceapp.dao.usermanagement.SystemuserDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.WebTerms;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha
 */
public class TermsAction extends ActionSupport implements ModelDriven<Object> {

    TermsInputBean inputBean = new TermsInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called TermsAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called TermsAction : execute");
        try {

            CommonDAO dao = new CommonDAO();
            TermsDAO termdao = new TermsDAO();

            inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
            inputBean.setDefaultStatus(CommonVarlist.STATUS_ACTIVE);
            termdao.getVersionList(inputBean);

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

    public String find() throws Exception {
        String result = "find";
        System.out.println("called TermsAction : find");
        try {

            CommonDAO dao = new CommonDAO();
            List<WebTerms> webterm = new ArrayList<WebTerms>();
            webterm = dao.getVersionList(inputBean.getVersionno());

            inputBean.setStatus(webterm.get(0).getStatus().getStatuscode());
            inputBean.setDescription(webterm.get(0).getTerms());

        } catch (Exception ex) {
            addActionError("Terms find " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() throws Exception {
        String result = "list";
        System.out.println("called TermsAction : update");

        System.out.println("status " + inputBean.getStatus());
        System.out.println("des " + inputBean.getDescription());
        System.out.println("version " + inputBean.getVersionno());
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            String message = this.validateInputs();

            if (message.isEmpty()) {
                TermsDAO dao = new TermsDAO();
                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.TERMS_AND_CONDITION, SectionVarlist.USERMANAGEMENT, "Terms updated successfully", null);
                message = dao.updateTerms(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage(MessageVarlist.RESET_PASSWORD_SUCCESS);
                } else {
                    addActionError(message);
                }
            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            addActionError("Terms update " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(TermsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() throws Exception {
        String message = "";
        try {

            if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.TERMS_EMPTY_STATUS;
            } else if (inputBean.getDescription() == null || inputBean.getDescription().trim().isEmpty()) {
                message = MessageVarlist.TERMS_EMPTY_DESCRIPTION;
            } else if (!inputBean.getVersionno().trim().contentEquals(inputBean.getVersionno().trim())) {
                message = MessageVarlist.TERMS_EMPTY_VERSION;
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

}
