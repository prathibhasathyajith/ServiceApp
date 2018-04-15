/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.FaqBean;
import com.serviceapp.bean.systemconfig.FaqInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobFaq;
import com.serviceapp.mapping.MobFaqSection;
import com.serviceapp.mapping.MobFaqType;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
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
public class FaqDAO {
    private String makeWhereClause(FaqInputBean inputBean) {
        String where = "1=1 ";

        if (inputBean.getId() != null && !inputBean.getId().trim().isEmpty()) {
            where += "and f.id = '" + inputBean.getId().trim() + "'";
        }
        if (inputBean.getSection() != null && !inputBean.getSection().trim().isEmpty()) {
            where += "and f.mobFaqSection.sectionType = '" + inputBean.getSection().trim() + "'";
        }
//        if (inputBean.getSection() != null && !inputBean.getSection().trim().isEmpty()) {
//            where += "and lower(f.section) like lower('%" + inputBean.getSection().trim() + "%') ";
//        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += "and f.status.statuscode = '" + inputBean.getStatus().trim() + "'";
        }
        if (inputBean.getQuestion() != null && !inputBean.getQuestion().trim().isEmpty()) {
            where += "and lower(f.question) like lower('%" + inputBean.getQuestion().trim() + "%') ";
        }
        if (inputBean.getAnswer() != null && !inputBean.getAnswer().trim().isEmpty()) {
            where += "and lower(f.answer) like lower('%" + inputBean.getAnswer().trim() + "%') ";
        }
        return where;
    }

    public List<FaqBean> getSearchList(FaqInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<FaqBean> dataList = new ArrayList<FaqBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by f.createdtime desc ";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MobFaq as f where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from MobFaq as f where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    FaqBean mobFaqBean = new FaqBean();
                    MobFaq mobFaq = (MobFaq) it.next();

                    try {
                        mobFaqBean.setId(mobFaq.getId().toString());
                    } catch (NullPointerException e) {
                        mobFaqBean.setId("--");
                    }
//                    try {
//                        mobFaqBean.setType(mobFaq.getMobFaqSection().getDescription());
//                    } catch (NullPointerException npe) {
//                        mobFaqBean.setType("--");
//                    }

                    try {
                        mobFaqBean.setSection(mobFaq.getMobFaqSection().getDescription());
                    } catch (NullPointerException e) {
                        mobFaqBean.setSection("--");
                    }

                    try {
                        mobFaqBean.setStatus(mobFaq.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        mobFaqBean.setStatus(null);
                    }

                    try {
                        mobFaqBean.setQuestion(mobFaq.getQuestion().toString());
                    } catch (NullPointerException e) {
                        mobFaqBean.setQuestion("--");
                    }

                    try {
                        mobFaqBean.setAnswer(mobFaq.getAnswer().toString());
                    } catch (NullPointerException e) {
                        mobFaqBean.setAnswer("--");
                    }

                    try {
                        if (mobFaq.getCreatedtime().toString() != null && !mobFaq.getCreatedtime().toString().isEmpty()) {
                            mobFaqBean.setCreatedtime(mobFaq.getCreatedtime().toString());
                        } else {
                            mobFaqBean.setCreatedtime("--");
                        }
//                        mobFaqBean.setCreatedtime(mobFaq.getCreatedtime());
                    } catch (NullPointerException e) {
                        mobFaqBean.setCreatedtime(null);
                    }
                    mobFaqBean.setFullCount(count);
                    dataList.add(mobFaqBean);
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

    public static String insertFaq(FaqInputBean faqbean, Systemaudit audit) throws Exception {
        String message = "";
        Session session = null;
        Transaction txn = null;

        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            MobFaq mobFaq = new MobFaq();

            String sql = "select max(id) from MobFaq";

            Query query = session.createQuery(sql);
            Iterator itCount = query.iterate();

            MobFaqSection mt = (MobFaqSection) session.get(MobFaqSection.class, faqbean.getSection().trim());
            mobFaq.setMobFaqSection(mt);

//            mobFaq.setSection(faqbean.getSection());

            Status st = (Status) session.get(Status.class, faqbean.getStatus().trim());
            mobFaq.setStatus(st);

            mobFaq.setQuestion(faqbean.getQuestion());
            mobFaq.setAnswer(faqbean.getAnswer());
            mobFaq.setLastupdateduser(audit.getLastupdateduser());
            mobFaq.setCreatedtime(sysDate);
            mobFaq.setLastupdatedtime(sysDate);

            int id = Integer.parseInt(String.valueOf(itCount.next())) + 1;
            String sId = String.valueOf(id);

            String newValue = sId
                    + "|" + mobFaq.getMobFaqSection().getDescription()
//                    + "|" + mobFaq.getSection()
                    + "|" + mobFaq.getStatus().getDescription()
                    + "|" + mobFaq.getQuestion()
                    + "|" + mobFaq.getAnswer();

            audit.setNewvalue(newValue);

            String auditDes = audit.getDescription();
            audit.setDescription("FAQ " + sId + " added " + auditDes);
            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);
            audit.setLastupdateduser(audit.getLastupdateduser());

            session.save(audit);
            session.save(mobFaq);
            txn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (txn != null) {
                txn.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return message;
    }

    public String deleteFaq(FaqInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            MobFaq mobfaq = (MobFaq) session.get(MobFaq.class, Integer.parseInt(inputBean.getId().trim()));

            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);

            if (mobfaq != null) {
                session.save(audit);
                session.delete(mobfaq);
            } else {
                message = MessageVarlist.COMMON_NOT_DELETE;
            }

            txn.commit();

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

    public MobFaq getId(String id) throws Exception {
        MobFaq mobfaq = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MobFaq as f where f.id=:id";

            Query query = session.createQuery(sql);
            query.setString("id", id);

            mobfaq = (MobFaq) query.list().get(0);

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
        return mobfaq;
    }

    public MobFaq getFaqById(String id) throws Exception {
        MobFaq mobfaq = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MobFaq as f where f.id=:id";

            Query query = session.createQuery(sql);
            query.setString("id", id);

            mobfaq = (MobFaq) query.list().get(0);

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
        return mobfaq;
    }

    public String updateFaq(FaqInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";

        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MobFaq mobfaq = (MobFaq) session.get(MobFaq.class, Integer.parseInt(inputBean.getId().trim()));

            if (mobfaq != null) {

                String oldValue = mobfaq.getId()
                        + "|" + mobfaq.getMobFaqSection().getDescription()
//                        + "|" + mobfaq.getSection()
                        + "|" + mobfaq.getStatus().getDescription()
                        + "|" + mobfaq.getQuestion()
                        + "|" + mobfaq.getAnswer();

                mobfaq.setId(Integer.parseInt(inputBean.getId().trim()));

                MobFaqType mobFaqType = new MobFaqType();
                mobFaqType.setCode(inputBean.getType());

//                mobfaq.setMobFaqSection(inputBean.getSection().trim());

//                mobfaq.setMobFaqType(mobFaqType);
                
                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                mobfaq.setStatus(st);

                mobfaq.setQuestion(inputBean.getQuestion().trim());
                mobfaq.setAnswer(inputBean.getAnswer().trim());

                MobFaqSection mt = (MobFaqSection) session.get(MobFaqSection.class, inputBean.getSection().trim());
                mobfaq.setMobFaqSection(mt);

                mobfaq.setLastupdateduser(audit.getLastupdateduser());
                mobfaq.setLastupdatedtime(sysDate);

                String newValue = mobfaq.getId()
                        + "|" + mobfaq.getMobFaqSection().getDescription()
//                        + "|" + mobfaq.getSection()
                        + "|" + mobfaq.getStatus().getDescription()
                        + "|" + mobfaq.getQuestion()
                        + "|" + mobfaq.getAnswer();

                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.update(mobfaq);

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
}
