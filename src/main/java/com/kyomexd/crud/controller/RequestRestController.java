package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.service.RequestService;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/requests")
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class RequestRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @RolesAllowed("ADMIN")
    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.findAll();
    }

    @RolesAllowed({ "USER", "ADMIN" })
    @PostMapping("/save")
    @ResponseBody
    public void saveRequest(@RequestBody Request request) {
        userService.updateRequests(userService.getUserByName(request.getUsername()), request);
        requestService.saveRequest(request);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/pending")
    public int getPendingRequests() {
        return requestService.findAllPending();
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/decline/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void declineRequest(@PathVariable long id) {
        requestService.resolveRequest(id);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptRequest(@PathVariable long id) {
        Request request = requestService.getRequestById(id);
        requestService.resolveRequest(id);
        userService.addRole(userService.getUserByName(request.getUsername()).getId(), new Role(2L, "ROLE_ADMIN"));
    }
}
