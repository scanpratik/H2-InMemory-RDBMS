package com.pp.util;


import com.pp.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/lab");
                settings.put(Environment.USER, "labuser");
                settings.put(Environment.PASS, "admin");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                // c3p0 configuration
                settings.put(Environment.C3P0_MIN_SIZE, 5); //Minimum size of pool
                settings.put(Environment.C3P0_MAX_SIZE, 20); //Maximum size of pool
                settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1); //Number of connections acquired at a time when pool is exhausted
                settings.put(Environment.C3P0_TIMEOUT, 1800); //Connection idle time
                settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
                settings.put(Environment.C3P0_CONFIG_PREFIX + ".initialPoolSize", 5);

                configuration.setProperties(settings);
                configuration.addPackage("com.pp.entity");
                configuration.addAnnotatedClass(Student.class);

                registry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                System.out.println("Hibernate Java Config serviceRegistry created");
                sessionFactory = configuration.buildSessionFactory(registry);
                return sessionFactory;

            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
