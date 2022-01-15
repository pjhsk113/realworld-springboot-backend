package study.backend.realworld.application.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.user.application.FollowService;
import study.backend.realworld.application.user.domain.Profile;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.dto.response.ProfileResponse;
import study.backend.realworld.application.user.exception.ExistsUserException;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class FollowRestController {

    private final FollowService followService;

    @PostMapping("/{userName}/follow")
    public ResponseEntity<ProfileResponse> following(@AuthenticationPrincipal User user, @PathVariable UserName userName) throws ExistsUserException, UserNotFountException {
        Profile profile = followService.follow(user, userName);

        return ResponseEntity.ok(ProfileResponse.of(profile));
    }

    @DeleteMapping("/{userName}/follow")
    public ResponseEntity<ProfileResponse> unFollow(@AuthenticationPrincipal User user, @PathVariable UserName userName) throws UserNotFountException {
        Profile profile = followService.unfollow(user, userName);
        return ResponseEntity.ok(ProfileResponse.of(profile));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ProfileResponse> find(@AuthenticationPrincipal User user, @PathVariable UserName userName) {
        return ResponseEntity.ok(null);
    }
}
