/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.service;

import com.serviceapp.bean.service.ScheduledRequestBean;
import com.serviceapp.bean.service.ScheduledRequestInputBean;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobScheduledServiceRequest;
import com.serviceapp.mapping.Roles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class ScheduledRequestDAO {

    public List<ScheduledRequestBean> getSearchList(ScheduledRequestInputBean inputBean, int to, int from, String orderBy) throws Exception {
        List<ScheduledRequestBean> dataList = new ArrayList<ScheduledRequestBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createdTime desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MobScheduledServiceRequest as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from MobScheduledServiceRequest u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(to);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {
                    ScheduledRequestBean scheduledReq = new ScheduledRequestBean();
                    MobScheduledServiceRequest map = (MobScheduledServiceRequest) it.next();

                    try {
                        scheduledReq.setId(Long.toString(map.getId()));
                    } catch (NullPointerException e) {
                        scheduledReq.setId("--");
                    }
                    try {
                        scheduledReq.setCusId(Integer.toString(map.getMobUser().getId()));
                    } catch (NullPointerException e) {
                        scheduledReq.setCusId("--");
                    }
                    try {
                        scheduledReq.setBassType(map.getRoles().getDescription());
                    } catch (NullPointerException e) {
                        scheduledReq.setBassType("--");
                    }
                    try {
                        scheduledReq.setScheduledDate(map.getScheduledDateTime().toString());
                    } catch (NullPointerException e) {
                        scheduledReq.setScheduledDate("--");
                    }
                    try {
                        scheduledReq.setLatitude(map.getLatitude().toString());
                    } catch (NullPointerException e) {
                        scheduledReq.setLatitude("--");
                    }
                    try {
                        scheduledReq.setLongitude(map.getLongitude().toString());
                    } catch (NullPointerException e) {
                        scheduledReq.setLongitude("--");
                    }
                    try {
                        scheduledReq.setDescription(map.getDescription());
                    } catch (NullPointerException e) {
                        scheduledReq.setDescription("--");
                    }
                    try {
                        scheduledReq.setAddress(map.getAddress());
                    } catch (NullPointerException e) {
                        scheduledReq.setAddress("--");
                    }
                    try {
                        scheduledReq.setCreatedTime(map.getCreatedTime().toString());
                    } catch (NullPointerException e) {
                        scheduledReq.setCreatedTime("--");
                    }

                    scheduledReq.setFullCount(count);
                    dataList.add(scheduledReq);
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

    public ScheduledRequestInputBean findScheduledReqById(String id) throws Exception {
        ScheduledRequestInputBean databean = new ScheduledRequestInputBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            MobScheduledServiceRequest bean = (MobScheduledServiceRequest) session.get(MobScheduledServiceRequest.class, new Long(id));

            if (bean != null) {

                databean.setFname(bean.getMobUser().getFirstName());
                databean.setLname(bean.getMobUser().getLastName());
                databean.setEmail(bean.getMobUser().getEmail());
                databean.setMobile(bean.getMobUser().getMobile());
                databean.setNic(bean.getMobUser().getNic());

                databean.setId(Long.toString(bean.getId()));
                databean.setCusId(Long.toString(bean.getMobUser().getId()));
                databean.setBassType(bean.getRoles().getDescription());

                databean.setLatitude(bean.getLatitude().toString());
                databean.setLongitude(bean.getLongitude().toString());
                databean.setAddress(bean.getAddress());
                databean.setDescription(bean.getDescription());
                databean.setScheduledDate(bean.getScheduledDateTime().toString());
                databean.setCreatedTime(bean.getCreatedTime().toString());
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

    private String makeWhereClause(ScheduledRequestInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getCusId() != null && !inputBean.getCusId().isEmpty()) {
            where += " and u.mobUser.id= '" + inputBean.getCusId() + "'";
        }
        if (inputBean.getBassType() != null && !inputBean.getBassType().isEmpty()) {
            where += " and u.roles.roleType= '" + inputBean.getBassType() + "'";
        }
        if (inputBean.getLatitude() != null && !inputBean.getLatitude().isEmpty()) {
            where += " and u.latitude= '" + inputBean.getLatitude() + "'";
        }
        if (inputBean.getLongitude() != null && !inputBean.getLongitude().isEmpty()) {
            where += " and u.longitude= '" + inputBean.getLongitude() + "'";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().isEmpty()) {
            where += " and lower(u.description) like lower('%" + inputBean.getDescription() + "%')";
        }
        if (inputBean.getAddress() != null && !inputBean.getAddress().isEmpty()) {
            where += " and lower(u.address) like lower('%" + inputBean.getAddress() + "%')";
        }
        //scheduled time
        if (inputBean.getFdate() != null && !inputBean.getFdate().isEmpty()) {
            where += " and u.scheduledDateTime >= '" + inputBean.getFdate() + "' ";
        }
        if (inputBean.getTdate() != null && !inputBean.getTdate().isEmpty()) {
            where += " and u.scheduledDateTime < '" + inputBean.getTdate() + "' ";
        }

        return where;
    }
    
    public List<Roles> getRoleList() throws Exception {
        
        List<Roles> roleList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Roles s order by Upper(s.description) asc";
            Query query = session.createQuery(sql);
            roleList = query.list();
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
        return roleList;
    }

}
