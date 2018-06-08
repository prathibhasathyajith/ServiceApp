/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemaudit;

import com.serviceapp.bean.systemaudit.SystemAuditBean;
import com.serviceapp.bean.systemaudit.SystemAuditInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.common.dao.ExcelCommon;
import com.serviceapp.common.dao.PartialList;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.object.Page;
import com.serviceapp.object.Section;
import com.serviceapp.object.Task;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author prathibha_s
 */
public class SystemAuditDAO {

    private final int columnCount = 9;
    private final int headerRowCount = 13;

    private String TXN_COUNT_SQL = "select "
            + "count(g.SYSTEMAUDITID) " //0            
            + "from web_systemaudit g "
            + "where ";

    private String TXN_ORDER_BY_SQL = " order by g.LASTUPDATEDTIME DESC ";

    private String TXN_SQL = "select "
            + "g.SYSTEMAUDITID, " //0            
//            + "g.DESCRIPTION ud, "
            + "g.DESCRIPTION us, " //1            
            + "g.SECTION aa, " //2
            + "g.PAGE bb, " //3
            + "g.TASK cc, " //4
            //            + "g.IP ip, " 
            + "g.LASTUPDATEDUSER, " //5
            + "g.LASTUPDATEDTIME, " //6
            + "g.CREATETIME " //7

            + "from web_systemaudit g "
            //            + "left outer join web_section sc on sc.SECTIONCODE = g.SECTIONCODE "
            //            + "left outer join web_page pg on pg.PAGECODE = g.PAGECODE "
            //            + "left outer join web_task ts on ts.TASKCODE = g.TASKCODE "
            //            + "left outer join web_userrole ur on ur.USERROLECODE = g.USERROLECODE "
            + "where ";

    public PartialList<SystemAuditBean> getSearchList(SystemAuditInputBean auditSearchDTO, int rows, int from, String sortIndex, String sortOrder) throws Exception {
        List<Systemaudit> searchList = null;
        List<SystemAuditBean> dataBeanList = null;
        Session session = null;
        long fullCount = 0;
        if ("".equals(sortIndex.trim())) {
            sortIndex = null;
        }
        try {
            session = HibernateInit.sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Systemaudit.class);
//            criteria.createAlias("user", "ur");

            if (sortIndex != null && sortOrder != null) {
                if (sortOrder.equals("asc")) {
                    criteria.addOrder(Order.asc(sortIndex));
                }
                if (sortOrder.equals("desc")) {
                    criteria.addOrder(Order.desc(sortIndex));
                }

            } else {
                criteria.addOrder(Order.desc("createtime"));
            }

            if (auditSearchDTO.getUser() != null && !auditSearchDTO.getUser().isEmpty()) {
                criteria.add(Restrictions.eq("lastupdateduser", auditSearchDTO.getUser()));
            }

            if (auditSearchDTO.getSection() != null && !auditSearchDTO.getSection().isEmpty()) {
                criteria.add(Restrictions.eq("section", auditSearchDTO.getSection()));
            }

            if (auditSearchDTO.getSdblpage() != null && !auditSearchDTO.getSdblpage().isEmpty()) {
                criteria.add(Restrictions.eq("page", auditSearchDTO.getSdblpage()));
            }

            if (auditSearchDTO.getTask() != null && !auditSearchDTO.getTask().isEmpty()) {
                criteria.add(Restrictions.eq("task", auditSearchDTO.getTask()));
            }

            if (auditSearchDTO.getDescription() != null && !auditSearchDTO.getDescription().isEmpty()) {
                criteria.add(Restrictions.ilike("description", auditSearchDTO.getDescription(), MatchMode.ANYWHERE));
            }

            if (auditSearchDTO.getFdate() != null && !auditSearchDTO.getFdate().isEmpty()) {
                criteria.add(Restrictions.ge("createtime", CommonDAO.specialStringtoDate(auditSearchDTO.getFdate())));
            }

            if (auditSearchDTO.getTdate() != null && !auditSearchDTO.getTdate().isEmpty()) {
                criteria.add(Restrictions.le("createtime", new Date((CommonDAO.specialStringtoDate(auditSearchDTO.getTdate().trim())).getTime() + TimeUnit.DAYS.toMillis(1))));
            }

            fullCount = criteria.list().size();

            criteria.setFirstResult(from);
            criteria.setMaxResults(rows);

            searchList = criteria.list();
            dataBeanList = new ArrayList<SystemAuditBean>();

            for (Systemaudit m : searchList) {
                SystemAuditBean tempBean = new SystemAuditBean(m);
                tempBean.setTaskDes(m.getTask());
                tempBean.setPageDes(m.getPage());
//                tempBean.setSectionDes(new CommonDAO().getSectionByprefix(m.getSection()));
                tempBean.setSectionDes(m.getSection());
                tempBean.setLastUpdatedDate(m.getLastupdatedtime().toString().substring(0, 19));
                tempBean.setCreatetime(m.getCreatetime().toString().substring(0, 19));
                dataBeanList.add(tempBean);
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

        PartialList<SystemAuditBean> list = new PartialList<SystemAuditBean>();

        list.setList(dataBeanList);
        list.setFullCount(fullCount);

        return list;
    }

    public SystemAuditBean findAuditById(String auditId) throws Exception {
        SystemAuditBean auditDatabean;
        Section sec = null;
        Page pg = null;
        Task tk = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            Systemaudit auditBean = (Systemaudit) session.get(Systemaudit.class, new Integer(auditId));

//            String sql = "from Section as u where u.sectioncode=:sectioncode";
//            Query query = session.createQuery(sql).setString("sectioncode", auditBean.getSection());
//            sec = (Section) query.list().get(0);
//
//            String sql2 = "from Page as u where u.pagecode=:pagecode";
//            Query query2 = session.createQuery(sql2).setString("pagecode", auditBean.getPage());
//            pg = (Page) query2.list().get(0);
//
//            String sql3 = "from Task as u where u.taskcode=:taskcode";
//            Query query3 = session.createQuery(sql3).setString("taskcode", auditBean.getTask());
//            tk = (Task) query3.list().get(0);
            auditDatabean = new SystemAuditBean(auditBean);
            auditDatabean.setSection(auditBean.getSection());
            auditDatabean.setPage(auditBean.getPage());
            auditDatabean.setTask(auditBean.getTask());
            auditDatabean.setLastUpdatedDate(auditBean.getLastupdatedtime().toString().substring(0, 19));
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
        return auditDatabean;
    }

    public SystemAuditBean findAuditById(String page, String task, String section) throws Exception {
        SystemAuditBean auditDatabean = null;
        Section sec = null;
        Page pg = null;
        Task tk = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            auditDatabean = new SystemAuditBean();

            auditDatabean.setSection(section);
            auditDatabean.setPage(page);
            auditDatabean.setTask(task);
//            if (!"".equals(section)) {
//                String sql = "from Section as u where u.sectioncode=:sectioncode";
//                Query query = session.createQuery(sql).setString("sectioncode", section);
//                sec = (Section) query.list().get(0);
//                auditDatabean.setSection(sec.getDescription());
//            }
//            if (!"".equals(page)) {
//                String sql2 = "from Page as u where u.pagecode=:pagecode";
//                Query query2 = session.createQuery(sql2).setString("pagecode", page);
//                pg = (Page) query2.list().get(0);
//                auditDatabean.setSdblpage(pg.getDescription());
//            }
//            if (!"".equals(task)) {
//                String sql3 = "from Task as u where u.taskcode=:taskcode";
//                Query query3 = session.createQuery(sql3).setString("taskcode", task);
//                tk = (Task) query3.list().get(0);
//                auditDatabean.setTask(tk.getDescription());
//            }

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
        return auditDatabean;
    }

    public String findTaskDescription(String task) throws Exception {

        String taskDes = "";
        Session session = null;
        Task tk = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (task != null) {

                String sql = "from Task as u where u.taskcode=:taskcode";
//                System.out.println("task audit "+ task);
                Query query = session.createQuery(sql).setString("taskcode", task);
                tk = (Task) query.list().get(0);
                taskDes = tk.getDescription();
            } else {
                taskDes = "--";
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
        return taskDes;
    }

    public String findPageDescription(String page) throws Exception {

        String pageDes = "";
        Session session = null;
        Page pg = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (page != null) {

                String sql = "from Page as u where u.pagecode=:pagecode";
                Query query = session.createQuery(sql).setString("pagecode", page);
                pg = (Page) query.list().get(0);
                pageDes = pg.getDescription();
            } else {
                pageDes = "--";
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
        return pageDes;
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

    public String findSectionDescription(String section) throws Exception {

        String sectionDes = "";
        Session session = null;
        Section sc = null;
        try {

            session = HibernateInit.sessionFactory.openSession();

            if (section != null) {

                String sql = "from Section as u where u.sectioncode=:sectioncode";
                Query query = session.createQuery(sql).setString("sectioncode", section);
                sc = (Section) query.list().get(0);
                sectionDes = sc.getDescription();

            } else {
                sectionDes = "--";
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
        return sectionDes;
    }

    public Object generateExcelReport(SystemAuditInputBean inputBean) throws Exception {
        Session session = null;
        Object returnObject = null;
        try {

            String directory = ServletActionContext.getServletContext().getInitParameter("tmpreportpath");
            File file = new File(directory);
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }

            session = HibernateInit.sessionFactory.openSession();

            int count = 0;
            String where1 = this.makeWhereClauseForExcel(inputBean);
            String sqlCount = this.TXN_COUNT_SQL + where1;
            System.out.println(sqlCount);
            Query queryCount = session.createSQLQuery(sqlCount);
//            queryCount = setDatesToQuery(queryCount, inputBean, session);

//            queryCount = setDatesToQuery(queryCount, inputBean, session);
            if (queryCount.uniqueResult() != null) {
                count = ((Number) queryCount.uniqueResult()).intValue();
            }
//                System.err.println("Count is "+count);
            if (count > 0) {

                long maxRow = Long.parseLong(ServletActionContext.getServletContext().getInitParameter("numberofrowsperexcel"));
                SXSSFWorkbook workbook = this.createExcelTopSection(inputBean);
                Sheet sheet = workbook.getSheetAt(0);

                int currRow = headerRowCount;
                int fileCount = 0;

                currRow = this.createExcelTableHeaderSection(workbook, currRow);

                String sql = this.TXN_SQL + where1 + this.TXN_ORDER_BY_SQL;
                System.out.println(sql);
                int selectRow = Integer.parseInt(ServletActionContext.getServletContext().getInitParameter("numberofselectrows"));
                int numberOfTimes = count / selectRow;
                if ((count % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                for (int i = 0; i < numberOfTimes; i++) {

                    Query query = session.createSQLQuery(sql);
                    query.setFirstResult(from);
                    query.setMaxResults(selectRow);

                    List<Object[]> objectArrList = (List<Object[]>) query.list();
                    if (objectArrList.size() > 0) {

                        for (Object[] objArr : objectArrList) {
                            SystemAuditBean dataBean = new SystemAuditBean();

                            try {
                                dataBean.setId(objArr[0].toString());

                                if (objArr[0].equals("")) {
                                    dataBean.setId("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setId("--");
                            }

                            //null has to be checked for every foreign keys
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setDescription(objArr[1].toString());
                                if (objArr[1].equals("")) {

                                    dataBean.setDescription("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setDescription("--");
                            }

                            try {

                                dataBean.setSection(objArr[2].toString());
                                if (objArr[2].equals("")) {

                                    dataBean.setSection("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setSection("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setSdblpage(objArr[3].toString());
                                if (objArr[3].equals("")) {
                                    dataBean.setSdblpage("--");

                                }
                            } catch (NullPointerException npe) {
                                dataBean.setSdblpage("--");
                            }
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setTask(objArr[4].toString());
                                if (objArr[4].equals("")) {
                                    dataBean.setTask("--");

                                }
                            } catch (NullPointerException npe) {
                                dataBean.setTask("--");
                            }

                            try {
                                dataBean.setUser(objArr[5].toString());
                                if (objArr[5].equals("")) {
                                    dataBean.setUser("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUser("--");
                            }

                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setLastUpdatedDate(objArr[6].toString());
                                if (objArr[6].equals("")) {
                                    dataBean.setLastUpdatedDate("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setLastUpdatedDate("--");
                            }
                            
                            try {
                                // dataBean.setTransactionID(txnBean.getTransactionid().toString());
                                dataBean.setCreatetime(objArr[7].toString());
                                if (objArr[7].equals("")) {
                                    dataBean.setCreatetime("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCreatetime("--");
                            }

                            dataBean.setFullCount(count);

                            if (currRow + 1 > maxRow) {
                                fileCount++;
                                this.writeTemporaryFile(workbook, fileCount, directory);
                                workbook = this.createExcelTopSection(inputBean);
                                sheet = workbook.getSheetAt(0);
                                currRow = headerRowCount;
                                this.createExcelTableHeaderSection(workbook, currRow);
                            }
                            currRow = this.createExcelTableBodySection(workbook, dataBean, currRow, listrownumber);
                            listrownumber++;
                            if (currRow % 100 == 0) {
                                ((SXSSFSheet) sheet).flushRows(100); // retain 100 last rows and flush all others

                                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                                // this method flushes all rows
                            }
                        }
                    }
                    from = from + selectRow;
                }

                Date createdTime = CommonDAO.getSystemDate(session);
                this.createExcelBotomSection(workbook, currRow, count, createdTime);

                if (fileCount > 0) {
                    fileCount++;
                    this.writeTemporaryFile(workbook, fileCount, directory);
                    ByteArrayOutputStream outputStream = CommonDAO.zipFiles(file.listFiles());
                    returnObject = outputStream;
                    workbook.dispose();
                } else {
                    for (int i = 0; i < columnCount; i++) {
                        //to auto size all column in the sheet
                        System.out.println(i+" - count");
                        sheet.autoSizeColumn(i);
                    }

                    returnObject = workbook;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnObject;
    }

    private String makeWhereClauseForExcel(SystemAuditInputBean inputBean) throws ParseException {
        String where = "1=1";

        if (inputBean.getSection() != null && !inputBean.getSection().isEmpty()) {
            where += " and g.SECTION LIKE '%" + inputBean.getSection() + "%'";
        }
        if (inputBean.getSdblpage() != null && !inputBean.getSdblpage().isEmpty()) {
            where += " and g.PAGE LIKE '%" + inputBean.getSdblpage() + "%'";
        }
        if (inputBean.getTask() != null && !inputBean.getTask().isEmpty()) {
            where += " and g.TASK LIKE '%" + inputBean.getTask() + "%'";
        }
        if (inputBean.getUser() != null && !inputBean.getUser().isEmpty()) {
            where += " and g.LASTUPDATEDUSER LIKE '%" + inputBean.getUser() + "%'";
        }
        if (inputBean.getDescription() != null && !inputBean.getDescription().isEmpty()) {
            where += " and g.DESCRIPTION LIKE '%" + inputBean.getDescription() + "%'";
        }

        String date1 = inputBean.getTdate();  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date1));
        c.add(Calendar.DATE, 1);  // number of days to add
        sdf.applyPattern("yyyy-MM-dd");
        date1 = sdf.format(c.getTime());  // dt is now the new date

        String datef = inputBean.getFdate();  // Start date
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cf = Calendar.getInstance();
        cf.setTime(sdff.parse(datef));
        cf.add(Calendar.DATE, 0);
        sdff.applyPattern("yyyy-MM-dd");
        datef = sdff.format(cf.getTime());

        if (inputBean.getFdate() != null && !inputBean.getFdate().isEmpty()) {
            where += " and g.LASTUPDATEDTIME >='" + datef + "'";
        }
        if (date1 != null && !date1.isEmpty()) {
            where += " and g.LASTUPDATEDTIME <'" + date1 + "'";
        }

//        System.err.println(where);
        return where;
    }

    private SXSSFWorkbook createExcelTopSection(SystemAuditInputBean inputBean) throws Exception {

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("System_Audit_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Service App/Admin Portal");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Audit Summary Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("From Date");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getFdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("To Date");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getTdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Section");
        cell = row.createCell(1);
        if (inputBean.getSection() != null && !inputBean.getSection().isEmpty()) {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(this.findAuditById("", "", inputBean.getSection()).getSection()));
        } else {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getSection()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("Page");
        cell = row.createCell(1);
        if (inputBean.getSdblpage() != null && !inputBean.getSdblpage().isEmpty()) {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(this.findAuditById(inputBean.getSdblpage(), "", "").getSdblpage()));
        } else {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getSdblpage()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(8);
        cell = row.createCell(0);
        cell.setCellValue("Task");
        cell = row.createCell(1);
        if (inputBean.getTask() != null && !inputBean.getTask().isEmpty()) {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(this.findAuditById("", inputBean.getTask(), "").getTask()));
        } else {
            cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getTask()));
        }
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(9);
        cell = row.createCell(0);
        cell.setCellValue("Last Updated User");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getUser()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(10);
        cell = row.createCell(0);
        cell.setCellValue("Description");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getDescription()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

//        
        return workbook;
    }

    private int createExcelTableHeaderSection(SXSSFWorkbook workbook, int currrow) throws Exception {
        CellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue("No");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("Description");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Section");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Page");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Task");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("Username");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("Last Updated Time");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(8);
        cell.setCellValue("Created Time");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }

    private void writeTemporaryFile(SXSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {
            Sheet sheet = workbook.getSheetAt(0);
//            for (int i = 0; i < columnCount; i++) {
//                //to auto size all column in the sheet
//                sheet.autoSizeColumn(i);
//            }

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }

            if (fileCount > 0) {
                file = new File(directory + File.separator + "System Audit Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "System Audit Report.xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private int createExcelTableBodySection(SXSSFWorkbook workbook, SystemAuditBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getId());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getDescription());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getSection());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getSdblpage());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getTask());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getUser());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getLastUpdatedDate());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(8);
        cell.setCellValue(dataBean.getCreatetime());
        cell.setCellStyle(rowColumnCell);

        return currrow;
    }

    private void createExcelBotomSection(SXSSFWorkbook workbook, int currrow, long count, Date date) throws Exception {

        CellStyle fontBoldedCell = ExcelCommon.getFontBoldedCell(workbook);
        Sheet sheet = workbook.getSheetAt(0);

        currrow++;
        Row row = sheet.createRow(currrow++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Summary");
        cell.setCellStyle(fontBoldedCell);

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Total Record Count");
        cell = row.createCell(1);
        cell.setCellValue(count);
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(currrow++);
        cell = row.createCell(0);
        cell.setCellValue("Report Created Time");
        cell = row.createCell(1);
        cell.setCellValue(date.toString().substring(0, 19));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));
    }
}
