package com.mvc.utility;

import com.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtility {
    private final static SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        pros.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");

//        pros.put(Environment.URL, "jdbc:mysql://localhost:3306/a3hshop");
//        pros.put(Environment.USER, "root");
//        pros.put(Environment.PASS, "123456");
        pros.put(Environment.URL, "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_e1964613066b423");
        pros.put(Environment.USER, "b0a4c10073fdbc");
        pros.put(Environment.PASS, "54514ae6");

        conf.setProperties(pros);
        conf.addAnnotatedClass(KhachEntity.class);
        conf.addAnnotatedClass(CategoryEntity.class);
        conf.addAnnotatedClass(ProductEntity.class);
        conf.addAnnotatedClass(OrderEntity.class);
        conf.addAnnotatedClass(DetailorderEntity.class);
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }
}
