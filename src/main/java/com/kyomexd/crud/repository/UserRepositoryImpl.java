package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.net.BindException;
import java.util.Collections;
import java.util.Date;
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

    public User getUserByName(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        User user = null;
        try {
            user = query.setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InternalAuthenticationServiceException("No user with such credentials. Please sign in again");
        }
        return user;
    }

    @Transactional
    public void addUser(@Validated User user) {
        try {
            entityManager.persist(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("User with these credentials already exists. Please choose other name/email");
        }
    }

    @Transactional
    public void updateUser(int id, String name, String age, String email, String city, Set<Role> roles) {
        User foundUser = entityManager.find(User.class, id);
        foundUser.setName(name);
        foundUser.setAge(age);
        foundUser.setEmail(email);
        foundUser.setRoles(roles);
        foundUser.setCity(city);
    }

    @Transactional
    public void deleteUser(int id) {
        User foundUser = entityManager.find(User.class, id);
        entityManager.remove(foundUser);
    }

    @Transactional
    public void updateRequests(User user, Request request) {
        Set<Request> requests = user.getRequests();
        requests.add(request);
        user.setRequests(requests);
    }

    @Transactional
    public void addRole(int id, Role role) {
        User user = entityManager.find(User.class, id);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
    }
}
