package com.thrones;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Hibernateutil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/Task");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "root"); // ✅ ensure correct password
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect"); // ✅ updated dialect
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(properties);

                // Register annotated entity classes
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Task.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to build SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}
