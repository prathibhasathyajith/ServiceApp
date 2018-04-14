/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.systemconfig;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.systemconfig.ConfigDataInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.dao.systemconfig.ConfigDataDAO;
import com.serviceapp.mapping.MobConfiguration;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class ConfigDataAction extends ActionSupport implements ModelDriven<Object> {

    ConfigDataInputBean inputBean = new ConfigDataInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called ConfigDataAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        System.out.println("called ConfigDataAction: view");
        try {

            CommonDAO dao = new CommonDAO();

        } catch (Exception e) {
            addActionError("ConfigDataAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ConfigDataAction.class.getName()).log(Level.SEVERE, null, e);

        }
        return result;
    }

    public String load() {
        System.out.println("called ConfigDataAction: load");
        try {

            ConfigDataDAO dao = new ConfigDataDAO();

            Map<String, String> nameList = new LinkedHashMap<String, String>();
            Map<String, String> nameList_Val = new LinkedHashMap<String, String>();
            List<MobConfiguration> mc = dao.getConfigData();

            String oldval = "";
            int count = 0;
            int key = 1;

            if (mc != null) {
                for (MobConfiguration mobConfiguration : mc) {
                    nameList.put("key"+key, mobConfiguration.getDescription());
                    nameList_Val.put("key"+key, mobConfiguration.getValue());

                    oldval += mobConfiguration.getValue() + "|";
                    count++;
                    key++;
                }
            }

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            session.setAttribute(SessionVarlist.COMF_DATA_COUNT, count);
            session.setAttribute(SessionVarlist.COMF_DATA_NAMELIST1, nameList);
            session.setAttribute(SessionVarlist.COMF_DATA_OLDVAL, oldval);

            inputBean.setNameList(nameList);
            inputBean.setNameListVal(nameList_Val);

        } catch (Exception e) {
            addActionError("ConfigDataAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(ConfigDataAction.class.getName()).log(Level.SEVERE, null, e);

        }
        return "list";
    }

    public String update() {
        String retType = "message";
        System.out.println("called ConfigDataAction: update");
        try {

            HttpSession session = ServletActionContext.getRequest().getSession(false);

            Integer count = (Integer) session.getAttribute(SessionVarlist.COMF_DATA_COUNT);
            Map<String, String> nameList = (Map<String, String>) session.getAttribute(SessionVarlist.COMF_DATA_NAMELIST1);

            String message = this.validateInputs(count, nameList);

            if (message.isEmpty()) {

                HttpServletRequest request = ServletActionContext.getRequest();

                ConfigDataDAO dao = new ConfigDataDAO();

                String newV = "";

                for (int i = 1; i < count + 1; i++) {
                    Method getMethod = inputBean.getClass().getMethod(MessageVarlist.CONFIGDATA_METHOD_NAME + i, new Class[]{});
                    String inputData = (String) getMethod.invoke(inputBean, new Object[]{});
                    newV += inputData + "|";
                }

                Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.CONFIG_DATA_PAGE, SectionVarlist.SYSTEMCONFIGMANAGEMENT, "Configuration Data updated", null, (String) session.getAttribute(SessionVarlist.COMF_DATA_OLDVAL), newV);
                message = dao.updateConfigData(inputBean, audit,count);

                if (message.isEmpty()) {
                    addActionMessage("Config Data " + MessageVarlist.COMMON_SUCC_UPDATE);
                } else {
                    addActionError(message);
                }

            } else {
                addActionError(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(ConfigDataAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("Config Data " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    private String validateInputs(int count, Map<String, String> namelist) throws Exception {
        String message = "";
        for (int i = 1; i < count + 1; i++) {
            Method getMethod = inputBean.getClass().getMethod(MessageVarlist.CONFIGDATA_METHOD_NAME + i, new Class[]{});
            String inputData = (String) getMethod.invoke(inputBean, new Object[]{});

            if (inputData == null || inputData.isEmpty()) {
                message = namelist.get(MessageVarlist.CONFIGDATA_KEY_NAME + i) + MessageVarlist.CONFIGDATA_EMPTY_MSG;
                break;
            }
        }
        return message;
    }

}
