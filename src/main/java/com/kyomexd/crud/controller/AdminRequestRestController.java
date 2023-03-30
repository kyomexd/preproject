package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.service.RequestService;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/requests")
public class AdminRequestRestController {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.findAll();
    }

    @GetMapping("/pending")
    public int getPendingRequests() {
        return requestService.findAllPending();
    }

    @PostMapping("/decline/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void declineRequest(@PathVariable long id) {
        requestService.resolveRequest(id);
    }

    @PostMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptRequest(@PathVariable long id) {
        Request request = requestService.getRequestById(id);
        requestService.resolveRequest(id);
        userService.addRole(userService.getUserByName(request.getUsername()).getId(), new Role(2L, "ROLE_ADMIN"));
    }
}
