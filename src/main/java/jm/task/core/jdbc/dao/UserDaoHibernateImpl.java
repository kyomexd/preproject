package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
/**
 * Hibernate implementation of UserDao, allowing to work with DB through Hibernate library methods
 */
public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {}

    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                "id SERIAL PRIMARY KEY," +
                "name varchar(225) NOT NULL," +
                "last_name varchar(225) NOT NULL," +
                "age smallint NOT NULL)").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createCriteria(User.class).list();
        session.close();
        return users;
    }

    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM users").executeUpdate();
        transaction.commit();
        session.close();
    }
}
