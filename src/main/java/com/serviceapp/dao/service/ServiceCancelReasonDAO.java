/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.service;

import com.serviceapp.bean.service.ServiceCancelReasonBean;
import com.serviceapp.bean.service.ServiceCancelReasonInputBean;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobServiceCancelReasons;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class ServiceCancelReasonDAO {

    public List<ServiceCancelReasonBean> getSearchList(ServiceCancelReasonInputBean inputBean, int to, int from, String orderBy) throws Exception {
        List<ServiceCancelReasonBean> dataList = new ArrayList<ServiceCancelReasonBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createdTime desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(serviceId) from MobServiceCancelReasons as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from MobServiceCancelReasons u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(to);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {
                    ServiceCancelReasonBean cancelReason = new ServiceCancelReasonBean();
                    MobServiceCancelReasons map = (MobServiceCancelReasons) it.next();

                    try {
                        cancelReason.setServiceId(Long.toString(map.getServiceId()));
                    } catch (NullPointerException e) {
                        cancelReason.setServiceId("--");
                    }
                    try {
                        cancelReason.setInitUser(Integer.toString(map.getMobUser().getId()));
                    } catch (NullPointerException e) {
                        cancelReason.setInitUser("--");
                    }
                    try {
                        cancelReason.setReason(map.getReason());
                    } catch (NullPointerException e) {
                        cancelReason.setReason("--");
                    }
                    try {
                        cancelReason.setCreatedTime(map.getCreatedTime().toString());
                    } catch (NullPointerException e) {
                        cancelReason.setCreatedTime("--");
                    }

                    cancelReason.setFullCount(count);
                    dataList.add(cancelReason);
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

    private String makeWhereClause(ServiceCancelReasonInputBean inputBean) {
        String where = "1=1";

        System.out.println("fdate - " + inputBean.getFdate());
        System.out.println("tdate - " + inputBean.getTdate());

        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if ((inputBean.getFdate() == null || inputBean.getFdate().isEmpty()) && (inputBean.getTdate() == null || inputBean.getTdate().isEmpty())) {
            System.out.println("-----------");
            inputBean.setFdate(dateformat.format(date));
            inputBean.setTdate(dateformat.format(date));
        }

        System.out.println("fdate - " + inputBean.getFdate());
        System.out.println("tdate - " + inputBean.getTdate());

        if ((inputBean.getFdate() == null || inputBean.getFdate().isEmpty())
                && (inputBean.getTdate() == null || inputBean.getTdate().isEmpty())
                && (inputBean.getServiceId() == null || inputBean.getServiceId().isEmpty())
                && (inputBean.getInitUser() == null || inputBean.getInitUser().isEmpty())
                && (inputBean.getReason() == null || inputBean.getReason().isEmpty())) {

        } else {

            if (inputBean.getServiceId() != null && !inputBean.getServiceId().isEmpty()) {
                where += " and u.serviceId= '" + inputBean.getServiceId() + "'";
            }
            if (inputBean.getInitUser() != null && !inputBean.getInitUser().isEmpty()) {
                where += " and u.mobUser.id= '" + inputBean.getInitUser() + "'";
            }
            if (inputBean.getReason() != null && !inputBean.getReason().isEmpty()) {
                where += " and lower(u.reason) like lower('%" + inputBean.getReason() + "%')";
            }
            if (inputBean.getFdate() != null && !inputBean.getFdate().isEmpty()) {
                where += " and u.createdTime >= '" + inputBean.getFdate() + "' ";
            }
            if (inputBean.getTdate() != null && !inputBean.getTdate().isEmpty()) {
                where += " and u.createdTime < '" + inputBean.getTdate() + "' ";
            }

        }
        return where;
    }

    public ServiceCancelReasonInputBean findResonDataById(String serviceId) throws Exception {

        ServiceCancelReasonInputBean databean = new ServiceCancelReasonInputBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            MobServiceCancelReasons bean = (MobServiceCancelReasons) session.get(MobServiceCancelReasons.class, new Long(serviceId));

            if (bean != null) {
                
                System.out.println("fname " + bean.getMobUser().getFirstName());
                
                databean.setFname(bean.getMobUser().getFirstName());
                databean.setLname(bean.getMobUser().getLastName());
                databean.setEmail(bean.getMobUser().getEmail());
                databean.setMobile(bean.getMobUser().getMobile());
                databean.setNic(bean.getMobUser().getNic());
                databean.setReason(bean.getReason());
                databean.setCreatedTime(bean.getCreatedTime().toString());
                databean.setServiceId(Long.toString(bean.getServiceId()));
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
        return databean;
    }

}
