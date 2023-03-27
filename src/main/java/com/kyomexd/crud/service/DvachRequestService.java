package com.kyomexd.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DvachRequestService {

    public String getCaptchaId() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://2ch.hk/api/captcha/2chcaptcha/id?board=pr&thread=2653852", String.class);
        Map<String, String> responseMap = new ObjectMapper().readValue(response.getBody(), Map.class);
        return responseMap.get("id");
    }

    public String sendPost(String captchaId, String captchaValue, String comment) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
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
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://2ch.hk/user/posting", requestEntity, String.class);
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }


}
