package com.kyomexd.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.service.RequestService;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/requests")
public class UserRequestRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @PostMapping("/save")
    @ResponseBody
    public void saveRequest(@RequestBody Request request) {
        userService.updateRequests(userService.getUserByName(request.getUsername()), request);
        requestService.saveRequest(request);
    }
}
