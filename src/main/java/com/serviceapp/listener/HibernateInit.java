package com.serviceapp.listener;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author prathibha_s
 */
public class HibernateInit {
    public static SessionFactory sessionFactory;

    public SessionFactory initialize() {
        //            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); //for hibernate 3.0
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
