/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemaudit;

import com.serviceapp.bean.systemaudit.MobLoginHistoryBean;
import com.serviceapp.bean.systemaudit.MobLoginHistoryInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.common.dao.ExcelCommon;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobAudittrace;
import com.serviceapp.mapping.Status;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author prathibha_s
 */
public class MobLoginHistoryDAO {
    private final int columnCount = 9;
    private final int headerRowCount = 9;

    private String TXN_COUNT_SQL = "select "
            + "count(u.ID) " //0            
            + "from mob_audittrace u "
            + "where ";

    private String TXN_ORDER_BY_SQL = " order by u.createdtime DESC ";

    private String TXN_SQL = "select "
            + "u.ID, " //0
            + "u.USERID, " //g.STAFFSTATUS //1
            + "u.USERNAME, " //g.CATEGORY //2 // s2.DESCRIPTION bb
            + "u.OPERATION, " //3
            + "u.DESCRIPTION, " //4
            + "u.DEVICE, " //5
            + "u.IP, " //6
            + "u.CREATEDTIME, " //g.STATUS //7
            + "u.TYPE " // TYPE //8
            + "from mob_audittrace u "
            + "where ";

    public List<MobLoginHistoryBean> getSearchList(MobLoginHistoryInputBean inputBean, int max, int first, String orderBy) throws Exception {
        List<MobLoginHistoryBean> dataList = new ArrayList<MobLoginHistoryBean>();
        Session session = null;
        try {
            if (orderBy.equals(" ") || orderBy == null) {
                orderBy = "asc";
            }
            long count = 0;

            String where = this.makeWhereClause(inputBean);

            session = HibernateInit.sessionFactory.openSession();

            String sqlCount = "select count(id) from MobAudittrace as u where " + where;
            Query queryCount = session.createQuery(sqlCount);

            Iterator itCount = queryCount.iterate();
            count = (Long) itCount.next();

            if (count > 0) {

                String sqlSearch = "from MobAudittrace u where " + where + " Order by u.id desc ";
                System.err.println(sqlSearch);
                Query querySearch = session.createQuery(sqlSearch);
                querySearch.setMaxResults(max);
                querySearch.setFirstResult(first);

                Iterator it = querySearch.iterate();
                while (it.hasNext()) {
                    MobLoginHistoryBean atmlocations = new MobLoginHistoryBean();
                    MobAudittrace mobAudittrace = (MobAudittrace) it.next();

                    try {

                        atmlocations.setId(mobAudittrace.getId().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setId("--");
                    }
                    try {
                        atmlocations.setUserid(mobAudittrace.getUserid().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setUserid("--");
                    }
                    try {
                        atmlocations.setUsername(mobAudittrace.getUsername().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setUsername("--");
                    }
                    try {
                        atmlocations.setOperation(mobAudittrace.getOperation().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setOperation("--");
                    }

                    try {
                        atmlocations.setDescription(mobAudittrace.getDescription().toString());
                    } catch (NullPointerException e) {
                        atmlocations.setDescription("--");
                    }

                    try {
                        atmlocations.setDevice(mobAudittrace.getDevice());
                    } catch (Exception e) {
                        atmlocations.setDevice("--");
                    }
                    
                    try {
                        atmlocations.setType(mobAudittrace.getType());
                    } catch (Exception e) {
                        atmlocations.setType("--");
                    }

                    try {
                        atmlocations.setIp(mobAudittrace.getIp());
                    } catch (Exception e) {
                        atmlocations.setIp("--");
                    }

                    try {
                        atmlocations.setCreatedtime(mobAudittrace.getCreatedtime().toString());
                    } catch (Exception e) {
                        atmlocations.setCreatedtime("--");
                    }

                    atmlocations.setFullCount(count);
                    dataList.add(atmlocations);
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

    public Object generateExcelReport(MobLoginHistoryInputBean inputBean) throws Exception {
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

            if (queryCount.uniqueResult() != null) {
                count = ((Number) queryCount.uniqueResult()).intValue();
                System.err.println("count " + count);
            }

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
                            MobLoginHistoryBean dataBean = new MobLoginHistoryBean();

                            try {

                                if (objArr[0] == null) {
                                    dataBean.setId("--");
                                    System.err.println(dataBean.getId());
                                } else {
                                    dataBean.setId(objArr[0].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setId("--");
                            }

                            try {

                                if (objArr[1] == null) {
                                    dataBean.setUserid("--");
                                } else {
                                    dataBean.setUserid(objArr[1].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUserid("--");
                            }

                            //null has to be checked for every foreign keys
                            try {

                                if (objArr[2] == null) {
                                    dataBean.setUsername("--");
                                } else {
                                    dataBean.setUsername(objArr[2].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setUsername("--");
                            }

                            try {
                                dataBean.setOperation(objArr[3].toString());
                                if (objArr[3] == null) {
                                    dataBean.setOperation("--");
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setOperation("--");
                            }

                            try {

                                if (objArr[4] == null) {
                                    dataBean.setDescription("--");
                                } else {
                                    dataBean.setDescription(objArr[4].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setDescription("--");
                            }

                            try {
                                if (objArr[5] == null) {
                                    dataBean.setDevice("--");
                                } else {
                                    dataBean.setDevice(objArr[5].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setDevice("--");
                            }

                            try {
                                if (objArr[6] == null) {
                                    dataBean.setIp("--");
                                } else {
                                    dataBean.setIp(objArr[6].toString());
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setIp("--");
                            }

                            try {
                                if (objArr[7] == null) {
                                    dataBean.setCreatedtime("--");
                                } else {
                                    dataBean.setCreatedtime(objArr[7].toString().substring(0, 19));
                                }
                            } catch (NullPointerException npe) {
                                dataBean.setCreatedtime("--");
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
//                        sheet.autoSizeColumn(i);
                    }

                    returnObject = workbook;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnObject;
    }

    private String makeWhereClauseForExcel(MobLoginHistoryInputBean inputBean) throws ParseException {
        String where = "1=1";

        if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {
            where += " and u.ID LIKE '%" + inputBean.getId() + "%'";
        }
        if (inputBean.getUserid() != null && !inputBean.getUserid().isEmpty()) {
            where += " and u.USERID LIKE '%" + inputBean.getUserid() + "%'";
        }
        if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {
            where += " and u.USERNAME LIKE '%" + inputBean.getUsername() + "%'";
        }

        String date = inputBean.getTodate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(inputBean.getTodate()));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = sdf.format(c.getTime());
        if (inputBean.getTodate() != null && !inputBean.getTodate().isEmpty()) {
            where += " and u.CREATEDTIME <TO_DATE('" + date + "','yyyy-mm-dd')";
        }

        return where;
    }

    private SXSSFWorkbook createExcelTopSection(MobLoginHistoryInputBean inputBean) throws Exception {

        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
        Sheet sheet = workbook.createSheet("MobileLoginHistory_Report");

        CellStyle fontBoldedUnderlinedCell = ExcelCommon.getFontBoldedUnderlinedCell(workbook);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Priority Banking Mobile Solution");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Mobile Login History Report");
        cell.setCellStyle(fontBoldedUnderlinedCell);

        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("From Date");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getFromdate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("To Date");
        cell = row.createCell(4);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getTodate()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("User Id");
        cell = row.createCell(1);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getUserid()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

        cell = row.createCell(3);
        cell.setCellValue("User Name");
        cell = row.createCell(4);
        cell.setCellValue(CommonDAO.replaceEmptyorNullStringToALL(inputBean.getUsername()));
        cell.setCellStyle(ExcelCommon.getAligneCell(workbook, null, XSSFCellStyle.ALIGN_RIGHT));

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
        cell.setCellValue("User ID");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("User Name");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("Operation");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(4);
        cell.setCellValue("Description");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(5);
        cell.setCellValue("Device");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(6);
        cell.setCellValue("IP");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(7);
        cell.setCellValue("Created Time");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }

    private void writeTemporaryFile(SXSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {

            file = new File(directory);
            if (!file.exists()) {
                System.out.println("Directory created or not : " + file.mkdirs());
            }

            if (fileCount > 0) {
                file = new File(directory + File.separator + "Mobile Login History Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Mobile Login History Report.xlsx");
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

    private int createExcelTableBodySection(SXSSFWorkbook workbook, MobLoginHistoryBean dataBean, int currrow, int rownumber) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        Row row = sheet.createRow(currrow++);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(0);
        cell.setCellValue(rownumber);
        cell.setCellStyle(rowColumnCell);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getUserid());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getUsername());
        cell.setCellStyle(rowColumnCell);
        cell = row.createCell(3);
        cell.setCellValue(dataBean.getOperation());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(4);
        cell.setCellValue(dataBean.getDescription());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(5);
        cell.setCellValue(dataBean.getDevice());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(6);
        cell.setCellValue(dataBean.getIp());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(7);
        cell.setCellValue(dataBean.getCreatedtime().substring(0, 19));
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


    public String getStatusDescriptionByCode(String statuscode) throws Exception {

        String Rescode = null;
        Session session = null;
        try {
            session = HibernateInit.sessionFactory.openSession();
            if (statuscode.equals("--")) {
                Rescode = "--";
            } else {

                String sql = "from Status as t where t.statuscode =:statusCode";
                Query query = session.createQuery(sql).setString("statusCode", statuscode);
                Rescode = ((Status) query.list().get(0)).getDescription();
            }
        } catch (Exception e) {
            System.err.println("error rescode" + Rescode);
            Rescode = "--";
            throw e;
        } finally {
            try {
                session.flush();
                session.close();
            } catch (Exception e) {
                throw e;

            }
        }
        return Rescode;
    }

    

    private String makeWhereClause(MobLoginHistoryInputBean inputBean) {
        String where = "1=1";

        if (inputBean.getId() != null && !inputBean.getId().isEmpty()) {
            where += " and lower(u.id) like lower('%" + inputBean.getId().trim() + "%')";
        }
        if (inputBean.getUserid() != null && !inputBean.getUserid().isEmpty()) {
            where += " and lower(u.userid) like lower('%" + inputBean.getUserid() + "%')";
        }
        if (inputBean.getUsername() != null && !inputBean.getUsername().isEmpty()) {
            where += " and lower(u.username) like lower('%" + inputBean.getUsername() + "%')";
        }
        if (inputBean.getType()!= null && !inputBean.getType().isEmpty()) {
            where += " and lower(u.type) like lower('%" + inputBean.getType() + "%')";
        }
        try {
            String date1 = inputBean.getTodate();                                   // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date1));
            c.add(Calendar.DATE, 1);                                                // number of days to add
            sdf.applyPattern("dd-MMM-yy");
            date1 = sdf.format(c.getTime());                                        // dt is now the new date

            String datef = inputBean.getFromdate();                                 // Start date
            SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cf = Calendar.getInstance();
            cf.setTime(sdff.parse(datef));
            cf.add(Calendar.DATE, 0);
            sdff.applyPattern("dd-MMM-yy");
            datef = sdff.format(cf.getTime());

            if (inputBean.getTodate() != null && !inputBean.getFromdate().isEmpty()) {
                where += " and u.createdtime >='" + datef + "'";
            }
            if (date1 != null && !date1.isEmpty()) {
                where += " and u.createdtime <'" + date1 + "'";
            }
        } catch (Exception ee) {

        }
        return where;
    }
}
