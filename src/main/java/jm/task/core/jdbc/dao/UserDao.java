package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;
/**
 * Abstract interface for CRUD operations with User entities in DB
 */
public interface UserDao {
    /**
     * Interface method for creating a table in DB
     */
    void createUsersTable();
    /**
     * Interface method for dropping a table in DB
     */
    void dropUsersTable();
    /**
     * Interface method for saving a user by his name, lastName and age parameters in DB
     */
    void saveUser(String name, String lastName, byte age);
    /**
     * Interface method for removing a user from the table by id
     */
    void removeUserById(long id);
    /**
     * Interface method returning a List collection from DB with all users inside
     */
    List<User> getAllUsers();
    /**
     * Interface method for deleting all users from a table in DB
     */
    void cleanUsersTable();
}
