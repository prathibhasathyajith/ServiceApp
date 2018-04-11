/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.cusmanagement;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.cusmanagement.SuggestedUserBean;
import com.serviceapp.bean.cusmanagement.SuggestedUserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.cusmanagement.SuggestedUserDAO;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.mapping.MobSuggestedUser;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.CommonVarlist;
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
public class SuggestedUserAction extends ActionSupport implements ModelDriven<Object> {

    SuggestedUserInputBean inputBean = new SuggestedUserInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called SuggestedUserAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {

            CommonDAO dao = new CommonDAO();
            SuggestedUserDAO sdao = new SuggestedUserDAO();
            inputBean.setStatusList(dao.getStatusListCus());
            inputBean.setServiceroleList(sdao.getRoleList());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called SuggestedUserAction :View");

        } catch (Exception ex) {
            addActionError("SuggestedUserAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SuggestedUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String viewDetail() {
        System.out.println("called SuggestedUserAction : detail");
        MobSuggestedUser msu = null;
        try {
            if (inputBean.getId()!= null && !inputBean.getId().isEmpty()) {

                SuggestedUserDAO dao = new SuggestedUserDAO();
                CommonDAO commonDAO = new CommonDAO();
                inputBean.setStatusList(commonDAO.getStatusListCus());
                inputBean.setServiceroleList(dao.getRoleList());

                msu = dao.findSuggestUserById(inputBean.getId());
                
                inputBean.setFirstName(msu.getFirstName());

                

            } else {
                inputBean.setMessage("Empty suggested user id.");
            }
        } catch (Exception ex) {
            inputBean.setMessage("SuggestedUserAction id  " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(SuggestedUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "viewdetail";

    }

    public String List() {
        System.out.println("called SuggestedUserAction: List");
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
            SuggestedUserDAO dao = new SuggestedUserDAO();
            List<SuggestedUserBean> dataList = dao.getSearchList(inputBean, to, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {
                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("First name", inputBean.getSearch_firstname())
                        + checkEmptyorNullString("Last name", inputBean.getSearch_lastname())
                        + checkEmptyorNullString("Email", inputBean.getSearch_email())
                        + checkEmptyorNullString("Mobile", inputBean.getSearch_mobile())
                        + checkEmptyorNullString("Status", inputBean.getSearch_status())
                        + checkEmptyorNullString("Role", inputBean.getSearch_role())
                        + checkEmptyorNullString("Referrer name", inputBean.getSearch_name_user())
                        + checkEmptyorNullString("Referrer mobile", inputBean.getSearch_mobile_user())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.SUGGEST_USER_PAGE, SectionVarlist.CUSTOMERMANAGEMENT, "Suggested user search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(SuggestedUserAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(" Suggested User " + MessageVarlist.COMMON_ERROR_PROCESS);
        }
        return "list";
    }
}
