package study.backend.realworld.application.user.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import study.backend.realworld.application.IntegrationTestUtils;
import study.backend.realworld.application.user.dto.request.LoginRequest;
import study.backend.realworld.application.user.dto.request.RegisterRequest;
import study.backend.realworld.application.user.dto.request.UpdateProfileRequest;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerTest extends IntegrationTestUtils {

    @DisplayName("올바르지 않은 요청 데이터로 회원가입을 시도하면 실패한다.")
    @MethodSource("invalidRegisterDto")
    @ParameterizedTest
    void when_register_user_invalid_body(RegisterRequest dto) throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("이미 존재하는 email 정보로 가입을 시도하면 실패한다.")
    @Test
    void when_register_fail_duplicate_email() throws Exception {
        RegisterRequest request = new RegisterRequest("userName", "user@email.com", "password");

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isConflict());
    }

    @DisplayName("로그인 성공")
    @Test
    void when_user_login_expect_is_success() throws Exception {
        LoginRequest requestDTO = new LoginRequest("user@email.com", "password");

        mockMvc.perform(post("/api/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDTO)))
                .andExpect(status().isOk());
    }

    @DisplayName("잘못된 email 정보로 로그인을 시도하면 실패한다.")
    @Test
    void when_user_login_fail_email_not_matched() throws Exception {
        LoginRequest requestDTO = new LoginRequest("user22@email.com", "password");

        mockMvc.perform(post("/api/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("잘못된 비밀번호를 입력했을 경우 로그인이 실패한다.")
    @Test
    void when_user_login_fail_password_not_matched() throws Exception {
        LoginRequest requestDTO = new LoginRequest("user@email.com", "wrongPassword");

        mockMvc.perform(post("/api/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDTO)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("user 정보 조회 성공")
    @Test
    void when_user_find_success() throws Exception{
        mockMvc.perform(get("/api/user")
                .header("Authorization", setUpToken)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("user 정보 업데이트 성공")
    @Test
    void when_user_update_success() throws Exception{
        UpdateProfileRequest request = new UpdateProfileRequest("hello", "hello@world.com",
                "password", "", "");
        mockMvc.perform(put("/api/user")
                .header("Authorization", setUpToken)
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("이미 존재하는 email로 정보 수정을 시도하면 실패한다.")
    @Test
    void when_user_update_fail_duplicate_email() throws Exception{
        UpdateProfileRequest request = new UpdateProfileRequest("hello", "user@email.com",
                "password", "", "");
        mockMvc.perform(put("/api/user")
                .header("Authorization", setUpToken)
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    private static Stream<Arguments> invalidRegisterDto() {
        return Stream.of(
                Arguments.of(new RegisterRequest("userName", "not.email.com", "password")),
                Arguments.of(new RegisterRequest("", "user@email.com", "password")),
                Arguments.of(new RegisterRequest("userName", "user@email.com", ""))
        );
    }
}