package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoRepository implements UserDao{

    @Autowired
    private EntityManager entityManager;

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public User findUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateUser(int id, String name, int age, String email) {
        User foundUser = entityManager.find(User.class, id);
        foundUser.setName(name);
        foundUser.setAge(age);
        foundUser.setEmail(email);
    }

    @Transactional
    public void deleteUser(int id) {
        User foundUser = entityManager.find(User.class, id);
        entityManager.remove(foundUser);
    }
}
