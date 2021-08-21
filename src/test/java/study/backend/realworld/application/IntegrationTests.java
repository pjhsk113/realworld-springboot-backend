package study.backend.realworld.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserRepository;
import study.backend.realworld.infra.security.jwt.TokenGenerator;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class IntegrationTests {
    protected static final String AUTHORIZATION = "Authorization";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenGenerator tokenGenerator;

    protected String setUpToken;
    protected User setUpUser;

    @BeforeEach
    protected void setUp() {
        setUpUser = userRepository.save(
                User.of("user@email.com", "user", passwordEncoder.encode("password"))
        );

        setUpToken = "Token " +tokenGenerator.generateToken(setUpUser.getEmail());
    }
}
