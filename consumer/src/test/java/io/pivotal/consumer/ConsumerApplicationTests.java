package io.pivotal.consumer;

import io.pivotal.consumer.users.User;
import io.pivotal.consumer.users.UsersController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {
    @Test
    public void contextLoads() {
        UsersController usersController = new UsersController(new RestTemplate(), "http://localhost:8081");
        List<User> users = usersController.getUsers();
        assertThat(users, equalTo(asList(
            new User("4", "barbara")
        )));
    }
}
