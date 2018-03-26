/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.EmailTemplateBean;
import com.serviceapp.bean.systemconfig.EmailTemplateInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.TransactionType;
import com.serviceapp.mapping.WebEmailTemplate;
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
public class EmailTemplateDAO {

    public ArrayList<EmailTemplateBean> getEmail(EmailTemplateInputBean inputBean, int max, int first) {
        ArrayList<EmailTemplateBean> itemDataArray = new ArrayList<EmailTemplateBean>();

        Session session = null;

        try {

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();
            String where = makeWhereclause(inputBean);

            String sqlCount = "select count(messageid) from WebEmailTemplate as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                Query query = session.createQuery("from WebEmailTemplate as u where " + where + " order by u.createdtime desc");

                query.setMaxResults(max);
                query.setFirstResult(first);

                ArrayList<WebEmailTemplate> array = new ArrayList<WebEmailTemplate>();

                array = (ArrayList<WebEmailTemplate>) query.list();

                for (WebEmailTemplate webEmailtemplate : array) {

                    EmailTemplateBean emailTemplateBean = new EmailTemplateBean();

                    try {

                        emailTemplateBean.setMessageid(webEmailtemplate.getMessageid().toString());

                    } catch (NullPointerException e) {
                        emailTemplateBean.setMessageid("--");
                    }

                    try {

                        emailTemplateBean.setSubject(webEmailtemplate.getSubject());

                    } catch (NullPointerException e) {
                        emailTemplateBean.setSubject("--");
                    }

                    try {

                        emailTemplateBean.setTransactiontype(webEmailtemplate.getTransactionType().getDescription());
                    } catch (NullPointerException e) {
                        emailTemplateBean.setTransactiontype("--");
                    }

                    try {
                        emailTemplateBean.setCreatedtime(webEmailtemplate.getCreatedtime().toString().substring(0, 19));

                    } catch (NullPointerException npe) {
                        emailTemplateBean.setCreatedtime("--");
                    }
                    emailTemplateBean.setFullCount(count);
                    itemDataArray.add(emailTemplateBean);

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

    public String makeWhereclause(EmailTemplateInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getS_messageid() != null && !inputBean.getS_messageid().isEmpty()) {

            where += " and lower(u.messageid) like lower('%" + inputBean.getS_messageid() + "%')";
        }

        if (inputBean.getS_subject() != null && !inputBean.getS_subject().isEmpty()) {
            where += " and lower(u.subject) like lower('%" + inputBean.getS_subject() + "%')";
        }

        if (inputBean.getTransactiontypesearch() != null && !inputBean.getTransactiontypesearch().isEmpty()) {
            where += " and u.transactionType.typecode = '" + inputBean.getTransactiontypesearch() + "'";

        }
        return where;
    }

    public WebEmailTemplate getEmailById(String messageid) throws Exception {
        WebEmailTemplate webEmailtemplate = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from WebEmailTemplate as su where su.messageid=:messageid";

            Query query = session.createQuery(sql);
            query.setString("messageid", messageid.trim());

            webEmailtemplate = (WebEmailTemplate) query.list().get(0);

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
        return webEmailtemplate;
    }

    public String addUpdateData(EmailTemplateInputBean inputBean, Systemaudit audit) {

        String message = "";
        Session session = null;
        Transaction txn = null;
        String have = "";
        try {

            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            TransactionType tr = (TransactionType) session.get(TransactionType.class, inputBean.getTransactiontype().trim());

            Date sysDate = CommonDAO.getSystemDate(session);

            WebEmailTemplate webEmailtemplate = (WebEmailTemplate) session.get(WebEmailTemplate.class, Integer.parseInt(inputBean.getMessageid()));

            if (webEmailtemplate != null) {

                String oldValue = webEmailtemplate.getMessageid()
                        + "|" + webEmailtemplate.getTransactionType().getDescription()
                        + "|" + webEmailtemplate.getSubject()
                        + "|" + webEmailtemplate.getMessage();

                webEmailtemplate.setSubject(inputBean.getSubject());
                webEmailtemplate.setTransactionType(tr);
                webEmailtemplate.setSubject(inputBean.getSubject());
                webEmailtemplate.setMessage(inputBean.getMessageEmail());

                String LastUpdateUser = audit.getLastupdateduser();
                webEmailtemplate.setLastupdateduser(LastUpdateUser);
                webEmailtemplate.setLastupdatedtime(sysDate);

                String newValue = webEmailtemplate.getMessageid()
                        + "|" + webEmailtemplate.getTransactionType().getDescription()
                        + "|" + webEmailtemplate.getSubject()
                        + "|" + webEmailtemplate.getMessage();
                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.update(webEmailtemplate);

                session.save(audit);
                txn.commit();

            } else {
                message = MessageVarlist.EMAIL_TEMPLATE_ERROR;
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

}
