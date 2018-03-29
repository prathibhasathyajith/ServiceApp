/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.TermsInputBean;
import com.serviceapp.bean.systemconfig.TermsVersionBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.WebTerms;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import java.util.ArrayList;
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
public class TermsDAO {

    public void getVersionList(TermsInputBean inputBean) throws Exception {

        List<WebTerms> vlist = new ArrayList<WebTerms>();
        List<TermsVersionBean> allvlist = new ArrayList< TermsVersionBean>();
        inputBean.setVersionList(allvlist);
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String hql = "from WebTerms as wt order by Upper(wt.versionNo) asc";
            Query query = session.createQuery(hql);
            vlist = (List<WebTerms>) query.list();

            for (Iterator<WebTerms> it = vlist.iterator(); it.hasNext();) {

                WebTerms term = it.next();
                TermsVersionBean termObj = new TermsVersionBean();
                termObj.setKey(term.getVersionNo());
                termObj.setValue(term.getVersionNo());
                inputBean.getVersionList().add(termObj);
            }

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
    }

    public String updateTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            List<WebTerms> statusACT = new ArrayList<WebTerms>();

            WebTerms u = (WebTerms) session.get(WebTerms.class, inputBean.getVersionno().trim());

            if (u != null) {

                if (!inputBean.getStatus().equals(CommonVarlist.STATUS_DEACTIVE)) {

                    String sql = "from WebTerms as wt where wt.status.statuscode=:statuscode ";
                    Query query = session.createQuery(sql).setString("statuscode", inputBean.getStatus());
                    statusACT = query.list();
                   
                    WebTerms u2 = (WebTerms) session.get(WebTerms.class, statusACT.get(0).getVersionNo());
                    
                    Status st2 = (Status) session.get(Status.class, CommonVarlist.STATUS_DEACTIVE);
                    u2.setStatus(st2);
                    session.update(u2);
                }

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setTerms(inputBean.getDescription());

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
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

    public String addTerms(TermsInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            List<WebTerms> statusACT = new ArrayList<WebTerms>();

            WebTerms ub = (WebTerms) session.get(WebTerms.class, inputBean.getVersionno().trim());
            WebTerms u = new WebTerms();
            

            if (ub == null) {

                if (!inputBean.getStatus().equals(CommonVarlist.STATUS_DEACTIVE)) {

                    String sql = "from WebTerms as wt where wt.status.statuscode=:statuscode ";
                    Query query = session.createQuery(sql).setString("statuscode", inputBean.getStatus());
                    statusACT = query.list();
                   
                    WebTerms u2 = (WebTerms) session.get(WebTerms.class, statusACT.get(0).getVersionNo());
                    
                    Status st2 = (Status) session.get(Status.class, CommonVarlist.STATUS_DEACTIVE);
                    u2.setStatus(st2);
                    session.update(u2);
                }
                
                u.setVersionNo(inputBean.getVersionno().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                u.setTerms(inputBean.getDescription());

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(u);

                txn.commit();
            } else {
                message = MessageVarlist.COMMON_ALREADY_EXISTS;
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
}
