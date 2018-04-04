/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.cusmanagement;

import com.serviceapp.bean.cusmanagement.CustomerMgtInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobUser;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class CustomerMgtDAO {

    public String updateCustomer(CustomerMgtInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";
        String imageName = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MobUser u = (MobUser) session.get(MobUser.class, Integer.parseInt("32"));

            if (u != null) {

//                String oldValue = u.getCode()
//                        + "|" + u.getName()
//                        + "|" + u.getStatus().getDescription()
//                        + "|" + u.getColour();
//
//                u.setName(inputBean.getName().trim());
//                u.setColour(inputBean.getColour().trim());

                /**
                 * insert web logo
                 */
                try {
                    if (inputBean.getOwnImage().length() != 0) {

                        imageName = inputBean.getOwnImageFileName();

                        File OwnImageFile = inputBean.getOwnImage();
                        byte[] bLogoWebFile = new byte[(int) OwnImageFile.length()];
                        try {
                            fileInputStream = new FileInputStream(OwnImageFile);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u.setImage(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

//                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
//                u.setStatus(st);
//
//                u.setLastupdateduser(audit.getLastupdateduser());
//                u.setLastupdatedtime(sysDate);
//
//                String newValue = imageName
//                        + "|" + u.getCode()
//                        + "|" + u.getName()
//                        + "|" + u.getStatus().getDescription()
//                        + "|" + u.getColour();
//
//                audit.setOldvalue(oldValue);
//                audit.setNewvalue(newValue);
//                audit.setCreatetime(sysDate);
//                audit.setLastupdatedtime(sysDate);
//
//                session.save(audit);
                session.update(u);

                txn.commit();

            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return message;
    }
    
    public MobUser findMobUserById(int userid) throws Exception {

        MobUser mobUser = null;
        Session session = null;

        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MobUser as u where u.id=:id";
            Query query = session.createQuery(sql).setInteger("id", userid);
            mobUser = (MobUser) query.list().get(0);

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
        return mobUser;

    }
    
}
