package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> findAll();
    Request getRequestById(long id);
    void saveRequest(Request request);
    void resolveRequest(long id);
    int findAllPending();
}
