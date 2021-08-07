package study.backend.realworld.application.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import study.backend.realworld.application.user.domain.UserRepository;
import study.backend.realworld.application.user.domain.UserService;
import study.backend.realworld.application.user.web.request.LoginRequest;
import study.backend.realworld.application.user.web.request.RegisterRequest;
import study.backend.realworld.infra.security.jwt.TokenGenerator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenGenerator tokenGenerator;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private String testToken;

    @BeforeEach
    void setUp() {
        testToken = "Token " + tokenGenerator.generateToken("user@email.com");
    }

    @MethodSource("invalidRegisterDto")
    @ParameterizedTest
    void when_register_user_invalid_body(RegisterRequest dto) throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_user_login_expect_is_failed() throws Exception {
        LoginRequest requestDTO = new LoginRequest("user@email.com", "password");

        mockMvc.perform(post("/api/users/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.token").value("user@email.com"));
    }

    @Test
    void when_user_find_success() throws Exception{
        System.out.println(testToken);
        mockMvc.perform(get("/api/user")
                .header("Authorization", testToken)
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