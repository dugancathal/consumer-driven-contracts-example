package io.pivotal.producer.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
public class UsersControllerTest {
    MockMvc mvc;

    @Mock
    UserRepository mockRepository;

    @Before
    public void setUp() throws Exception {
        when(mockRepository.getAll()).thenReturn(asList(
            new User("4", "name")
        ));
        mvc = standaloneSetup(new UsersController(mockRepository)).build();
    }

    @Test
    public void testControllerIndexReturnsListOfUsers() throws Exception {
        mvc.perform(get("/users"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("[0]id").exists())
            .andExpect(jsonPath("[0]name").exists());
    }
}
