package study.backend.realworld.application.user.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.backend.realworld.application.IntegrationTests;
import study.backend.realworld.application.user.domain.User;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowRestControllerTest extends IntegrationTests {

    private User user;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        user = super.setUpUser;
    }

    @DisplayName("follow 성공")
    @Test
    void when_follow_success() throws Exception {
        String username = "user";

        mockMvc.perform(post("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("이미 follow 하고 있는 user를 follow 할 경우 실패한다.")
    @Test
    void when_follow_fail_exist_user() throws Exception {
        setUpUser.follow(user);

        String username = "test";

        mockMvc.perform(post("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("존재하지 않는 user를 follow할 경우 실패한다.")
    @Test
    void when_unfollow_fail_cause_user_not_found() throws Exception {
        String username = "unKnown";

        mockMvc.perform(delete("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("unfollow 성공")
    @Test
    void when_unfollow_success() throws Exception {
        setUpUser.follow(user);
        String username = "user";

        mockMvc.perform(delete("/api/profiles/" + username + "/follow")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }
}