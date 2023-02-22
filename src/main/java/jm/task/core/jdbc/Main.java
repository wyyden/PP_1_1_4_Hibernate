package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        User user1 = new User("Edith", "Bailey", (byte) 14);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User � ������ - " + user1.getName() + " �������� � ���� ������");

        User user2 = new User("Emma", "Larson", (byte) 15);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println("User � ������ - " + user2.getName() + " �������� � ���� ������");

        User user3 = new User("Denise", "Page", (byte) 28);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User � ������ - " + user3.getName() + " �������� � ���� ������");

        User user4 = new User("Edwin", "Adams", (byte) 35);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User � ������ - " + user4.getName() + " �������� � ���� ������");

        List<User> users = userService.getAllUsers();

        for(User user: users) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeSessionFactory();
    }
}
