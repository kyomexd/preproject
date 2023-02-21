package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;
    // реализуйте настройку соединения с БД
    public static Connection getSQLConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "kyomexd");
            } catch (SQLException sqle) {
                System.out.println("Connection failed: " + sqle.getMessage());
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(AvailableSettings.URL, "jdbc:postgresql://127.0.0.1:5432/postgres");
                properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(AvailableSettings.USER, "postgres");
                properties.put(AvailableSettings.PASS, "kyomexd");
                properties.put(AvailableSettings.DRIVER, "org.postgresql.Driver");
                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException hex) {
                System.out.println("Connection failed: ");
                hex.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
