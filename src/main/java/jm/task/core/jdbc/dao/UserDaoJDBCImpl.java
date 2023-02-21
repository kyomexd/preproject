package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getSQLConnection();

    public void createUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users(" +
                        "id SERIAL NOT NULL PRIMARY KEY," +
                        "name varchar(225) NOT NULL," +
                        "last_name varchar(225) NOT NULL," +
                        "age smallint NOT NULL)")){
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            System.out.println("Couldn't create table: " + sqle.getMessage());
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = connection.prepareStatement(
                "DROP TABLE IF EXISTS users")){
            ps.executeUpdate();
        }
        catch (SQLException sqle) {
            System.out.println("Couldn't drop table: " + sqle.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users(name, last_name, age)" +
                "VALUES(?,?,?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException sqle) {
            System.out.println("Couldn't save user: " + sqle.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("Couldn't remove user: " + sqle.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                allUsers.add(new User(name, lastName, age));
            }
        } catch (SQLException sqle) {
            System.out.println("Couldn't retrieve user data: " + sqle.getMessage());
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException sqle) {
            System.out.println("Couldn't clean users table: " + sqle.getMessage());
        }
    }
}
