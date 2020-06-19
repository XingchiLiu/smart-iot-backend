package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:45
 */
public class ForwardAction extends Action {

    public ForwardAction(String target, JSONObject data) {
        super(target, data);
    }

    @Override
    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + this.target;
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(data.toString(), headers);
        ResponseEntity<String> res = restTemplate.postForEntity(url, formEntity, String.class);
    }
}
