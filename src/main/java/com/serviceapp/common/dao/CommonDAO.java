/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.common.dao;

import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.SessionVarlist;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha
 */
public class CommonDAO {
    
    public static Date getSystemDate(Session session) throws Exception {
        Date sysDateTime = null;
        try {
            
            String sql = "SELECT NOW()";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            sysDateTime = (Date) l.get(0);
//            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return sysDateTime;
    }
    public static String replaceEmptyorNullStringToNA(String string) {
        String value = "--";
        if (string != null && !string.trim().isEmpty()) {
            value = string;
        }
        return value;
    }

    public static String replaceEmptyorNullStringToALL(String string) {
        String value = "-ALL-";
        if (string != null && !string.trim().isEmpty()) {
            value = string;
        }
        return value;
    }

    public static String checkEmptyorNullString(String str) {
        if (str == null || str.isEmpty()) {
            str = "--";
        }
        return str;
    }

    public static String checkEmptyorNullString(String feildName, String str) {
        if (str == null || str.isEmpty()) {
            str = "";
        } else {
            str = feildName + " - " + str + ",";
        }
        return str;
    }
    
    public static String makeHash(String text) throws Exception {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }
    
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String getFormattedDateForLogin(Date date) {
        String fDate = "";
        String pattern = "dd MMMM yyyy hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        fDate = dateFormat.format(date);
        return fDate;
    }
    
    public static Date getSystemDateLogin() throws Exception {
        Date sysDateTime = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "SELECT NOW()";
            Query query = session.createSQLQuery(sql);
            List l = query.list();
            sysDateTime = (Date) l.get(0);
//            sysDateTime = Timestamp.valueOf(stime);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return sysDateTime;
    }
    public static Systemaudit makeAudittrace(HttpServletRequest request, Systemuser user, String task, String page, String section, String description, String remarks) {

        Systemaudit audit = new Systemaudit();
        audit.setDescription(description + " by " + user.getUsername());
        audit.setSection(section);
//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPage(page);
        audit.setTask(task);
//        Systemuser us = new Systemuser();
//        us.setUsername(user.getUsername());
//        audit.setRemarks(remarks);
        audit.setLastupdateduser(user.getUsername());
        return audit;
    }
    
    public static Systemaudit makeAudittrace(HttpServletRequest request, String task, String page, String section, String description, String remarks, String oldvalue, String newvalue) throws Exception {

        HttpSession session = request.getSession(false);
        Systemuser sysUser = (Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
        Systemaudit audit = new Systemaudit();
        audit.setDescription(description + " by " + sysUser.getUsername());
        audit.setOldvalue(oldvalue);
        audit.setNewvalue(newvalue);

        CommonDAO dao = new CommonDAO();

        audit.setSection(section);

//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPage(page);

        audit.setTask(task);

//        Systemuser us = new Systemuser();
//        us.setUsername(sysUser.getUsername());
//        audit.setRemarks(/remarks);
        audit.setLastupdateduser(sysUser.getUsername());
        return audit;

    }
    
    public static Systemaudit makeAudittrace(HttpServletRequest request, String task, String page, String section, String description, String remarks) throws Exception {

        HttpSession session = request.getSession(false);
        Systemuser sysUser = (Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
        Systemaudit audit = new Systemaudit();
        audit.setDescription(description + " by " + sysUser.getUsername());

        CommonDAO dao = new CommonDAO();

        audit.setSection(section);

//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPage(page);

        audit.setTask(task);

//        Systemuser us = new Systemuser();
//        us.setUsername(sysUser.getUsername());
//        audit.setRemarks(remarks);
        audit.setLastupdateduser(sysUser.getUsername());
        return audit;

    }
    
    public String saveAudit(Systemaudit audit) throws Exception {

        Session session = null;
        Transaction txn = null;
        String message = "";
        try {
            session = HibernateInit.sessionFactory.openSession();
            Date sysDate = CommonDAO.getSystemDate(session);

            txn = session.beginTransaction();
            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);
            audit.setLastupdateduser(audit.getLastupdateduser());

            session.save(audit);

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
    public List<Status> getDefultStatusList(String statusCode)
            throws Exception {

        List<Status> statusList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Status as s where s.category=:statuscategorycode order by Upper(s.description) asc";
            Query query = session.createQuery(sql).setString(
                    "statuscategorycode", statusCode);
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
    public static String mpiMd5(String value) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(value.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        return result;
    }
}
