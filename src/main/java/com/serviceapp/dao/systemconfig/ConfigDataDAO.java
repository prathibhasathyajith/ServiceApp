/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviceapp.dao.systemconfig;

import com.serviceapp.bean.systemconfig.ConfigDataInputBean;
import com.serviceapp.common.dao.CommonDAO;
import com.serviceapp.listener.HibernateInit;
import com.serviceapp.mapping.MobConfiguration;
import com.serviceapp.mapping.Systemaudit;
import com.serviceapp.varlist.MessageVarlist;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author prathibha_s
 */
public class ConfigDataDAO {

    public List<MobConfiguration> getConfigData() throws Exception {
        List<MobConfiguration> conf = null;
        Session session = null;
        try {

            session = HibernateInit.sessionFactory.openSession();
            String sql = "from MobConfiguration ";

            Query query = session.createQuery(sql);

            if (query.list().size() > 0) {
                conf = (List<MobConfiguration>) query.list();
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
        return conf;
    }

    public String updateConfigData(ConfigDataInputBean inputBean, Systemaudit audit, int count) throws Exception {
        Session session = null;
        Transaction txn = null;
        String message = "";

        try {
            session = HibernateInit.sessionFactory.openSession();
            txn = session.beginTransaction();
            Date sysDate = CommonDAO.getSystemDate(session);
            
            List<MobConfiguration> conf = this.getConfigData();
            String pk = "";

            String oldV = "";
            String newV = "";

            for (int i = 1; i < count+1; i++) {
                pk = conf.get(i-1).getCode();
                MobConfiguration u = (MobConfiguration) session.get(MobConfiguration.class, pk);

                if (u != null) {
                    oldV += u.getValue() + "|";

                    Method getMethod = inputBean.getClass().getMethod(MessageVarlist.CONFIGDATA_METHOD_NAME + i, new Class[]{});
                    String inputData = (String) getMethod.invoke(inputBean, new Object[]{});

                    u.setValue(inputData);

                    newV += u.getValue() + "|";

                    session.update(u);

                } else {
                    message = MessageVarlist.COMMON_NOT_EXISTS;
                }

            }

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
