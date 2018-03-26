/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.TransactionTypeBean;
import com.serviceapp.bean.systemconfig.TransactionTypeInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.TransactionType;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class TransactionTypeDAO {
    public String insertTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if ((TransactionType) session.get(TransactionType.class, inputBean.getTransactiontypecode().trim()) == null) {
                txn = session.beginTransaction();

                TransactionType tt = new TransactionType();
                tt.setTypecode(inputBean.getTransactiontypecode().trim());
                tt.setDescription(inputBean.getDescription().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                tt.setStatus(st);

                String newV = tt.getTypecode() + "|"
                        + tt.getDescription() + "|"
                        + tt.getStatus().getDescription() + "|";

                audit.setNewvalue(newV);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.save(tt);

                txn.commit();
            } else {

                long count = 0;

                String sqlCount = "select count(typecode) from TransactionType as u where u.status=:statuscode AND u.typecode=:typecode ";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarlist.STATUS_DELETE)
                        .setString("typecode", inputBean.getTransactiontypecode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getTransactiontypecode().trim();
                } else {
                    message = MessageVarlist.COMMON_ALREADY_EXISTS;
                }
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

    public List<TransactionTypeBean> getSearchList(TransactionTypeInputBean inputBean, int max, int first, String orderBy) throws Exception {

        List<TransactionTypeBean> dataList = new ArrayList<TransactionTypeBean>();
        Session session = null;
        try {

            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by TT.TYPECODE desc ";
            }
            String where = this.makeWhereClause(inputBean);

            BigDecimal count = new BigDecimal(0);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(TT.TYPECODE) from transaction_type TT where " + where;

            Query queryCount = session.createSQLQuery(sqlCount);

            List countList = queryCount.list();
            count = (BigDecimal) countList.get(0);

            if (count.longValue() > 0) {

                String sqlSearch = " SELECT * from ( select "
                        + "TT.TYPECODE, "//0
                        + "TT.DESCRIPTION, "//1
                        + "ST.description AS STATUSDESC "//2
                        + "row_number() over (" + orderBy + ") as r "
                        + "from "
                        + "transaction_type TT, "
                        + "status ST, "
                        + "status ST1 "
                        + "where "
                        + "TT.STATUS=ST.status_code AND "
                        + "" + where + ") where r > " + first + " and r<= " + max;

                List<Object[]> chequeList = (List<Object[]>) session.createSQLQuery(sqlSearch).list();

                for (Object[] ttBean : chequeList) {

                    TransactionTypeBean trnsactionTypeBean = new TransactionTypeBean();

                    try {
                        trnsactionTypeBean.setTransactiontypecode(ttBean[0].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setTransactiontypecode("--");
                    }
                    try {
                        trnsactionTypeBean.setDescription(ttBean[1].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setDescription("--");
                    }
                    try {
                        trnsactionTypeBean.setStatus(ttBean[2].toString());
                    } catch (NullPointerException npe) {
                        trnsactionTypeBean.setStatus("--");
                    }

                    trnsactionTypeBean.setFullCount(count.longValue());

                    dataList.add(trnsactionTypeBean);
                }
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
        return dataList;
    }

    public List<TransactionTypeBean> getSearchListHQL(TransactionTypeInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<TransactionTypeBean> dataList = new ArrayList<TransactionTypeBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.typecode desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from TransactionType as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from TransactionType u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                CommonDAO daao = new CommonDAO();
                while (it.hasNext()) {
                    TransactionTypeBean ttype = new TransactionTypeBean();
                    TransactionType txntype = (TransactionType) it.next();

                    try {
                        ttype.setTransactiontypecode(txntype.getTypecode());
                    } catch (NullPointerException e) {
                        ttype.setTransactiontypecode("--");
                    }
                    try {
                        ttype.setDescription(txntype.getDescription());
                    } catch (NullPointerException e) {
                        ttype.setDescription("--");
                    }

                    try {
                        ttype.setStatus(txntype.getStatus().getDescription());
                    } catch (Exception e) {
                        ttype.setStatus("--");
                    }

                    try {
                        ttype.setDescription_receiver(txntype.getDescriptionReceiver());
                    } catch (Exception e) {
                        ttype.setDescription_receiver("--");
                    }

                    ttype.setFullCount(count);
                    dataList.add(ttype);
                }
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
        return dataList;
    }

    private String makeWhereClause(TransactionTypeInputBean inputBean) throws Exception {

        String where = "1=1";

        if ((inputBean.getS_transactiontypecode() == null || inputBean.getS_transactiontypecode().isEmpty())
                && (inputBean.getS_description() == null || inputBean.getS_description().isEmpty())
                && (inputBean.getS_status() == null || inputBean.getS_status().isEmpty())
                && (inputBean.getS_description_receiver() == null || inputBean.getS_description_receiver().isEmpty())) {

        } else {

            if (inputBean.getS_transactiontypecode() != null && !inputBean.getS_transactiontypecode().isEmpty()) {
                where += " and u.typecode like '%" + inputBean.getS_transactiontypecode() + "%'";
            }
            if (inputBean.getS_description() != null && !inputBean.getS_description().isEmpty()) {
                where += " and lower(u.description) like lower('%" + inputBean.getS_description() + "%')";
            }
            if (inputBean.getS_status() != null && !inputBean.getS_status().isEmpty()) {
                where += " and u.status.statuscode='" + inputBean.getS_status() + "'";
            }
            if (inputBean.getS_description_receiver() != null && !inputBean.getS_description_receiver().isEmpty()) {
                where += " and lower(u.descriptionReceiver) like lower('%" + inputBean.getS_description_receiver() + "%')";
            }

        }
        return where;
    }

    public TransactionType findTransactionTypeById(String ttCode) throws Exception {
        TransactionType tt = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from TransactionType as u where u.typecode=:typecode";
            Query query = session.createQuery(sql).setString("typecode", ttCode);
            tt = (TransactionType) query.list().get(0);

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
        return tt;

    }

    public String updateTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            TransactionType u = (TransactionType) session.get(TransactionType.class, inputBean.getTransactiontypecode().trim());

            if (u != null) {

                String oldV = u.getTypecode() + "|"
                        + u.getDescription() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getDescriptionReceiver() + "|";

                u.setDescription(inputBean.getDescription().trim());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);
                u.setDescriptionReceiver(inputBean.getDescription_receiver());

                String newV = u.getTypecode() + "|"
                        + u.getDescription() + "|"
                        + u.getStatus().getDescription() + "|"
                        + u.getDescriptionReceiver() + "|";

                audit.setOldvalue(oldV);
                audit.setNewvalue(newV);
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

    public String deleteTransactionType(TransactionTypeInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            TransactionType u = (TransactionType) session.get(TransactionType.class, inputBean.getTransactiontypecode().trim());
            if (u != null) {

                long count = 0;

                String sqlCount = "select count(typecode) from TransactionType as u where u.typecode=:typecode";
                Query queryCount = session.createQuery(sqlCount).setString("typecode", inputBean.getTransactiontypecode().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(u);
                    txn.commit();

                }
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

}
