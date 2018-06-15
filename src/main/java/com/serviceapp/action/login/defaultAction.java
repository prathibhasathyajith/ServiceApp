/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.action.login;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.serviceapp.varlist.MessageVarlist;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prathibha_s
 */
public class defaultAction extends ActionSupport {

    @Override
    public String execute() {
        System.out.println("called defaultAction : execute");
        return SUCCESS;
    }

    public String viewHome() {
        String result = "view";
        try {

            System.out.println("called defaultAction :View");

        } catch (Exception ex) {
            addActionError("defaultAction " + MessageVarlist.COMMON_ERROR_PROCESS);
            Logger.getLogger(defaultAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
