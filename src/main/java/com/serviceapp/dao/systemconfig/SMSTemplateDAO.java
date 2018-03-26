/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.SMSTemplateBean;
import com.serviceapp.bean.systemconfig.SMSTemplateInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.TransactionType;
import com.serviceapp.mapping.WebSmsTemplate;
import com.serviceapp.varlist.MessageVarlist;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class SMSTemplateDAO {
    public ArrayList<SMSTemplateBean> getSearchList(SMSTemplateInputBean inputBean, int max, int first) throws Exception {

        ArrayList<SMSTemplateBean> itemDataArray = new ArrayList<SMSTemplateBean>();

        Session session = null;

        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();
            String where = makeWhereclause(inputBean);

            String sqlCount = "select count(messageid) from WebSmsTemplate as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                Query query = session.createQuery("from WebSmsTemplate as u where " + where + " order by u.createdtime desc");

                query.setMaxResults(max);
                query.setFirstResult(first);

                ArrayList<WebSmsTemplate> array = new ArrayList<WebSmsTemplate>();

                array = (ArrayList<WebSmsTemplate>) query.list();

                for (WebSmsTemplate webSmsoutTemplate : array) {

                    SMSTemplateBean smsOutputBean = new SMSTemplateBean();

                    try {

                        smsOutputBean.setMessageId(webSmsoutTemplate.getMessageid().toString());

                    } catch (NullPointerException e) {
                        smsOutputBean.setMessageId("--");
                    }

                    try {

                        smsOutputBean.setTxnType(webSmsoutTemplate.getTransactionType().getDescription());

                    } catch (NullPointerException e) {
                        smsOutputBean.setTxnType("--");
                    }

                    try {

                        smsOutputBean.setDescription(webSmsoutTemplate.getMessage());
                    } catch (NullPointerException e) {
                        smsOutputBean.setDescription("--");
                    }

                    try {
                        smsOutputBean.setCreatetime(webSmsoutTemplate.getCreatedtime().toString().substring(0, 19));

                    } catch (NullPointerException npe) {
                        smsOutputBean.setCreatetime("--");
                    }
                    smsOutputBean.setFullCount(count);
                    itemDataArray.add(smsOutputBean);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }

        }

        return itemDataArray;
    }

    public String makeWhereclause(SMSTemplateInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getMessageIdSearch() != null && !inputBean.getMessageIdSearch().isEmpty()) {

            where += " and lower(u.messageid) like lower('%" + inputBean.getMessageIdSearch() + "%')";
        }

        if (inputBean.getTxnTypeSearch() != null && !inputBean.getTxnTypeSearch().isEmpty()) {
            where += " and lower(u.transactionType.typecode) like lower('%" + inputBean.getTxnTypeSearch() + "%')";
        }

        if (inputBean.getDescriptionSearch() != null && !inputBean.getDescriptionSearch().isEmpty()) {

            where += " and lower(u.message) like lower('%" + inputBean.getDescriptionSearch() + "%')";

        }
        return where;
    }

    public WebSmsTemplate findTemplateById(String messageid) throws Exception {
        WebSmsTemplate webSmsoutTemplate = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from WebSmsTemplate as su where su.messageid=:messageid";

            Query query = session.createQuery(sql);
            query.setString("messageid", messageid.trim());

            webSmsoutTemplate = (WebSmsTemplate) query.list().get(0);

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
        return webSmsoutTemplate;
    }

    public String addUpdateData(SMSTemplateInputBean inputBean, Systemaudit audit) {

        String message = "";
        Session session = null;
        Transaction txn = null;
        String have = "";
        try {

            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            TransactionType tr = (TransactionType) session.get(TransactionType.class, inputBean.getTxnType().trim());

            Date sysDate = CommonDAO.getSystemDate(session);

            WebSmsTemplate webSmsoutTemplate = (WebSmsTemplate) session.get(WebSmsTemplate.class, inputBean.getMessageId());

            if (webSmsoutTemplate != null) {
                String oldValue = webSmsoutTemplate.getMessageid()
                        + "|" + webSmsoutTemplate.getTransactionType().getDescription()
                        + "|" + webSmsoutTemplate.getMessage();

                webSmsoutTemplate.setMessageid(Integer.parseInt(inputBean.getMessageId()));
                webSmsoutTemplate.setTransactionType(tr);
                webSmsoutTemplate.setMessage(inputBean.getDescription());

                String LastUpdateUser = audit.getLastupdateduser();
                webSmsoutTemplate.setLastupdateduser(LastUpdateUser);
                webSmsoutTemplate.setLastupdatedtime(sysDate);

                String newValue = webSmsoutTemplate.getMessageid()
                        + "|" + webSmsoutTemplate.getTransactionType().getDescription()
                        + "|" + webSmsoutTemplate.getMessage();
                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.update(webSmsoutTemplate);

                session.save(audit);
                txn.commit();
            } else {
                message= MessageVarlist.SMS_TEMPLATE_ERROR;
            }

        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();

            }
            message = "Record Doesn't Update";

        } finally {
            try {

                if (session != null) {
                    session.flush();
                    session.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return message;
    }

//    public boolean isExisTransactiontype(String transactiontype) throws Exception {
//        long count = 0;
//        boolean status = true;
//        Session session = null;
//        try {
//
//            session = HibernateInit.sessionFactory.openSession();
//
//            String sql = "select count(u.transactiontype) from WebSmsTemplate as u where u.transactiontype=:transactiontype";
//            Query query = session.createQuery(sql).setString("transactiontype", transactiontype);
//
//            Iterator it = query.iterate();
//
//            while (it.hasNext()) {
//                count = (Long) it.next();
//                if (count > 0) {
//                    status = false;
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return status;
//    }
}
