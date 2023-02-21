package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        byte age = 20;
        userService.saveUser("Anatolii", "Marandyuk", age);
        userService.saveUser("Vasilii", "Matveev", age);
        userService.saveUser("Sofiia", "Kelep", age);
        userService.saveUser("Alexey", "Menchikov", age);
        List<User> users = userService.getAllUsers();
        users.forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
