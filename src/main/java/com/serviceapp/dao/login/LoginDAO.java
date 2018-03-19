/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.login;

import com.serviceapp.bean.login.LoginBean;
import com.serviceapp.dao.login.*;
import com.serviceapp.bean.login.LoginInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.mapping.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha
 */
public class LoginDAO {
    
    public Systemuser findUserbyUsername(String username) throws Exception {
        Systemuser user = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from Systemuser as u join fetch u.status where u.username =:username";
            Query query = session.createQuery(sql).setString("username", username);

            user = (Systemuser) query.list().get(0);
        } catch (IndexOutOfBoundsException ibe) {
            user = null;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return user;
    }
    
    public Passwordpolicy findPasswordPolicy() throws Exception {
        Passwordpolicy passwordpolicy = new Passwordpolicy();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hql = "from Passwordpolicy as m";
            Query query = session.createQuery(hql);
            passwordpolicy = (Passwordpolicy) query.list().get(0);

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
        return passwordpolicy;
    }

    public String checkUser(LoginInputBean inputBean) throws Exception {
        List<User> laList = new ArrayList<User>();
        Session session = null;
        String des = "";
        try {
            session = HibernateInit.sessionFactory.openSession();

            String hash = this.HashSHA256(inputBean.getLoginPassword());
            String hql = "from User as t where t.username =:username and LOWER(t.password)=:password and t.userType=:userType  order by Upper(t.username) asc";
            Query query = session.createQuery(hql).setString("username", inputBean.getLoginUserName())
                                        .setString("password", hash.toLowerCase())
//                    .setString("password", inputBean.getLoginPassword())
                    .setString("userType", "USR");

            laList = (List<User>) query.list();

            if (laList.size() > 0) {
                if (laList.get(0).getUserStatus().equals("ACT")) {
                    des = "";
                } else if (laList.get(0).getUserStatus().equals("NEW")) {
                    des = "new";
                } else if (laList.get(0).getUserStatus().equals("DEACT")) {
                    des = "deact";
                }
            } else {
                des = "error";
            }

        } catch (Exception e) {
            des = "error";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }

        return des;
    }

    public String HashSHA256(String password) throws NoSuchAlgorithmException {
        String hash = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public boolean updateUser(LoginBean inputBean, Systemaudit audit, boolean login) throws Exception {
        Session session = null;
        Transaction txn = null;
        boolean status = true;
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String sql = "from Systemuser as u where u.username =:username";
            Query query = session.createQuery(sql).setString("username", inputBean.getUsername().trim());

            Systemuser u = (Systemuser) query.list().get(0);

//            Mpisystemuser u = (Mpisystemuser) session.get(Mpisystemuser.class, inputBean.getUsername().trim());
            if (u != null) {

                if (login) {
                    u.setLoggeddate(sysDate);//set last logged date only in successfull login
                }
                Status s = new Status();
                s.setStatuscode(inputBean.getStatus());

//                u.setStatus(s);
//                u.setLastupdatedtime(sysDate);
//                u.setNoofinvlidattempt(String.valueOf(inputBean.getAttempts()));

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(u);

                txn.commit();
            } else {
                status = false;
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return status;
    }
}
