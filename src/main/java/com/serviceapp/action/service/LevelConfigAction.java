/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.service;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.service.LevelConfigBean;
import com.serviceapp.bean.service.LevelConfigInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.dao.service.LevelConfigDAO;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.util.Iterator;
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
public class LevelConfigAction extends ActionSupport implements ModelDriven<Object> {

    LevelConfigInputBean inputBean = new LevelConfigInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called LevelConfigAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called LevelConfigAction : view");
        try {

            CommonDAO dao = new CommonDAO();
            LevelConfigDAO db = new LevelConfigDAO();
            List<LevelConfigBean> data = db.getLevels();

            this.setLevelData(inputBean);

            for (Iterator<LevelConfigBean> iterator = data.iterator(); iterator.hasNext();) {
                LevelConfigBean next = iterator.next();

                System.out.println("level - " + next.getLevel());
                System.out.println("des - " + next.getDescription());
                System.out.println("price - " + next.getPrice());
                System.out.println("count - " + next.getServiceCount());
                System.out.println("---------------------------------------");

            }

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called LevelConfigAction :View");

        } catch (Exception ex) {
            addActionError("LevelConfigAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(LevelConfigAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void setLevelData(LevelConfigInputBean inputBean) throws Exception {
        LevelConfigDAO db = new LevelConfigDAO();
        try {

            List<LevelConfigBean> data = db.getLevels();
            //Basic
            LevelConfigBean basic = new LevelConfigBean();
            basic = data.get(0);
            inputBean.setBasic(basic.getLevel());
            inputBean.setBasicDes(basic.getDescription());
            inputBean.setBasicPrice(basic.getPrice());
            inputBean.setBasicSerCont(basic.getServiceCount());

            //Bronze
            LevelConfigBean bronze = new LevelConfigBean();
            bronze = data.get(1);
            inputBean.setBronze(bronze.getLevel());
            inputBean.setBronzeDes(bronze.getDescription());
            inputBean.setBronzePrice(bronze.getPrice());
            inputBean.setBronzeSerCont(bronze.getServiceCount());

            //Gold
            LevelConfigBean gold = new LevelConfigBean();
            gold = data.get(2);
            inputBean.setGold(gold.getLevel());
            inputBean.setGoldDes(gold.getDescription());
            inputBean.setGoldPrice(gold.getPrice());
            inputBean.setGoldSerCont(gold.getServiceCount());

            //Platinum
            LevelConfigBean platinum = new LevelConfigBean();
            platinum = data.get(3);
            inputBean.setPlatinum(platinum.getLevel());
            inputBean.setPlatinumDes(platinum.getDescription());
            inputBean.setPlatinumPrice(platinum.getPrice());
            inputBean.setPlatinumSerCont(platinum.getServiceCount());

            //silver
            LevelConfigBean silver = new LevelConfigBean();
            silver = data.get(4);
            inputBean.setSilver(silver.getLevel());
            inputBean.setSilverDes(silver.getDescription());
            inputBean.setSilverPrice(silver.getPrice());
            inputBean.setSilverSerCont(silver.getServiceCount());
        } catch (Exception e) {
            throw e;
        }

    }

    public String find() throws Exception {
        String result = "list";
        System.out.println("called LevelConfigAction : find");
        try {
            this.setLevelData(inputBean);
        } catch (Exception ex) {
            addActionError("LevelConfigAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(LevelConfigAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    public String update() {
        String result = "message";
        System.out.println("called LevelConfigAction : update");

        try {
            String message = this.validateInputs();

            if (message.isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();
                LevelConfigDAO dao = new LevelConfigDAO();

                String newV = inputBean.getBasicPrice() + "|"
                        + inputBean.getBasicSerCont() + "|"
                        + inputBean.getBronzePrice() + "|"
                        + inputBean.getBronzeSerCont() + "|"
                        + inputBean.getSilverPrice() + "|"
                        + inputBean.getSilverSerCont() + "|"
                        + inputBean.getGoldPrice() + "|"
                        + inputBean.getGoldSerCont() + "|"
                        + inputBean.getPlatinumPrice() + "|"
                        + inputBean.getPlatinumSerCont();

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK,
                        PageVarlist.LEVEL_CONFIG_PAGE, SectionVarlist.SERVICE_MGT,
                        "Lewvel configuration details updated", null, inputBean.getOldvalue(), newV);
                message = dao.updateLevelConfig(inputBean, audit);

                if (message.isEmpty()) {
                    addActionMessage("Level config " + MessageVarlist.COMMON_SUCC_UPDATE);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }

        } catch (Exception ex) {
            addActionError("LevelConfigAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(LevelConfigAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    public String validateInputs() {
        String message = "";
        if (inputBean.getBasicPrice() == null || inputBean.getBasicPrice().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getBasicSerCont() == null || inputBean.getBasicSerCont().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getBronzePrice() == null || inputBean.getBronzePrice().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getBronzeSerCont() == null || inputBean.getBronzeSerCont().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getSilverPrice() == null || inputBean.getSilverPrice().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getSilverSerCont() == null || inputBean.getSilverSerCont().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getPlatinumSerCont() == null || inputBean.getPlatinumSerCont().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getPlatinumPrice() == null || inputBean.getPlatinumPrice().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getGoldPrice() == null || inputBean.getGoldPrice().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        } else if (inputBean.getGoldSerCont() == null || inputBean.getGoldSerCont().isEmpty()) {
            message = MessageVarlist.INPUT_FIELDS_EMPTY_MSG;
        }

        return message;
    }
}
