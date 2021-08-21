package study.backend.realworld.application.user.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import study.backend.realworld.application.IntegrationTests;
import study.backend.realworld.application.user.web.request.LoginRequest;
import study.backend.realworld.application.user.web.request.RegisterRequest;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerTest extends IntegrationTests {

    @MethodSource("invalidRegisterDto")
    @ParameterizedTest
    void when_register_user_invalid_body(RegisterRequest dto) throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_user_login_expect_is_success() throws Exception {
        LoginRequest requestDTO = new LoginRequest("user@email.com", "password");

        mockMvc.perform(post("/api/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void when_user_find_success() throws Exception{
        mockMvc.perform(get("/api/user")
                .header("Authorization", setUpToken)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private static Stream<Arguments> invalidRegisterDto() {
        return Stream.of(
                Arguments.of(new RegisterRequest("userName", "not.email.com", "password")),
                Arguments.of(new RegisterRequest("", "user@email.com", "password")),
                Arguments.of(new RegisterRequest("userName", "user@email.com", ""))
        );
    }
}