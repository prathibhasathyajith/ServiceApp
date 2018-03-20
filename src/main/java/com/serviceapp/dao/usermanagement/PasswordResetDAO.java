/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.usermanagement;

import com.serviceapp.bean.usermanagement.PasswordResetInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.MessageVarlist;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha
 */
public class PasswordResetDAO {

    //password reset user 
    public String UpdatePasswordReset(PasswordResetInputBean inputBean, Systemaudit audit, Passwordpolicy passPolicy) throws Exception {
        Session session = null;
        Transaction txn = null;
        Calendar cal = Calendar.getInstance();
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Systemuser as u where u.username=:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getHusername().trim());
            Systemuser u = (Systemuser) query.list().get(0);

            // update password expiry period (inserted) 
            String sql3 = "select passwordexpiryperiod from Passwordpolicy";
            Query query3 = session.createQuery(sql3);
            Iterator itCount = query3.iterate();
            int expiryperiod = (Integer) itCount.next();

            if (u != null && !inputBean.getCurrpwd().equals(inputBean.getRenewpwd())) {
                u.setPassword(inputBean.getRenewpwd());
                // this is not the first time logged in
                u.setInitialloginstatus("1");
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                // update password expiry period (inserted)
                cal.setTime(sysDate);
                cal.add(Calendar.DAY_OF_MONTH, expiryperiod);
                u.setExpirydate(cal.getTime());

                session.save(audit);
                session.update(u);
//                 txn.commit();
//                session.flush();
//                session.close();              

                txn.commit();

            } else {
//                message = MessageVarList.COMMON_ALREADY_EXISTS;
                message = MessageVarlist.RESET_PASS_SAME_NEW_CURRENT;
            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
//                if(session.isOpen()){
//                    session.close();
//                }
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }

    //find user by username
    public Systemuser findUserById(String username) throws Exception {

        Systemuser user = new Systemuser();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Systemuser as u join fetch u.status where u.username=:username";
            Query query = session.createQuery(sql).setString("username", username);
            user = (Systemuser) query.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return user;

    }
}
