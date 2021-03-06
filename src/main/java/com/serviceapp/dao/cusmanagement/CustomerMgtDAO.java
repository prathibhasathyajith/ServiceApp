/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.cusmanagement;

import com.serviceapp.bean.cusmanagement.CustomerMgtBean;
import com.serviceapp.bean.cusmanagement.CustomerMgtInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobBassData;
import com.serviceapp.mapping.MobUser;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.WebBassQualification;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

/**
 *
 * @author prathibha_s
 */
public class CustomerMgtDAO {

    public String updateCustomer(CustomerMgtInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        FileInputStream fileInputStream = null;
        String message = "";
        String imageName = "";

        String oldValueBass = "";
        String newValueBass = "";
        try {

            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            MobUser u = (MobUser) session.get(MobUser.class, Integer.parseInt(inputBean.getUserId()));
            MobBassData u2 = (MobBassData) session.get(MobBassData.class, Integer.parseInt(inputBean.getUserId()));
            MobBassData bassData = new MobBassData();

            //update user role 
            this.UpdateUserRole(inputBean.getUserId(), inputBean.getStatus());

            // if true update bass table / false inset bass table
            if (u2 != null) {

                oldValueBass = "|" + u2.getAddress()
                        + "|" + u2.getArea()
                        + "|" + u2.getDistrict()
                        + "|" + u2.getWebBassQualification().getDescription();

                u2.setAddress(inputBean.getAddress());
                u2.setArea(inputBean.getArea());
                u2.setDistrict(inputBean.getDistrict());
                
                WebBassQualification qf = (WebBassQualification) session.get(WebBassQualification.class, Integer.parseInt(inputBean.getQualify()));
                u2.setWebBassQualification(qf);

                try {
                    if (inputBean.getPrImage().length() != 0) {

                        imageName = inputBean.getPrImageFileName();

                        File PrImage = inputBean.getPrImage();
                        byte[] bLogoWebFile = new byte[(int) PrImage.length()];
                        try {
                            fileInputStream = new FileInputStream(PrImage);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u2.setPoliceReport(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }

                    if (inputBean.getBcImage().length() != 0) {

                        imageName = inputBean.getBcImageFileName();

                        File BcImageFile = inputBean.getBcImage();
                        byte[] bLogoWebFile = new byte[(int) BcImageFile.length()];
                        try {
                            fileInputStream = new FileInputStream(BcImageFile);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u2.setBirthCert(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }

                    if (inputBean.getQlImage().length() != 0) {

                        imageName = inputBean.getQlImageFileName();

                        File QImage = inputBean.getQlImage();
                        byte[] bLogoWebFile = new byte[(int) QImage.length()];
                        try {
                            fileInputStream = new FileInputStream(QImage);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u2.setQualificationImg(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                newValueBass = "|" + u2.getAddress()
                        + "|" + u2.getArea()
                        + "|" + u2.getDistrict()
                        + "|" + u2.getWebBassQualification().getDescription();

                session.update(u2);

            } else {
                oldValueBass = "";

                bassData.setUserId(Integer.parseInt(inputBean.getUserId()));
                bassData.setMobUser(u);
                bassData.setAddress(inputBean.getAddress());
                bassData.setArea(inputBean.getArea());
                bassData.setDistrict(inputBean.getDistrict());

                WebBassQualification qf = (WebBassQualification) session.get(WebBassQualification.class, Integer.parseInt(inputBean.getQualify()));
                bassData.setWebBassQualification(qf);

                try {
                    if (inputBean.getPrImage().length() != 0) {

                        imageName = inputBean.getPrImageFileName();

                        File PrImage = inputBean.getPrImage();
                        byte[] bLogoWebFile = new byte[(int) PrImage.length()];
                        try {
                            fileInputStream = new FileInputStream(PrImage);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            bassData.setPoliceReport(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }

                    if (inputBean.getBcImage().length() != 0) {

                        imageName = inputBean.getBcImageFileName();

                        File BcImageFile = inputBean.getBcImage();
                        byte[] bLogoWebFile = new byte[(int) BcImageFile.length()];
                        try {
                            fileInputStream = new FileInputStream(BcImageFile);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            bassData.setBirthCert(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }

                    if (inputBean.getQlImage().length() != 0) {

                        imageName = inputBean.getQlImageFileName();

                        File QImage = inputBean.getQlImage();
                        byte[] bLogoWebFile = new byte[(int) QImage.length()];
                        try {
                            fileInputStream = new FileInputStream(QImage);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            bassData.setQualificationImg(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                newValueBass = "|" + bassData.getAddress()
                        + "|" + bassData.getArea()
                        + "|" + bassData.getDistrict()
                        + "|" + bassData.getWebBassQualification().getDescription();

                session.save(bassData);
            }

            if (u != null) {

                String oldValue = u.getId()
                        + "|" + u.getFirstName()
                        + "|" + u.getLastName()
                        + "|" + u.getEmail()
                        + "|" + u.getNic()
                        + oldValueBass
                        + "|" + u.getStatus().getDescription();

                u.setFirstName(inputBean.getFirstName());
                u.setLastName(inputBean.getLastName());
                u.setFirstName(inputBean.getFirstName());
                u.setEmail(inputBean.getEmail());
                u.setNic(inputBean.getNic());

                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                u.setStatus(st);

                if (inputBean.getStatus().trim().endsWith(CommonVarlist.STATUS_ACTIVE)) {
                    u.setIsBass(true);
                } else {
                    u.setIsBass(false);
                }

                // user image
                try {
                    if (inputBean.getOwnImage().length() != 0) {

                        imageName = inputBean.getOwnImageFileName();

                        File OwnImageFile = inputBean.getOwnImage();
                        byte[] bLogoWebFile = new byte[(int) OwnImageFile.length()];
                        try {
                            fileInputStream = new FileInputStream(OwnImageFile);
                            fileInputStream.read(bLogoWebFile);
                            fileInputStream.close();
                            u.setImage(bLogoWebFile);
                        } catch (Exception ex) {

                        }
                    }
                } catch (NullPointerException ex) {
                } finally {
                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        } else {
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }

                u.setCreatedTime(sysDate);

                String newValue = u.getId()
                        + "|" + u.getFirstName()
                        + "|" + u.getLastName()
                        + "|" + u.getEmail()
                        + "|" + u.getNic()
                        + newValueBass
                        + "|" + u.getStatus().getDescription();

                audit.setOldvalue(oldValue);
                audit.setNewvalue(newValue);
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

    public List<CustomerMgtBean> getSearchList(CustomerMgtInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<CustomerMgtBean> dataList = new ArrayList<CustomerMgtBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = " order by u.firstName desc ";
            }
            String where = this.makeWhereClause(inputBean);

            long count = 0;

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MobUser as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();
            System.err.println(sqlCount);
            if (count > 0) {
                String sqlSearch = "from MobUser u where " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                CommonDAO daao = new CommonDAO();
                while (it.hasNext()) {
                    CustomerMgtBean bean = new CustomerMgtBean();
                    MobUser cus = (MobUser) it.next();

                    try {
                        bean.setId(cus.getId().toString());
                    } catch (NullPointerException e) {
                        bean.setId("--");
                    }
                    try {
                        bean.setStatus(cus.getStatus().getDescription());
                    } catch (NullPointerException e) {
                        bean.setStatus("--");
                    }

                    try {
                        bean.setStatuscode(cus.getStatus().getStatuscode());
                    } catch (NullPointerException e) {
                        bean.setStatuscode("--");
                    }

                    try {
                        bean.setMobile(cus.getMobile());
                    } catch (Exception e) {
                        bean.setMobile("--");
                    }

                    try {
                        bean.setEmail(cus.getEmail());
                    } catch (Exception e) {
                        bean.setEmail("--");
                    }

                    try {
                        bean.setFirstName(cus.getFirstName());
                    } catch (Exception e) {
                        bean.setFirstName("--");
                    }

                    try {
                        bean.setLastName(cus.getLastName());
                    } catch (Exception e) {
                        bean.setLastName("--");
                    }

                    try {
                        bean.setNic(cus.getNic());
                    } catch (Exception e) {
                        bean.setNic("--");
                    }

                    try {
                        bean.setCreatedTime(cus.getCreatedTime().toString());
                    } catch (Exception e) {
                        bean.setCreatedTime("--");
                    }

                    try {
                        bean.setLastLoginTime(cus.getLastLoginTime().toString());
                    } catch (Exception e) {
                        bean.setLastLoginTime("--");
                    }

                    bean.setFullCount(count);
                    dataList.add(bean);
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

    public String deleteCustomer(CustomerMgtInputBean inputBean, Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            CommonDAO commonDAO = new CommonDAO();
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = commonDAO.getSystemDate(session);

            if (inputBean.getUserId() != null) {

                //user role
                this.UpdateUserRole(inputBean.getUserId(), inputBean.getStatus());

                //mob user
                String sql = "update MobUser as u set u.status=:statuscode where u.id=:userId";
                Query query = session.createQuery(sql).setInteger("userId", Integer.parseInt(inputBean.getUserId())).setString("statuscode", inputBean.getStatus());
                query.executeUpdate();

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                session.save(audit);
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

    private String makeWhereClause(CustomerMgtInputBean inputBean) {
        String where = "1=1";

        if ((inputBean.getNicSearch() == null || inputBean.getNicSearch().isEmpty())
                && (inputBean.getFnameSearch() == null || inputBean.getFnameSearch().isEmpty())
                && (inputBean.getLnameSearch() == null || inputBean.getLnameSearch().isEmpty())
                && (inputBean.getMobileSearch() == null || inputBean.getMobileSearch().isEmpty())
                && (inputBean.getEmailSearch() == null || inputBean.getEmailSearch().isEmpty())
                && (inputBean.getStatusSearch() == null || inputBean.getStatusSearch().isEmpty())) {

        } else {

            if (inputBean.getNicSearch() != null && !inputBean.getNicSearch().isEmpty()) {
                where += " and u.nic like '%" + inputBean.getNicSearch() + "%'";
            }
            if (inputBean.getFnameSearch() != null && !inputBean.getFnameSearch().isEmpty()) {
                where += " and lower(u.firstName) like lower('%" + inputBean.getFnameSearch() + "%')";
            }
            if (inputBean.getStatusSearch() != null && !inputBean.getStatusSearch().isEmpty()) {
                where += " and u.status.statuscode='" + inputBean.getStatusSearch() + "'";
            }
            if (inputBean.getEmailSearch() != null && !inputBean.getEmailSearch().isEmpty()) {
                where += " and lower(u.email) like lower('%" + inputBean.getEmailSearch() + "%')";
            }
            if (inputBean.getLnameSearch() != null && !inputBean.getLnameSearch().isEmpty()) {
                where += " and lower(u.lastName) like lower('%" + inputBean.getLnameSearch() + "%')";
            }
            if (inputBean.getMobileSearch() != null && !inputBean.getMobileSearch().isEmpty()) {
                where += " and lower(u.mobile) like lower('%" + inputBean.getMobileSearch() + "%')";
            }

        }
        return where;
    }

    public MobUser findCustomerById(String userId) throws Exception {
        MobUser mb = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MobUser as u where u.id=:id";
            Query query = session.createQuery(sql).setInteger("id", Integer.parseInt(userId));

            if (query.list().size() > 0) {
                mb = (MobUser) query.list().get(0);
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
        return mb;
    }

    public MobBassData findCustomerBassById(String userId) throws Exception {
        MobBassData mbb = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "from MobBassData as u where u.userId=:userId";
            Query query = session.createQuery(sql).setInteger("userId", Integer.parseInt(userId));

            if (query.list().size() > 0) {
                mbb = (MobBassData) query.list().get(0);
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
        return mbb;
    }

    public void UpdateUserRole(String userId, String statuscode) throws Exception {
        MobBassData mbb = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();

            String sql = "update UserRoles as u set u.status=:statuscode where u.mobUser.id=:userId";
            Query query = session.createQuery(sql).setInteger("userId", Integer.parseInt(userId)).setString("statuscode", statuscode);
            query.executeUpdate();

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

    public String findCustomerBassRate(final String userId) throws Exception {
        String level = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            level = session.doReturningWork(new ReturningWork<String>() {
                public String execute(Connection connection) throws SQLException {
                    CallableStatement call = connection.prepareCall("{ ? = call service_app.getBassLevel(?) }");
                    call.registerOutParameter(1, Types.VARCHAR); 
                    call.setInt(2, Integer.parseInt(userId));
                    call.execute();
                    String result = call.getString(1); 
                    return result;
                }
            });

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
        return level;
    }

}
