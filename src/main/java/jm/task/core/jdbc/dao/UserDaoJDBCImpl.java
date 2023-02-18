package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    static Connection con = Util.getConnect();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String request = " CREATE TABLE IF NOT EXISTS usertable " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(20) NOT NULL, " +
                "last_name VARCHAR(20) NOT NULL, " +
                "age INT(3) NOT NULL, PRIMARY KEY (id))";
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            System.out.println("Fail when create statement");
        }
    }

    public void dropUsersTable() {
        String request = "DROP TABLE IF EXISTS usertable";
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            System.out.println("Fail when drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String request = "INSERT INTO usertable (name, last_name, age) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preStatement = con.prepareStatement(request)) {
            preStatement.setString(1, name);
            preStatement.setString(2, lastName);
            preStatement.setByte(3, age);
            preStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fail when add user");
        }
    }

    public void removeUserById(long id) {
        String request = "DELETE FROM usertable " +
                         "WHERE id = ?";
        try (PreparedStatement preStatement = con.prepareStatement(request)) {
            preStatement.setLong(1, id);
            preStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Fail, when remove user");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String request = "SELECT * FROM usertable";
        try (PreparedStatement preStatement = con.prepareStatement(request)) {
            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User curUser = new User(name, lastName, age);
                curUser.setId(id);
                users.add(curUser);
            }

        } catch (SQLException e){
            System.out.println("Fail, get users");
        }
        return users;
    }

    public void cleanUsersTable() {
        String request = "DELETE FROM usertable";
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            System.out.println("Fail when clean table");
        }
    }
}
