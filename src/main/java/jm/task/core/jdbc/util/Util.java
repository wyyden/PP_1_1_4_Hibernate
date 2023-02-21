package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/db1.1.4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Util() {
    }

    public static Connection getConnect() {

        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection error!!!");
           e.printStackTrace();
        }
        return connection;

    }
    public static SessionFactory getSessionFactory(){
        SessionFactory sessionFactory = null;
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.driver_class", DRIVER);
            prop.setProperty("hibernate.connection.url", URL);
            prop.setProperty("hibernate.connection.username", USERNAME);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.order_updates", "true");
            Configuration config = new Configuration();
            config.setProperties(prop);
            config.addAnnotatedClass(User.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable  e) {
            System.out.println("Connection error!!!");
            e.printStackTrace();
        }
        return sessionFactory;
    }
}