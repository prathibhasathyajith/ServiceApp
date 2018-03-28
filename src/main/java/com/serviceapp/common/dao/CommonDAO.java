/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.common.dao;

import com.serviceapp.bean.systemconfig.TermsInputBean;
import com.serviceapp.bean.systemconfig.TermsVersionBean;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobFaqType;
import com.serviceapp.mapping.Status;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.mapping.TransactionType;
import com.serviceapp.mapping.WebTerms;
import com.serviceapp.object.Page;
import com.serviceapp.object.Section;
import com.serviceapp.object.Task;
import com.serviceapp.varlist.SessionVarlist;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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

    public static Date specialStringtoDate(String date) {
        Date fdate = null;
        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return fdate;
    }

    public static ByteArrayOutputStream zipFiles(File[] listFiles) throws Exception {
        byte[] buffer;
        ByteArrayOutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            for (File file : listFiles) {
                buffer = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(buffer, 0, (int) file.length());
                ZipEntry ze = new ZipEntry(file.getName());

                zipOutputStream.putNextEntry(ze);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
        return outputStream;
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

    public static String saveAudit(Systemaudit audit) throws Exception {

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

    public List<Systemuser> getUserList() throws Exception {
        // TODO Auto-generated method stub

        List<Systemuser> userList = new ArrayList<Systemuser>();
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from Systemuser u order by Upper(u.username) asc";
            Query query = session.createQuery(sql);
            userList = query.list();

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
        return userList;
    }

    public List<Section> getSectionList() throws Exception {

        List<Section> section = new ArrayList<Section>();
        Section sec = new Section();
        try {
            sec.setSectioncode("DEFAULT_SECTION");
            sec.setDescription("Default Section");
            section.add(sec);

            sec = new Section();
            sec.setSectioncode("SYSTEMCONFIGMANAGEMENT");
            sec.setDescription("System Config");
            section.add(sec);

            sec = new Section();
            sec.setSectioncode("USERMANAGEMENT");
            sec.setDescription("User Management");
            section.add(sec);

            sec = new Section();
            sec.setSectioncode("SYSTEM_AUDIT");
            sec.setDescription("System Audit");
            section.add(sec);

        } catch (Exception e) {
            throw e;
        }
        return section;
    }

    public List<Page> getPageList() throws Exception {

        List<Page> page = new ArrayList<Page>();
        Page pge = new Page();
        try {
            pge.setPagecode("LOGIN_PAGE");
            pge.setDescription("Login Page");
            page.add(pge);

            pge = new Page();
            pge.setPagecode("PASSWORD_POLICY");
            pge.setDescription("Password Policy");
            page.add(pge);

            pge = new Page();
            pge.setPagecode("SYSTEM_USER");
            pge.setDescription("System User");
            page.add(pge);

            pge = new Page();
            pge.setPagecode("SYSTEM_AUDIT_PAGE");
            pge.setDescription("System Audit");
            page.add(pge);

        } catch (Exception e) {
            throw e;
        }
        return page;
    }

    public List<Task> getTaskList() throws Exception {

        List<Task> task = new ArrayList<Task>();
        Task tsk = new Task();
        try {

            tsk.setTaskcode("LOGIN_TASK");
            tsk.setDescription("Login");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("LOGOUT_TASK");
            tsk.setDescription("Logout");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("ADD_TASK");
            tsk.setDescription("Add");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("SEARCH_TASK");
            tsk.setDescription("Search");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("UPDATE_TASK");
            tsk.setDescription("Update");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("DELETE_TASK");
            tsk.setDescription("Delete");
            task.add(tsk);

            tsk = new Task();
            tsk.setTaskcode("VIEW_TASK");
            tsk.setDescription("View");
            task.add(tsk);

        } catch (Exception e) {
            throw e;
        }
        return task;
    }

    public List<MobFaqType> getDefultTypeList() throws Exception {

        List<MobFaqType> typeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MobFaqType as t";
            Query query = session.createQuery(sql);
            typeList = query.list();
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
        return typeList;
    }

    public List<TransactionType> getTxnTypeList()
            throws Exception {

        List<TransactionType> txnTypeList = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from TransactionType as u order by Upper(u.description) asc";

            Query query = session.createQuery(sql);
            txnTypeList = query.list();
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
        return txnTypeList;
    }

    public List<WebTerms> getVersionList(String versionNo) throws Exception {
        List<WebTerms> webterms = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from WebTerms as wt where wt.versionNo=:versionNo order by Upper(wt.versionNo) asc";
            Query query = session.createQuery(sql).setString("versionNo", versionNo);
            webterms = query.list();
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
        return webterms;
    }

    public List<WebTerms> getAllVersionList() throws Exception {
        List<WebTerms> webterms = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            String sql = "from WebTerms as wt order by Upper(wt.versionNo) asc";
            Query query = session.createQuery(sql);
            webterms = query.list();
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
        return webterms;
    }
}
