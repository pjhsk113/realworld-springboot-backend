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

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> following(@AuthenticationPrincipal User user, @PathVariable UserName username) throws ExistsUserException, UserNotFountException {
        Profile profile = followService.follow(user, username);

        return ResponseEntity.ok(ProfileResponse.of(profile));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> unFollow(@AuthenticationPrincipal User user, @PathVariable UserName username) throws UserNotFountException {
        Profile profile = followService.unfollow(user, username);
        return ResponseEntity.ok(ProfileResponse.of(profile));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> find(@AuthenticationPrincipal User user, @PathVariable UserName username) {
        return ResponseEntity.ok(null);
    }
}
