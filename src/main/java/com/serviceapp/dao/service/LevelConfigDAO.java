/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.service;

import com.serviceapp.bean.service.LevelConfigBean;
import com.serviceapp.bean.service.LevelConfigInputBean;
import com.serviceapp.bean.usermanagement.SystemuserBean;
import com.serviceapp.bean.usermanagement.SystemuserInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobBassLevelConfig;
import com.serviceapp.mapping.Passwordpolicy;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.mapping.Systemuser;
import com.serviceapp.varlist.MessageVarlist;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class LevelConfigDAO {

    public List<LevelConfigBean> getLevels() throws Exception {
        List<LevelConfigBean> dataList = new ArrayList<LevelConfigBean>();
        Session session = null;
        try {

            long count = 0;
            session = HibernateInit.sessionFactory.openSession();

            String sqlSearch = "from MobBassLevelConfig u ";

            Query querySearch = session.createQuery(sqlSearch);

            Iterator it = querySearch.iterate();
            while (it.hasNext()) {
                LevelConfigBean levelconfigbean = new LevelConfigBean();
                MobBassLevelConfig bassLevelConfig = (MobBassLevelConfig) it.next();

                try {
                    levelconfigbean.setLevel(bassLevelConfig.getLevel());
                } catch (NullPointerException e) {
                    levelconfigbean.setLevel("--");
                }
                try {
                    levelconfigbean.setDescription(bassLevelConfig.getDescription());
                } catch (NullPointerException e) {
                    levelconfigbean.setDescription("--");
                }
                try {
                    levelconfigbean.setPrice(Double.toString(bassLevelConfig.getPrice()));
                } catch (NullPointerException e) {
                    levelconfigbean.setPrice("--");
                }
                try {
                    levelconfigbean.setServiceCount(Integer.toString(bassLevelConfig.getRequiredServiceCount()));
                } catch (NullPointerException e) {
                    levelconfigbean.setServiceCount("--");
                }

                levelconfigbean.setFullCount(count);
                dataList.add(levelconfigbean);
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

    public String updateLevelConfig(LevelConfigInputBean inputBean, Systemaudit audit) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);

            String oldV = "";
            String newV = "";

            //basic
            MobBassLevelConfig basic = (MobBassLevelConfig) session.get(MobBassLevelConfig.class, inputBean.getBasic());

            if (basic != null) {
                oldV += basic.getLevel() + "|"
                        + basic.getDescription() + "|"
                        + basic.getPrice() + "|"
                        + basic.getRequiredServiceCount() + "|";

                basic.setPrice(Double.parseDouble(inputBean.getBasicPrice()));
                basic.setRequiredServiceCount(Integer.parseInt(inputBean.getBasicSerCont()));

                newV += basic.getLevel() + "|"
                        + basic.getDescription() + "|"
                        + basic.getPrice() + "|"
                        + basic.getRequiredServiceCount() + "|";

                session.update(basic);
            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }
            //bronze
            MobBassLevelConfig bronze = (MobBassLevelConfig) session.get(MobBassLevelConfig.class, inputBean.getBronze());

            if (bronze != null) {
                oldV += bronze.getLevel() + "|"
                        + bronze.getDescription() + "|"
                        + bronze.getPrice() + "|"
                        + bronze.getRequiredServiceCount() + "|";

                bronze.setPrice(Double.parseDouble(inputBean.getBronzePrice()));
                bronze.setRequiredServiceCount(Integer.parseInt(inputBean.getBronzeSerCont()));

                newV += bronze.getLevel() + "|"
                        + bronze.getDescription() + "|"
                        + bronze.getPrice() + "|"
                        + bronze.getRequiredServiceCount() + "|";

                session.update(bronze);
            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }

            //silver
            MobBassLevelConfig silver = (MobBassLevelConfig) session.get(MobBassLevelConfig.class, inputBean.getSilver());

            if (silver != null) {
                oldV += silver.getLevel() + "|"
                        + silver.getDescription() + "|"
                        + silver.getPrice() + "|"
                        + silver.getRequiredServiceCount() + "|";

                silver.setPrice(Double.parseDouble(inputBean.getSilverPrice()));
                silver.setRequiredServiceCount(Integer.parseInt(inputBean.getSilverSerCont()));

                newV += silver.getLevel() + "|"
                        + silver.getDescription() + "|"
                        + silver.getPrice() + "|"
                        + silver.getRequiredServiceCount() + "|";

                session.update(silver);
            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }

            //gold
            MobBassLevelConfig gold = (MobBassLevelConfig) session.get(MobBassLevelConfig.class, inputBean.getGold());

            if (gold != null) {
                oldV += gold.getLevel() + "|"
                        + gold.getDescription() + "|"
                        + gold.getPrice() + "|"
                        + gold.getRequiredServiceCount() + "|";

                gold.setPrice(Double.parseDouble(inputBean.getGoldPrice()));
                gold.setRequiredServiceCount(Integer.parseInt(inputBean.getGoldSerCont()));

                newV += gold.getLevel() + "|"
                        + gold.getDescription() + "|"
                        + gold.getPrice() + "|"
                        + gold.getRequiredServiceCount() + "|";

                session.update(gold);
            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }

            //gold
            MobBassLevelConfig platinum = (MobBassLevelConfig) session.get(MobBassLevelConfig.class, inputBean.getPlatinum());

            if (platinum != null) {
                oldV += platinum.getLevel() + "|"
                        + platinum.getDescription() + "|"
                        + platinum.getPrice() + "|"
                        + platinum.getRequiredServiceCount();

                platinum.setPrice(Double.parseDouble(inputBean.getPlatinumPrice()));
                platinum.setRequiredServiceCount(Integer.parseInt(inputBean.getPlatinumSerCont()));

                newV += platinum.getLevel() + "|"
                        + platinum.getDescription() + "|"
                        + platinum.getPrice() + "|"
                        + platinum.getRequiredServiceCount();

                session.update(platinum);
            } else {
                message = MessageVarlist.COMMON_NOT_EXISTS;
            }

            System.out.println("new == " + newV);
            System.out.println("old == " + oldV);

            audit.setOldvalue(oldV);
            audit.setNewvalue(newV);
            audit.setCreatetime(sysDate);
            audit.setLastupdatedtime(sysDate);

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
}
