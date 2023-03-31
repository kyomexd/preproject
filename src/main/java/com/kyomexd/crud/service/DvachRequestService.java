package com.kyomexd.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DvachRequestService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${2chcaptcha_id}")
    private String captchaIdLink;

    @Value("${2ch_posting}")
    private String postingLink;

    public String getCaptchaId() throws JsonProcessingException {
        ResponseEntity<String> response = template.getForEntity(captchaIdLink, String.class);
        Map<String, String> responseMap = objectMapper.readValue(response.getBody(), Map.class);
        return responseMap.get("id");
    }

    public String sendPostWithLogs(String captchaId, String captchaValue, String comment) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(createLogs(captchaId, captchaValue, comment), getPostHeaders());
        ResponseEntity<String> responseEntity = template.postForEntity(postingLink, requestEntity, String.class);
        return responseEntity.getBody();
    }

    private HttpHeaders getPostHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private MultiValueMap<String, String> createLogs(String captchaId, String captchaValue, String comment) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("task", "post");
        data.add("board", "pr");
        data.add("thread", "2653852");
        data.add("usercode", "");
        data.add("code", "");
        data.add("captcha_type", "2chcaptcha");
        data.add("email", "sage");
        data.add("comment", comment);
        data.add("oekaki_image", "");
        data.add("oekaki_metadata", "");
        data.add("2chcaptcha_value", captchaValue);
        data.add("2chcaptcha_id", captchaId);
        data.add("makaka_id", "");
        data.add("makaka_answer", "");
        return data;
    }


}
