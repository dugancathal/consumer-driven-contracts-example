package io.pivotal.consumer.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class UsersController {
    @Autowired
    RestTemplate restTemplate;
    private String url;

    public UsersController(RestTemplate restTemplate, @Value("${userservice.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(url + "/users", User[].class);
        return asList(forEntity.getBody());
    }
}
