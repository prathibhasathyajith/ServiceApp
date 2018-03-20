/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.serviceapp.mapping.Systemuser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author chanuka
 */
public class AccessControlInterceptor implements Interceptor {

    @Override
    public void destroy() {
        System.out.println("Access Control Interceptor : Destroyed");
    }

    @Override
    public void init() {
        System.out.println("Access Control Interceptor : Initialized");
    }

    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        String result = "";
        String INTERCEPT_LOGOUT = "acccontroler";
        Systemuser suser = new Systemuser();
        try {
            System.out.println("-------Access Control Interceptor : Started-------");
            String className = ai.getAction().getClass().getName();
            System.out.println("Access Control Interceptor : Intercepted " + className);

            ActionProxy ap = ai.getProxy();
            String method = ap.getMethod();

            System.out.println("Access Control Interceptor : Method " + method);

            if (method.trim().equalsIgnoreCase("Check") || method.trim().equalsIgnoreCase("Logout")) {
                result = ai.invoke();
            } else {
                try {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        Systemuser user = (Systemuser)session.getAttribute("SYSTEMUSER");
                        if (user!=null) {
                            result = ai.invoke();
                           
                        } else {
                            result = INTERCEPT_LOGOUT;
                            session.setAttribute("intercept", "ERROR_USER_INFO");
                        }
                    } else {
                        result = INTERCEPT_LOGOUT;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(AccessControlInterceptor.class.getName()).log(Level.SEVERE, null, ex);
                    result = INTERCEPT_LOGOUT;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AccessControlInterceptor.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("Access Control Interceptor : result " + result);
        System.out.println("-------Access Control Interceptor : Stopped-------");
        return result;
    }
}
