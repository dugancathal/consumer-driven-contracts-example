package io.pivotal.consumer.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UsersController {
    @Autowired
    RestTemplate restTemplate;

    public UsersController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8080", String.class);
        return forEntity.getBody();
    }
}
