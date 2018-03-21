/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.usermanagement;

import com.serviceapp.bean.usermanagement.SystemuserBean;
import com.serviceapp.bean.usermanagement.SystemuserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.CommonVarlist;
import com.serviceapp.varlist.MessageVarlist;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author prathibha_s
 */
public class SystemuserDAO {
    private String makeWhereClause(SystemuserInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getUsername() != null && !inputBean.getUsername().trim().isEmpty()) {
            where += " and lower(u.username) like lower('%" + inputBean.getUsername().trim() + "%')";
        }
        if (inputBean.getServiceid() != null && !inputBean.getServiceid().trim().isEmpty()) {
            where += " and lower(u.empid) like lower('%" + inputBean.getServiceid().trim() + "%')";
        }
        if (inputBean.getNic() != null && !inputBean.getNic().trim().isEmpty()) {
            where += " and lower(u.nic) like lower('%" + inputBean.getNic().trim() + "%')";
        }
        if (inputBean.getContactno() != null && !inputBean.getContactno().trim().isEmpty()) {
            where += " and lower(u.mobile) like lower('%" + inputBean.getContactno().trim() + "%')";
        }
        if (inputBean.getEmail() != null && !inputBean.getEmail().trim().isEmpty()) {
            where += " and lower(u.email) like lower('%" + inputBean.getEmail().trim() + "%')";
        }
        if (inputBean.getStatus() != null && !inputBean.getStatus().trim().isEmpty()) {
            where += " and u.status.statuscode = '" + inputBean.getStatus().trim() + "'";
        }

        if (inputBean.getFullname() != null && !inputBean.getFullname().trim().isEmpty()) {
            where += " and lower(u.fullname) like lower('%" + inputBean.getFullname().trim() + "%')";
        }
//        if (inputBean.getExpirydate()!= null && !inputBean.getExpirydate().trim().isEmpty()) {
//            where += " and lower(u.expirydate) like lower('%" + inputBean.getExpirydate().trim() + "%')";
//        }

        return where;
    }

    public List<SystemuserBean> getSearchList(SystemuserInputBean inputBean, int max, int first, String orderBy, String username) throws Exception {
        List<SystemuserBean> dataList = new ArrayList<SystemuserBean>();
        Session session = null;
        try {
            if (orderBy.equals("") || orderBy == null) {
                orderBy = "order by u.createtime desc";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);
//            System.err.println("where "+where);
            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(username) from Systemuser as u where u.username!=:username and " + where;
            Query queryCount = session.createQuery(sqlCount).setString("username", username);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {
                String sqlSearch = "from Systemuser u where u.username!=:username and " + where + orderBy;

                Query querySearch = session.createQuery(sqlSearch).setString("username", username);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    SystemuserBean systemUser = new SystemuserBean();
                    Systemuser user = (Systemuser) it.next();

                    try {
                        systemUser.setUsername(user.getUsername().toString());
                    } catch (NullPointerException e) {
                        systemUser.setUsername("--");
                    }
                    

                    try {
                        systemUser.setStatus(user.getStatus().getDescription().toString());
                    } catch (NullPointerException e) {
                        systemUser.setStatus("--");
                    }

                    try {
                        systemUser.setStatus(user.getStatus().getDescription().toString());
                    } catch (NullPointerException e) {
                        systemUser.setStatus("--");
                    }
                    

                    try {
                        systemUser.setNic(user.getNic().toString());
                    } catch (NullPointerException e) {
                        systemUser.setNic("--");
                    }

                    try {
                        systemUser.setContactNo(user.getMobile().toString());
                    } catch (NullPointerException e) {
                        systemUser.setContactNo("--");
                    }
                    try {
                        systemUser.setFullname(user.getFullname().toString());
                    } catch (NullPointerException e) {
                        systemUser.setFullname("--");
                    }

                    try {
                        systemUser.setEmail(user.getEmail().toString());
                    } catch (NullPointerException e) {
                        systemUser.setEmail("--");
                    }
                  
                    try {
                        if (user.getCreatetime().toString() != null && !user.getCreatetime().toString().isEmpty()) {
                            systemUser.setCreatetime(user.getCreatetime().toString().substring(0, 19));
                        } else {
                            systemUser.setCreatetime("--");
                        }
                    } catch (NullPointerException e) {
                        systemUser.setCreatetime("--");
                    }

                    systemUser.setFullCount(count);
                    dataList.add(systemUser);
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

    //start newly changed
    public String activateUser(SystemuserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

//            User u = (User) session.get(User.class, inputBean.getUsername().trim());
            Systemuser u = this.getSystemuserByUserName2(inputBean.getUsername(), session);
            if (u != null) {

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                //modified (3/7/2014)
//                u.setPasswordexpirydate(formatter.parse(inputBean.getExpirydate()));

               
//                Systemuser dualAuthUSer = new Systemuser();
//                dualAuthUSer.setUsername(inputBean.getDualauthuser());
//                u.setDualauthuserrole(inputBean.getDualauthuser());

                //Change status to 'Activate'
                Status status = new Status();
                status.setStatuscode(CommonVarlist.STATUS_ACTIVE);
                u.setStatus(status);

                u.setNoofinvlidattempt("0");

                //if 'Active', change noofinvalidattempt to 0
                if ((inputBean.getStatus()).equals(CommonVarlist.STATUS_ACTIVE)) {
//                u.setNoofinvlidattempt(0);
                }

                u.setFullname(inputBean.getFullname());
                u.setAddress(inputBean.getAddress1());
//                u.setAddress2(inputBean.getAddress2());
//                u.setCity(inputBean.getCity());
                u.setMobile(inputBean.getContactno());
                u.setEmail(inputBean.getEmail());
//                u.setNic(inputBean.getNic());
//                u.setDateofbirth(formatter.parse(inputBean.getDateofbirth()));

                u.setLastupdatedtime(sysDate);

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

    //end newly changed
    public String insertSystemuser(SystemuserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Calendar cal = Calendar.getInstance();
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            if (!isSystemuserExist(inputBean.getUsername())) {
                txn = session.beginTransaction();

                Systemuser user = new Systemuser();
                user.setUsername(inputBean.getUsername());
                user.setPassword(CommonDAO.mpiMd5(inputBean.getPassword()));

                // modified (3/7/2014)
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                String sql = "select passwordexpiryperiod from Passwordpolicy";
                Query query = session.createQuery(sql);
                Iterator itCount = query.iterate();
                int expiryperiod = (Integer) itCount.next();
                cal.setTime(sysDate);
                cal.add(Calendar.DAY_OF_MONTH, expiryperiod);
                user.setExpirydate(cal.getTime());


                Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
                user.setStatus(st);

               
                user.setFullname(inputBean.getFullname().trim());
                user.setAddress(inputBean.getAddress1().trim());
//                user.setAddress2(inputBean.getAddress2());
                user.setCity(inputBean.getCity().trim());
                user.setMobile(inputBean.getContactno().trim());
                user.setEmail(inputBean.getEmail().trim());
                user.setNic(inputBean.getNic().trim());
                String date_of_birth = null;
                if (!inputBean.getDateofbirth().isEmpty()) {
                    user.setDateofbirth(formatter2.parse(inputBean.getDateofbirth()));
                    date_of_birth = inputBean.getDateofbirth() + " 00:00:00";
                } else {
                    date_of_birth = "";
                }
                user.setNoofinvlidattempt("0");//edited

                String newValue = user.getUsername() + "|"
                        + user.getFullname() + "|"
                        + user.getNic() + "|"
                        + user.getStatus().getDescription() + "|"
                        + user.getMobile() + "|"
                        + user.getEmail() + "|"
                        + user.getAddress() + "|"
                        + user.getCity() + "|"
                        + inputBean.getExpirydate().substring(0, 19) + "|"
                        + date_of_birth;

                audit.setCreatetime(sysDate);
                audit.setLastupdatedtime(sysDate);
                audit.setNewvalue(newValue);
//                Systemuser lastUpdatedUser = new Systemuser();
//                lastUpdatedUser.setUsername(audit.getLastupdateduser().getUsername());
                user.setLastupdateduser(audit.getLastupdateduser());

                user.setLastupdatedtime(sysDate);
                user.setLoggeddate(sysDate);
                user.setInitialloginstatus("0");
                user.setCreatetime(sysDate);
                session.save(audit);
                session.save(user);

                txn.commit();
            } else {

                long count = 0;

                String sqlCount = "select count(username) from Systemuser as u where u.status.statuscode=:statuscode AND u.username=:username";
                Query queryCount = session.createQuery(sqlCount).setString("statuscode", CommonVarlist.STATUS_DELETE)
                        .setString("username", inputBean.getUsername().trim());

                Iterator itCount = queryCount.iterate();
                count = (Long) itCount.next();

                if (count > 0) {
                    message = "$" + inputBean.getUsername().trim();
                } else {
                    message = MessageVarlist.COMMON_ALREADY_EXISTS;
                }
//                message = MessageVarlist.COMMON_ALREADY_EXISTS;
//                txn = session.beginTransaction();
//
//                User user = this.getSystemuserByUserName2(inputBean.getUsername(), session);
//
//                user.setPassword(Common.mpiMd5(inputBean.getPassword()));
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                user.setPasswordexpirydate(formatter.parse(inputBean.getExpirydate()));
//
//                Userrole ur = new Userrole();
//                ur.setUserrole(inputBean.getUserrole());
//                user.setUserrole(ur);
//
//                User dualAuthUSer = new User();
//                dualAuthUSer.setUsername(inputBean.getDualauthuser());
//                user.setUserByDualauthuser(dualAuthUSer);
//
//                Status st = new Status();
//                st.setStatuscode(CommonVarlist.STATUS_ACTIVE);
//                user.setStatus(st);
//
//                //Status is 'Active', change noofinvalidattempt to 0
//                user.setNoofinvlidattempt(0);
//
//
//                user.setFullname(inputBean.getFullname());
//                user.setServiceid(inputBean.getServiceid());
//                user.setAddress1(inputBean.getAddress1());
//                user.setAddress2(inputBean.getAddress2());
//                user.setCity(inputBean.getCity());
//                user.setContactnumber(inputBean.getContactno());
//                user.setEmailaddress(inputBean.getEmail());
//                user.setNic(inputBean.getNic());
//                user.setDateofbirth(formatter.parse(inputBean.getDateofbirth()));
//
//                audit.setCreatedtime(sysDate);
//                audit.setLastupdatedtime(sysDate);
//
//                User lastUpdatedUser = new User();
//                lastUpdatedUser.setUsername(audit.getUser().getUsername());
//                user.setUserByLastupdateduser(lastUpdatedUser);
//
//                user.setLastupdatedtime(sysDate);
//                session.save(audit);
//                session.update(user);
//
//                txn.commit();

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

    public Passwordpolicy getPasswordPolicyDetails() throws Exception {
        Passwordpolicy passwordpolicy = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Passwordpolicy.class);
            passwordpolicy = (Passwordpolicy) criteria.list().get(0);

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
        return passwordpolicy;
    }

    private boolean isSystemuserExist(String username) throws Exception {
        List<Systemuser> userList = new ArrayList<Systemuser>();
        Session session = null;
        boolean userCheckStatus = false;
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Systemuser.class);
            criteria.add(Restrictions.eq("username", username));
            userList = (List<Systemuser>) criteria.list();

            for (Systemuser user : userList) {
                userCheckStatus = true;
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
        return userCheckStatus;
    }

    public Systemuser getSystemuserByUserName(String username) throws Exception {
        Systemuser user = null;
        Session session = null;

        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Systemuser as u where u.username=:username  ";
            Query query = session.createQuery(sql).setString("username", username);

            user = (Systemuser) query.list().get(0);

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
        return user;
    }

    public Systemuser getSystemuserByUserName2(String username, Session session) throws Exception {//call via update method
        Systemuser user = null;
        try {

            String sql = "from Systemuser as su where su.username=:username";

            Query query = session.createQuery(sql);
            query.setString("username", username);

            user = (Systemuser) query.list().get(0);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
            } catch (Exception e) {
                throw e;
            }
        }
        return user;
    }

//    public void findUsersByUserRole(SystemuserInputBean inputBean, int currUserLevel) throws Exception {
//        List<Systemuser> userList = new ArrayList<Systemuser>();
//        Session session = null;
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(Systemuser.class);
//            criteria.createAlias("userrole", "ur")
//                    .createAlias("ur.userlevel", "ul");
//            criteria.add(Restrictions.le("ul.levelid", currUserLevel));
//            userList = (List<Systemuser>) criteria.list();
//
//            for (Systemuser user : userList) {
//                inputBean.getDualAuthUserMap().put(user.getUsername(), user.getUsername());
//            }
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//    }

//    public int getCurrUsersUserLevel(String userrole) throws Exception {
//        Session session = null;
//        Userrole userRole = null;
//        int userLevel = 8;
//        try {
//            session = HibernateInit.sessionFactory.openSession();
//            session.beginTransaction();
//            Criteria criteria = session.createCriteria(Userrole.class);
//            criteria.add(Restrictions.eq("userrolecode", userrole));
//            userRole = (Userrole) criteria.list().get(0);
//            userLevel = userRole.getUserlevel().getLevelid();
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return userLevel;
//    }

//    public MsUserParam setTooltip(String sectionCode) throws Exception {
//        MsUserParam section = null;
//        Session session = null;
//
//        try {
//
//            session = HibernateInit.sessionFactory.openSession();
//
//            String sql = "from MsUserParam as u where u.paramcode=:code";
//            Query query = session.createQuery(sql).setString("code", sectionCode);
//            section = (MsUserParam) query.list().get(0);
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                session.flush();
//                session.close();
//            } catch (HibernateException e) {
//                throw e;
//            }
//        }
//        return section;
//    }
    public String updateSystemuser(SystemuserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        Systemuser user = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();

            user = this.getSystemuserByUserName2(inputBean.getUsername(), session);
            if (user.getAddress() == null) {
                user.setAddress("");
            }
            if (user.getCity() == null) {
                user.setCity("");
            }
            String date_of_birth = null;
            if (user.getDateofbirth() == null) {
                date_of_birth = "";
            } else {
                date_of_birth = user.getDateofbirth().toString().substring(0, 19);

            }

            String oldValue = user.getUsername() + "|"
                    + user.getFullname() + "|"
                    + user.getNic() + "|"
                    + user.getStatus().getDescription() + "|"
                    + user.getMobile() + "|"
                    + user.getEmail() + "|"
                    + user.getAddress() + "|"
                    + user.getCity() + "|"
                    + user.getExpirydate().toString().substring(0, 19) + "|"
                    + date_of_birth;

//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            //modified (3/7/2014)
//            user.setPasswordexpirydate(formatter.parse(inputBean.getExpirydate()));

            Status st = (Status) session.get(Status.class, inputBean.getStatus().trim());
            user.setStatus(st);

            //if 'Active', change noofinvalidattempt to 0 and loggeddate to now
            if ((inputBean.getStatus()).equals(CommonVarlist.STATUS_ACTIVE)) {
                user.setNoofinvlidattempt("0");
                user.setLoggeddate(sysDate);
            }

            user.setFullname(inputBean.getFullname().trim());
            user.setAddress(inputBean.getAddress1().trim());
//            user.setAddress2(inputBean.getAddress2());
            user.setCity(inputBean.getCity().trim());
            user.setMobile(inputBean.getContactno().trim());
            user.setEmail(inputBean.getEmail().trim());
            user.setNic(inputBean.getNic().trim());

            if (!inputBean.getDateofbirth().isEmpty()) {

                user.setDateofbirth(formatter2.parse(inputBean.getDateofbirth()));
                date_of_birth = inputBean.getDateofbirth() + " 00:00:00";

            } else {
                date_of_birth = "";
            }

            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);

            Systemuser lastUpdatedUser = new Systemuser();
            lastUpdatedUser.setUsername(audit.getLastupdateduser());
            user.setLastupdateduser(audit.getLastupdateduser());

            user.setLastupdatedtime(sysDate);
            String newValue = user.getUsername() + "|"
                    + user.getFullname() + "|"
                    + user.getNic() + "|"
                    + user.getStatus().getDescription() + "|"
                    + user.getMobile() + "|"
                    + user.getEmail() + "|"
                    + user.getAddress() + "|"
                    + user.getCity() + "|"
                    + user.getExpirydate().toString().substring(0, 19) + "|"
                    + date_of_birth;

            audit.setNewvalue(newValue);
            audit.setOldvalue(oldValue);

            session.save(audit);
            session.update(user);

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

    public String deleteSystemuser(SystemuserInputBean inputBean, Systemuser currentUser, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();

            Date sysDate = CommonDAO.getSystemDate(session);

            Systemuser user = (Systemuser) this.getSystemuserByUserName(inputBean.getUsername());

            // Check whether login user and requested to delete user are same            
            if (user.getUsername().equals(currentUser.getUsername())) {
                message = user.getUsername() + MessageVarlist.SYSUSER_DEL_INVALID;
            } else {

                long flag = 0;
                String sql = "select count(systemauditid) from Systemaudit as u where u.lastupdateduser=:user";
                Query query = session.createQuery(sql).setString("user", inputBean.getUsername().trim());
                Iterator itCount1 = query.iterate();
                flag = (Long) itCount1.next();

                if (flag > 0) {
                    message = MessageVarlist.COMMON_ALREADY_IN_USE;
                } else {

                    audit.setCreatetime(sysDate);
                    audit.setLastupdatedtime(sysDate);

                    session.save(audit);
                    session.delete(user);

                    txn.commit();
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

    public String updatePasswordReset(SystemuserInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            Systemuser u = (Systemuser) session.get(Systemuser.class, inputBean.getHusername().trim());

            if (u != null) {

                u.setPassword(inputBean.getCrenewpwd());
//                Systemuser lastUpdatedUser = new Systemuser();
//                lastUpdatedUser.setUsername(audit.getLastupdateduser().getUsername());
                u.setLastupdateduser(audit.getLastupdateduser());
                u.setLastupdatedtime(sysDate);

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
}
