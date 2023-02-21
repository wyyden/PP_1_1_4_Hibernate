package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String request = " CREATE TABLE IF NOT EXISTS users " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(20) NOT NULL, " +
                "last_name VARCHAR(20) NOT NULL, " +
                "age INT(3) NOT NULL, PRIMARY KEY (id))";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();
            System.out.println("The table has been created");
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String request = "DROP TABLE IF EXISTS users";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();
            System.out.println("Table deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Saving error");
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("User deleted");
        } catch (HibernateException e) {
            System.out.println("Deletion error");
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            users = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (HibernateException e){
            System.out.println("Error getting the list of users");
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("All users have been deleted");
        } catch (HibernateException e) {
            System.out.println("Deletion error");
        } finally {
            session.close();
        }
    }
}
