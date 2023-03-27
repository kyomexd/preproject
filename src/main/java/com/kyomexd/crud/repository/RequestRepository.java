package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RequestRepository {

    List<Request> findAll();
    Request getRequestById(long id);
    void saveRequest(Request request);
    void resolveRequest(long id);

}
