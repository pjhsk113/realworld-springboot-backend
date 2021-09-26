package study.backend.realworld.application.user.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.backend.realworld.application.IntegrationTests;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.exception.ExistsUserException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowRestControllerTest extends IntegrationTests {

    private User user;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        user = userRepository.save(User.of("test@email.com", "test", "password"));
    }

    @Test
    void when_follow_success() throws Exception {
        String username = "test";

        mockMvc.perform(post("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @Test
    void when_follow_fail_exist_user() throws Exception {
        setUpUser.follow(user);

        String username = "test";

        mockMvc.perform(post("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isBadRequest());
    }

}