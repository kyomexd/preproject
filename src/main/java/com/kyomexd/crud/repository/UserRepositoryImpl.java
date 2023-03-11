package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private EntityManager entityManager;

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.name = :name", User.class);
        User user = null;
        try {
            user = query.setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InternalAuthenticationServiceException("No user with such credentials. Please sign in again");
        }
        return user;
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateUser(int id, String name, int age, String email, Set<Role> roles) {
        User foundUser = entityManager.find(User.class, id);
        foundUser.setName(name);
        foundUser.setAge(age);
        foundUser.setEmail(email);
        foundUser.setRoles(roles);
    }

    @Transactional
    public void deleteUser(int id) {
        User foundUser = entityManager.find(User.class, id);
        entityManager.remove(foundUser);
    }
}
