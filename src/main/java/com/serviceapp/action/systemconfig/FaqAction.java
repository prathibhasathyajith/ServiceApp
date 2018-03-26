/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.FaqBean;
import com.serviceapp.bean.systemconfig.FaqInputBean;
import com.serviceapp.common.dao.CommonDAO;
import static com.serviceapp.common.dao.CommonDAO.checkEmptyorNullString;
import com.serviceapp.dao.systemaudit.SystemAuditDAO;
import com.serviceapp.dao.systemconfig.FaqDAO;
import com.serviceapp.mapping.MobFaq;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.OracleMessage;
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
public class FaqAction extends ActionSupport implements ModelDriven<Object>{
    
    FaqInputBean inputBean = new FaqInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called FaqAction : execute");
        return SUCCESS;
    }
    
    public String view() {
        System.out.println("called FQAAction :view");
        String result = "view";
        try {
            

                CommonDAO dao = new CommonDAO();
                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
                inputBean.setTypeList(dao.getDefultTypeList());

            
        } catch (Exception ex) {
            addActionError("FAQ " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String list() {

        System.out.println("called FAQAction: list");
        try {
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

            FaqDAO dao = new FaqDAO();
            List<FaqBean> dataList = dao.getSearchList(inputBean, rows, from, orderBy);

            /**
             * for search audit
             */
            if (inputBean.isSearch() && from == 0) {

                HttpServletRequest request = ServletActionContext.getRequest();

                String searchParameters = "["
                        + checkEmptyorNullString("ID", inputBean.getId())
                        + checkEmptyorNullString("Type", inputBean.getType())
                        + checkEmptyorNullString("Section", inputBean.getSection())
                        + checkEmptyorNullString("Status", inputBean.getStatus())
                        + checkEmptyorNullString("Question", inputBean.getQuestion())
                        + checkEmptyorNullString("Answer", inputBean.getAnswer())
                        + "]";

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.SEARCH_TASK, PageVarlist.FAQ_MGT, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "FAQ search using " + searchParameters + " parameters ", null);
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
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, e);
            addActionError(MessageVarlist.COMMON_ERROR_PROCESS + " FAQ Action");
        }
        return "list";
    }

    public String add() {

        System.out.println("called FQAAction : add");
        String result = "message";
        String message = "";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            FaqDAO dao = new FaqDAO();

            message = this.validateInputs();

            if (message.isEmpty()) {

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.ADD_TASK, PageVarlist.FAQ_MGT, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "", null, null, null);
                message = dao.insertFaq(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("FAQ " + MessageVarlist.COMMON_SUCC_INSERT);
                } else {
                    addActionError(MessageVarlist.COMMON_ALREADY_EXISTS);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("FAQ " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String validateInputs() throws Exception {

        String message = "";
        try {
            if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_TYPE;
            } else if (inputBean.getSection() == null || inputBean.getSection().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_SECTION;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_STATUS;
            } else if (inputBean.getQuestion() == null || inputBean.getQuestion().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_QUESTION;
            } else if (inputBean.getAnswer() == null || inputBean.getAnswer().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_ANSWER;
            }

        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String viewpopup() {

        String result = "viewpopup";
        System.out.println("called FQAAction : ViewPopup");

        try {
            
                CommonDAO dao = new CommonDAO();

                inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
                inputBean.setTypeList(dao.getDefultTypeList());

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

        } catch (Exception e) {
            addActionError("FAQ " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String delete() {

        System.out.println("called FAQAction : delete");
        String message = "";
        String retType = "delete";

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            FaqDAO dao = new FaqDAO();
            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.DELETE_TASK, PageVarlist.FAQ_MGT, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "FAQ " + inputBean.getId() + " deleted", null);

            message = dao.deleteFaq(inputBean, audit);

            if (message.isEmpty()) {
                message = "FAQ " + MessageVarlist.COMMON_SUCC_DELETE;
            }

            inputBean.setMessage(message);

        } catch (Exception e) {
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, e);
            inputBean.setMessage(OracleMessage.getMessege(e.getMessage()));
        }
        return retType;
    }

    public String update() {

        System.out.println("called FAQAction : update");
        String retType = "message";
        FaqDAO dao = new FaqDAO();

        try {
            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {
                //set id get in hidden fileds
                inputBean.setId(inputBean.getId());

                String message = this.validateUpdateInputs();

                if (message.isEmpty()) {
                    HttpServletRequest request = ServletActionContext.getRequest();

                    Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.FAQ_MGT, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "FAQ " + inputBean.getId() + " updated", null, null, null);
                    message = dao.updateFaq(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("FAQ " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("FAQ " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateUpdateInputs() throws Exception {

        String message = "";

        try {
            if (inputBean.getId() == null || inputBean.getId().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_ID;
            } else if (inputBean.getType() == null || inputBean.getType().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_TYPE;
            } else if (inputBean.getSection() == null || inputBean.getSection().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_SECTION;
            } else if (inputBean.getQuestion() == null || inputBean.getQuestion().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_QUESTION;
            } else if (inputBean.getAnswer() == null || inputBean.getAnswer().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_ANSWER;
            } else if (inputBean.getStatus() == null || inputBean.getStatus().trim().isEmpty()) {
                message = MessageVarlist.FAQ_MGT_EMPTY_STATUS;
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    public String detail() {

        System.out.println("called FAQAction: detail");
        MobFaq mobfaq = null;

        try {
            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {

                FaqDAO dao = new FaqDAO();
                CommonDAO commonDAO = new CommonDAO();

                inputBean.setStatusList(commonDAO.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));
                inputBean.setTypeList(commonDAO.getDefultTypeList());

                mobfaq = dao.getId(inputBean.getId());

                try {
                    inputBean.setId(mobfaq.getId().toString());
                } catch (NullPointerException e) {
                    inputBean.setId("");
                }

                try {
                    inputBean.setType(mobfaq.getMobFaqType().getCode());
                } catch (NullPointerException e) {
                    inputBean.setType("");
                }

                try {
                    inputBean.setSection(mobfaq.getSection());
                } catch (NullPointerException e) {
                    inputBean.setSection("");
                }

                try {
                    inputBean.setStatus(mobfaq.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setQuestion(mobfaq.getQuestion());
                } catch (NullPointerException e) {
                    inputBean.setQuestion("");
                }

                try {
                    inputBean.setAnswer(mobfaq.getAnswer());
                } catch (NullPointerException e) {
                    inputBean.setAnswer("");
                }

            } else {
                inputBean.setMessage("Empty ID.");
            }

        } catch (Exception ex) {
            inputBean.setMessage("FAQ " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "detail";
    }

    public String find() {

        System.out.println("called FAQAction: find");
        MobFaq mobfaq = new MobFaq();

        try {

            if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {

                FaqDAO dao = new FaqDAO();

                mobfaq = dao.getFaqById(inputBean.getId());

                try {
                    inputBean.setId(mobfaq.getId().toString());
                } catch (NullPointerException e) {
                    inputBean.setId("");
                }

                try {
                    inputBean.setType(mobfaq.getMobFaqType().getCode());
                } catch (NullPointerException e) {
                    inputBean.setType("");
                }

                try {
                    inputBean.setSection(mobfaq.getSection());
                } catch (NullPointerException e) {
                    inputBean.setSection("");
                }

                try {
                    inputBean.setStatus(mobfaq.getStatus().getStatuscode());
                } catch (NullPointerException e) {
                    inputBean.setStatus("");
                }

                try {
                    inputBean.setQuestion(mobfaq.getQuestion());
                } catch (NullPointerException e) {
                    inputBean.setQuestion("");
                }

                try {
                    inputBean.setAnswer(mobfaq.getAnswer());
                } catch (NullPointerException e) {
                    inputBean.setAnswer("");
                }

            } else {
                inputBean.setMessage("No such record!.");
            }

        } catch (Exception ex) {
            inputBean.setMessage("FAQ " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(FaqAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "find";
    }
}
