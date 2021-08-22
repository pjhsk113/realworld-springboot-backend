package study.backend.realworld.application.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserService;
import study.backend.realworld.application.user.domain.exception.UserNotFountException;
import study.backend.realworld.application.user.web.request.LoginRequest;
import study.backend.realworld.application.user.web.request.RegisterRequest;
import study.backend.realworld.application.user.web.request.UpdateProfileRequest;
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
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) throws Exception {
        return ResponseEntity.ok(
                UserResponse.of(userService.register(request.toRegisterRequest()))
        );
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) throws Exception {
        User user = userService.login(request.toLoginRequest());
        String token = tokenGenerator.generateToken(user.getEmail());

        return ResponseEntity.ok(UserResponse.of(user, token));
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> find(@AuthenticationPrincipal User user,
                                             @RequestHeader("Authorization") String authorization) throws UserNotFountException {
        return ResponseEntity.ok(
                UserResponse.of(userService.find(user), authorization.split(" ")[1])
        );
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponse> update(@AuthenticationPrincipal User user,
                                               @RequestBody @Valid UpdateProfileRequest profile,
                                               @RequestHeader("Authorization") String authorization) throws Exception {

        return ResponseEntity.ok(UserResponse.of(userService.updateProfile(user, profile.toUpdateProfileRequest()), authorization.split(" ")[1]));
    }
}
