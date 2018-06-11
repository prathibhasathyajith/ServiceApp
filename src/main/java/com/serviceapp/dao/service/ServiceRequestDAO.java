/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.service;

import com.serviceapp.bean.service.ServiceRequestBean;
import com.serviceapp.bean.service.ServiceRequestInputBean;
import com.serviceapp.bean.service.SummaryBean;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobServiceRequest;
import com.serviceapp.mapping.Status;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class ServiceRequestDAO {

    public ServiceRequestInputBean findServiceReqById(String serviceId) throws Exception {
        ServiceRequestInputBean databean = new ServiceRequestInputBean();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            MobServiceRequest bean = (MobServiceRequest) session.get(MobServiceRequest.class, new Long(serviceId));

            if (bean != null) {

                databean.setServiceId(Long.toString(bean.getServiceId()));
                databean.setCusId(Long.toString(bean.getMobUser().getId()));
                databean.setBassId(Long.toString(bean.getMobBassData().getMobUser().getId()));
                databean.setCusfname(bean.getMobUser().getFirstName());
                databean.setBassfname(bean.getMobBassData().getMobUser().getFirstName());
                databean.setLongitude(Double.toString(bean.getCustLong()));
                databean.setLatitude(Double.toString(bean.getCustLat()));
                databean.setStatus(bean.getStatus().getDescription());
                databean.setCustAddress(bean.getCustAddress());
                databean.setCreatedTime(bean.getCreatedTime().toString());
                databean.setUpdatedTime(bean.getUpdatedTime().toString());
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

    public List<ServiceRequestBean> getSearchList(ServiceRequestInputBean inputBean, int to, int from, String orderBy) throws Exception {
        List<ServiceRequestBean> dataList = new ArrayList<ServiceRequestBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createdTime desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(serviceId) from MobServiceRequest as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from MobServiceRequest u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(to);
                querySearch.setFirstResult(from);

                Iterator it = querySearch.iterate();

                while (it.hasNext()) {
                    ServiceRequestBean serviceReq = new ServiceRequestBean();
                    MobServiceRequest map = (MobServiceRequest) it.next();

                    try {
                        serviceReq.setServiceId(Long.toString(map.getServiceId()));
                    } catch (NullPointerException e) {
                        serviceReq.setServiceId("--");
                    }
                    try {
                        serviceReq.setCusId(Integer.toString(map.getMobUser().getId()));
                    } catch (NullPointerException e) {
                        serviceReq.setCusId("--");
                    }
                    try {
                        serviceReq.setBassId(Integer.toString(map.getMobBassData().getMobUser().getId()));
                    } catch (NullPointerException e) {
                        serviceReq.setBassId("--");
                    }
                    try {
                        serviceReq.setCusfname(map.getMobUser().getFirstName());
                    } catch (NullPointerException e) {
                        serviceReq.setCusfname("--");
                    }
                    try {
                        serviceReq.setBassfname(map.getMobBassData().getMobUser().getFirstName());
                    } catch (NullPointerException e) {
                        serviceReq.setBassfname("--");
                    }
                    try {
                        serviceReq.setStatus(map.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        serviceReq.setStatus("--");
                    }
                    try {
                        serviceReq.setCustAddress(map.getCustAddress());
                    } catch (NullPointerException e) {
                        serviceReq.setCustAddress("--");
                    }
                    try {
                        serviceReq.setLatitude(Double.toString(map.getCustLat()));
                    } catch (NullPointerException e) {
                        serviceReq.setLatitude("--");
                    }
                    try {
                        serviceReq.setLongitude(Double.toString(map.getCustLong()));
                    } catch (NullPointerException e) {
                        serviceReq.setLongitude("--");
                    }
                    try {
                        serviceReq.setUpdatedTime(map.getUpdatedTime().toString());
                    } catch (NullPointerException e) {
                        serviceReq.setUpdatedTime("--");
                    }

                    try {
                        serviceReq.setCreatedTime(map.getCreatedTime().toString());
                    } catch (NullPointerException e) {
                        serviceReq.setCreatedTime("--");
                    }

                    serviceReq.setFullCount(count);
                    dataList.add(serviceReq);
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

    private String makeWhereClause(ServiceRequestInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getCusfname() != null && !inputBean.getCusfname().isEmpty()) {
            where += " and lower(u.mobUser.firstName) like lower('%" + inputBean.getCusfname() + "%')";
        }
        if (inputBean.getBassfname() != null && !inputBean.getBassfname().isEmpty()) {
            where += " and lower(u.mobBassData.mobUser.firstName) like lower('%" + inputBean.getBassfname() + "%')";
        }
        if (inputBean.getCustAddress() != null && !inputBean.getCustAddress().isEmpty()) {
            where += " and lower(u.custAddress) like lower('%" + inputBean.getCustAddress() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatus() + "'";
        }

        return where;
    }

    public List<Status> getStatusList(String statusCode) throws Exception {

        List<Status> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.category =:statuscategorycode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString("statuscategorycode", statusCode);
            statusList = query.list();
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
        return statusList;
    }

    public void getSummary(ServiceRequestInputBean inputBean) throws Exception {

        List<SummaryBean> dataList = new ArrayList<SummaryBean>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sqlSearch = "";
            String sqlSearch_forCount = "";

            String sdat = "2018-05";
            String edat = "2018-06";

            sqlSearch_forCount = "SELECT count(s.service_id) AS count  "
                    + "FROM mob_service_request AS s "
                    + "WHERE s.updated_time > '" + sdat + "' AND s.updated_time <= '" + edat + "' ";

            Query querySearchCount = session.createSQLQuery(sqlSearch_forCount);

            List ObjetctListCount = querySearchCount.list();
            
            System.out.println("cout " +ObjetctListCount.get(0).toString());

            if (ObjetctListCount.size() > 0) {

                sqlSearch = "SELECT "
                        + "ss.description as des, "
                        + "count( s.service_id ) as cnt, "
                        + this.fullCountDate(sdat, edat) + ", "
                        + "CONCAT((count( s.service_id ) /" + this.fullCountDate(sdat, edat) + ")*100,'%') AS percentage "
                        + "FROM mob_service_request AS s "
                        + "LEFT OUTER JOIN `status` AS ss ON s.`status` = ss.status_code "
                        + "WHERE s.updated_time > '" + sdat + "' AND s.updated_time <= '" + edat + "' "
                        + "GROUP BY s.`status` ";
                
                System.out.println("query ---- \n "+ sqlSearch);

                Query querySearch = session.createSQLQuery(sqlSearch);
                List<Object[]> ObjetctList = querySearch.list();

                for (Object[] bean : ObjetctList) {
                    SummaryBean map = new SummaryBean();
                    
                    System.out.println("bean -- " + bean[0].toString());

                    if (bean[0] != null) {
                        map.setStatusDes(bean[0].toString());
                    } else {
                        map.setStatusDes("--");
                    }

                    if (bean[1] != null) {
                        map.setStatusCount(bean[1].toString());
                    } else {
                        map.setStatusCount("--");
                    }

                    if (bean[2] != null) {
                        map.setFullCount(bean[2].toString());
                    } else {
                        map.setFullCount("--");
                    }

                    if (bean[3] != null) {
                        map.setPercentage(bean[3].toString());
                    } else {
                        map.setPercentage("--");
                    }

                    dataList.add(map);
                }

            } else {
                System.out.println("no recoreds");
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

    public String fullCountDate(String sDate, String eDate) {
        String query = "";

        query = "( SELECT count( * ) "
                + "FROM mob_service_request d "
                + "WHERE d.updated_time < '" + eDate + "' "
                + "AND "
                + "d.updated_time >= '" + sDate + "' )  ";

        return query;
    }

}
