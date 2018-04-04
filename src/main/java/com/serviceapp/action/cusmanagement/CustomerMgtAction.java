/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.cusmanagement;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.serviceapp.bean.cusmanagement.CustomerMgtInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.common.dao.Validation;
import com.serviceapp.dao.cusmanagement.CustomerMgtDAO;
import com.serviceapp.mapping.MobUser;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import com.serviceapp.varlist.PageVarlist;
import com.serviceapp.varlist.SectionVarlist;
import com.serviceapp.varlist.SessionVarlist;
import com.serviceapp.varlist.TaskVarlist;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author prathibha_s
 */
public class CustomerMgtAction extends ActionSupport implements ModelDriven<Object> {

    CustomerMgtInputBean inputBean = new CustomerMgtInputBean();

    public Object getModel() {
        return inputBean;
    }

    @Override
    public String execute() {
        System.out.println("called CustomerMgtAction : execute");
        return SUCCESS;
    }

    public String view() {
        String result = "view";
        try {

            CommonDAO dao = new CommonDAO();
            inputBean.setStatusList(dao.getDefultStatusList(CommonVarlist.STATUS_CATEGORY_GENERAL));

            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD) != null && session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) != null) {
                if ((Integer) session.getAttribute(SessionVarlist.ONLY_SHOW_ONTIME) == 0) {
                    session.setAttribute(SessionVarlist.ONLY_SHOW_ONTIME, 1);
                    addActionError((String) session.getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD));
                }
            }

            System.out.println("called CustomerMgtAction :View");

        } catch (Exception ex) {
            addActionError("CustomerMgtAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String update() {

        System.out.println("called CustomerMgtAction : update");
        String retType = "message";

        BufferedImage readImage = null;
        File destFile;
        ServletContext context = ServletActionContext.getServletContext();
        String destPath = context.getRealPath("/resouces/images");

        try {
//            if (inputBean.getUserId() != null && !inputBean.getUserId().isEmpty()) {

            String message = "";

//                if (message.isEmpty()) {
            HttpServletRequest request = ServletActionContext.getRequest();
            CustomerMgtDAO dao = new CustomerMgtDAO();

            Systemaudit audit = CommonDAO.makeAudittrace(request, TaskVarlist.UPDATE_TASK, PageVarlist.CUS_MGT_PAGE, SectionVarlist.CUSTOMERMANAGEMENT, "User id " + inputBean.getUserId() + " updated", null, null, null);

            if (inputBean.getOwnImageFileName() != null) {

                message = this.getImageLogo(this.inputBean.getOwnImageFileName()); // get file

                if (message.isEmpty()) {

                    destFile = new File(destPath, inputBean.getOwnImageFileName() + ".jpg");
                    FileUtils.copyFile(inputBean.getOwnImage(), destFile);
                    readImage = ImageIO.read(new File(destPath, inputBean.getOwnImageFileName() + ".jpg"));

                    int height = readImage.getHeight();
                    int width = readImage.getWidth();

                    System.out.println("height --- " + height);
                    System.out.println("width --- " + width);

//                            System.err.println(height + " " + width);
//                            if (height != 92) {
//                                message = "Logo height should be 92 pixels";
//                            }
//                            if (width > 92) {
//                                message = "Logo width should be less than 280 pixels";
//                            }
                }

//                    } else if (dao.isexsitsImg(inputBean.getCode())) {
////                        message = MessageVarlist.INSTITUTE_MGT_EMPTY_LOGO;
//                    }
                if (message.isEmpty()) {

                    message = dao.updateCustomer(inputBean, audit);

                    if (message.isEmpty()) {
                        addActionMessage("CustomerMgtAction " + MessageVarlist.COMMON_SUCC_UPDATE);
                    } else {
                        addActionError(message);
                    }
                } else {
                    addActionError(message);
                }

//                } else {
//                    addActionError(message);
//                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomerMgtAction.class.getName()).log(Level.SEVERE, null, ex);
            addActionError("CustomerMgtAction " + MessageVarlist.COMMON_ERROR_UPDATE);
        }
        return retType;
    }

    public String find() throws Exception {
        System.out.println("called CustomerMgtAction : find");
        CustomerMgtDAO dao = new CustomerMgtDAO();
        MobUser mu = dao.findMobUserById(32);

        inputBean.setEditOwnImage(mu.getImage());
        return "list";
    }

    public String getImageLogo(String file) {
        String msgEx = "";
        if (file == null) {
            msgEx = "Please choose a file to upload.";
        } else {
            msgEx = Validation.isImageLogo(file);
        }
        return msgEx;
    }
}
