/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.cusmanagement;

import com.serviceapp.bean.cusmanagement.SuggestedUserBean;
import com.serviceapp.bean.cusmanagement.SuggestedUserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobSuggestedUser;
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
public class SuggestedUserDAO {
    
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
    
    public List<SuggestedUserBean> getSearchList(SuggestedUserInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<SuggestedUserBean> dataList = new ArrayList<SuggestedUserBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.createdTime desc ";
            }
            String where = this.makeWhereClause(inputBean);
            
            long count = 0;
            
            session = HibernateInit.sessionFactory.openSession();
            
            String sqlCount = "select count(id) from MobSuggestedUser as u where " + where;
            Query queryCount = session.createQuery(sqlCount);
            
            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from MobSuggestedUser u where " + where + orderBy;
                
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);
                
                Iterator it = querySearch.iterate();
                CommonDAO daao = new CommonDAO();
                while (it.hasNext()) {
                    SuggestedUserBean sgstUser = new SuggestedUserBean();
                    MobSuggestedUser map = (MobSuggestedUser) it.next();
                    
                    try {
                        sgstUser.setId(map.getId().toString());
                    } catch (NullPointerException e) {
                        sgstUser.setId("--");
                    }
                    try {
                        sgstUser.setFirstName(map.getFirstName());
                    } catch (NullPointerException e) {
                        sgstUser.setFirstName("--");
                    }
                    
                    try {
                        sgstUser.setLastName(map.getLastName());
                    } catch (NullPointerException e) {
                        sgstUser.setLastName("--");
                    }
                    
                    try {
                        sgstUser.setMobile(map.getMobile());
                    } catch (NullPointerException e) {
                        sgstUser.setMobile("--");
                    }
                    
                    try {
                        sgstUser.setEmail(map.getEmail());
                    } catch (NullPointerException e) {
                        sgstUser.setEmail("--");
                    }
                    
                    try {
                        sgstUser.setStatus(map.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        sgstUser.setStatus("--");
                    }
                    
                    try {
                        sgstUser.setArea(map.getArea());
                    } catch (NullPointerException e) {
                        sgstUser.setArea("--");
                    }
                    
                    try {
                        sgstUser.setServiceRole(map.getRoles().getDescription());
                    } catch (NullPointerException e) {
                        sgstUser.setServiceRole("--");
                    }
                    
                    try {
                        sgstUser.setFirstName_user(map.getMobUser().getFirstName());
                    } catch (NullPointerException e) {
                        sgstUser.setServiceRole("--");
                    }
                    
                    try {
                        sgstUser.setCreatedTime(map.getCreatedTime().toString());
                    } catch (NullPointerException e) {
                        sgstUser.setCreatedTime("--");
                    }
                    
                    sgstUser.setFullCount(count);
                    dataList.add(sgstUser);
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
    
    private String makeWhereClause(SuggestedUserInputBean inputBean) {
        String where = "1=1";
        
        if ((inputBean.getSearch_firstname() == null || inputBean.getSearch_firstname().isEmpty())
                && (inputBean.getSearch_lastname() == null || inputBean.getSearch_lastname().isEmpty())
                && (inputBean.getSearch_mobile() == null || inputBean.getSearch_mobile().isEmpty())
                && (inputBean.getSearch_email() == null || inputBean.getSearch_email().isEmpty())
                && (inputBean.getSearch_status() == null || inputBean.getSearch_status().isEmpty())
                && (inputBean.getSearch_role() == null || inputBean.getSearch_role().isEmpty())
                && (inputBean.getSearch_name_user() == null || inputBean.getSearch_name_user().isEmpty())
                && (inputBean.getSearch_mobile_user() == null || inputBean.getSearch_mobile_user().isEmpty())) {
            
        } else {
            
            if (inputBean.getSearch_firstname() != null && !inputBean.getSearch_firstname().isEmpty()) {
                where += " and lower(u.firstName) like lower('%" + inputBean.getSearch_firstname() + "%')";
            }
            if (inputBean.getSearch_lastname() != null && !inputBean.getSearch_lastname().isEmpty()) {
                where += " and lower(u.lastName) like lower('%" + inputBean.getSearch_lastname() + "%')";
            }
            if (inputBean.getSearch_mobile() != null && !inputBean.getSearch_mobile().isEmpty()) {
                where += " and u.mobile like '%" + inputBean.getSearch_mobile() + "%'";
            }
            if (inputBean.getSearch_email() != null && !inputBean.getSearch_email().isEmpty()) {
                where += " and lower(u.email) like lower('%" + inputBean.getSearch_email() + "%')";
            }
            if (inputBean.getSearch_name_user() != null && !inputBean.getSearch_name_user().isEmpty()) {
                where += " and lower(u.mobUser.firstName) like lower('%" + inputBean.getSearch_name_user() + "%')";
            }
            if (inputBean.getSearch_mobile_user() != null && !inputBean.getSearch_mobile_user().isEmpty()) {
                where += " and lower(u.mobUser.mobile) like lower('%" + inputBean.getSearch_mobile_user() + "%')";
            }
            if (inputBean.getSearch_status() != null && !inputBean.getSearch_status().isEmpty()) {
                where += " and u.status.statuscode='" + inputBean.getSearch_status() + "'";
            }
            if (inputBean.getSearch_role() != null && !inputBean.getSearch_role().isEmpty()) {
                where += " and u.roles.roleType='" + inputBean.getSearch_role() + "'";
            }
        }
        return where;
    }
    
    public MobSuggestedUser findSuggestUserById(String id) throws Exception {
        MobSuggestedUser tt = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            
            String sql = "from MobSuggestedUser as u where u.id=:id";
            Query query = session.createQuery(sql).setLong("id", Long.parseLong(id));
            tt = (MobSuggestedUser) query.list().get(0);
            
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
}
