package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository repository;

    @Override
    public List<Request> findAll() {
        return repository.findAll();
    }

    @Override
    public Request getRequestById(long id) {
        return repository.getRequestById(id);
    }

    @Override
    public void saveRequest(Request request) {
        repository.saveRequest(request);
    }

    @Override
    public void resolveRequest(long id) {
        repository.resolveRequest(id);
    }

    @Override
    public int findAllPending() {
        return repository.findAllPending();
    }
}
