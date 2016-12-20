package io.pivotal.producer.users;

import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class UserRepository {
    public List<User> getAll() {
        return asList(
            new User("4")
        );
    }
}
