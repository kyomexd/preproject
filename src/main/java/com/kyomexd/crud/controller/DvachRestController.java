package com.kyomexd.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyomexd.crud.model.RequestLogDTO;
import com.kyomexd.crud.service.DvachRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dvach")
public class DvachRestController {

    @Autowired
    private DvachRequestService dvachRequestService;

    @GetMapping("/captcha")
    public String getCaptchaId() throws JsonProcessingException {
        return dvachRequestService.getCaptchaId();
    }

    @PostMapping("/logs")
    public String postLogsOnDvach(@RequestBody RequestLogDTO requestDTO) {
        return dvachRequestService.sendPostWithLogs(requestDTO.getId(), requestDTO.getValue(), requestDTO.getComment());
    }

}
