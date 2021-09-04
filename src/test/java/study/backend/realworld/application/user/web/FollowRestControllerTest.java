package study.backend.realworld.application.user.web;

import org.junit.jupiter.api.BeforeEach;
import study.backend.realworld.application.IntegrationTests;
import study.backend.realworld.application.user.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class FollowRestControllerTest extends IntegrationTests {

    private User user;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        user = userRepository.save(User.of("aaa@email.com", "aaa", "password"));
    }
}