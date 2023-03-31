package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RequestRepositoryImpl implements RequestRepository{

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Request> findAll() {
        return entityManager.createQuery("SELECT r FROM Request r").getResultList();
    }

    @Override
    public int findAllPending() {
        return entityManager.createQuery("SELECT r FROM Request r WHERE resolved = false").getResultList().size();
    }

    @Override
    public Request getRequestById(long id) {
        return entityManager.find(Request.class, id);
    }

    @Override
    @Transactional
    public void saveRequest(Request request) {
        entityManager.persist(request);
    }

    @Override
    @Transactional
    public void resolveRequest(long id) {
        Request request = getRequestById(id);
        request.setResolved(true);
    }
}
