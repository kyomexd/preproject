package jm.task.core.jdbc.service;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoHibernateImpl hiberDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        hiberDao.createUsersTable();
    }

    public void dropUsersTable() {
        hiberDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        hiberDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        hiberDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return hiberDao.getAllUsers();
    }

    public void cleanUsersTable() {
        hiberDao.cleanUsersTable();
    }
}
