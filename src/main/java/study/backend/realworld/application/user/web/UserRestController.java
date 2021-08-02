package study.backend.realworld.application.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserService;
import study.backend.realworld.application.user.web.request.LoginRequest;
import study.backend.realworld.application.user.web.request.RegisterRequest;
import study.backend.realworld.application.user.web.response.UserResponse;
import study.backend.realworld.infra.security.jwt.TokenGenerator;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final TokenGenerator tokenGenerator;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(
                UserResponse.of(userService.register(request.toRegisterRequest()))
        );
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.login(request.toLoginRequest());
        String token = tokenGenerator.generateToken(user.getEmail());

        return ResponseEntity.ok(UserResponse.of(user, token));
    }
}
