/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.service;

import com.serviceapp.bean.service.ServiceCategoryBean;
import com.serviceapp.bean.service.ServiceCategoryInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Roles;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
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
 * @author prathibha_s
 */
public class ServiceCategoryDAO {

    public List<ServiceCategoryBean> getSearchList(ServiceCategoryInputBean inputBean, int rows, int from, String orderBy) throws Exception {
        List<ServiceCategoryBean> dataList = new ArrayList<ServiceCategoryBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.description desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(roleType) from Roles as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from Roles u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(rows);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {
                    ServiceCategoryBean serviceCat = new ServiceCategoryBean();
                    Roles map = (Roles) it.next();

                    try {
                        serviceCat.setRoleType(map.getRoleType());
                    } catch (NullPointerException e) {
                        serviceCat.setRoleType("--");
                    }
                    try {
                        serviceCat.setDescription(map.getDescription());
                    } catch (NullPointerException e) {
                        serviceCat.setDescription("--");
                    }
                    try {
                        serviceCat.setStatus(map.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        serviceCat.setStatus("--");
                    }

                    serviceCat.setFullCount(count);
                    dataList.add(serviceCat);
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

    private String makeWhereClause(ServiceCategoryInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getRoleType() != null && !inputBean.getRoleType().isEmpty()) {
            where += " and u.roleType= '" + inputBean.getRoleType() + "'";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and u.status.statuscode= '" + inputBean.getStatus() + "'";
        }

        return where;
    }

    public Roles getServiceCatByRole(String roleType) throws Exception {
        Roles role = null;
        Session session = null;

        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Roles as u where u.roleType=:roleType  ";
            Query query = session.createQuery(sql).setString("roleType", roleType);

            role = (Roles) query.list().get(0);

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
        return role;
    }

    public String insertServiceCategory(ServiceCategoryInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Roles role = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            role = (Roles) session.get(Roles.class, inputBean.getRoleType());

            if (role == null) {

                role = new Roles();

                role.setRoleType(inputBean.getRoleType());
                Status st = (Status) session.get(Status.class, inputBean.getStatus());
                role.setStatus(st);
                role.setDescription(inputBean.getDescription());

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                String newValue = role.getRoleType() + "|"
                        + role.getDescription() + "|"
                        + role.getStatus().getDescription();

                audit.setNewvalue(newValue);

                session.save(audit);
                session.save(role);

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

    public String updateServiceCategory(ServiceCategoryInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Roles role = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            role = (Roles) session.get(Roles.class, inputBean.getRoleType());

            if (role != null) {
                String oldValue = role.getRoleType() + "|"
                        + role.getDescription() + "|"
                        + role.getStatus().getDescription();

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                role.setStatus(st);
                role.setDescription(inputBean.getDescription());

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                String newValue = role.getRoleType() + "|"
                        + role.getDescription() + "|"
                        + role.getStatus().getDescription();

                audit.setNewvalue(newValue);
                audit.setOldvalue(oldValue);

                session.save(audit);
                session.update(role);

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

    public String deleteServiceCategory(ServiceCategoryInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            // must delete from all foreign keys from all tables
            Roles role = (Roles) session.get(Roles.class, inputBean.getRoleType());
            //Roles role = (Roles) this.getServiceCatByRole(inputBean.getRoleType());
            if (role != null) {
                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
                session.delete(role);

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
