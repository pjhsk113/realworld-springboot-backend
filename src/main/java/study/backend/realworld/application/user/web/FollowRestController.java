package study.backend.realworld.application.user.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.web.response.ProfileResponse;

@RestController
@RequestMapping("/api/profiles")
public class FollowRestController {

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> following(@AuthenticationPrincipal User user,
                                                     @PathVariable String userName) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> unFollow(@AuthenticationPrincipal User user,
                                                    @PathVariable String userName) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> find(@AuthenticationPrincipal User user,
                                                @PathVariable String userName) {
        return ResponseEntity.ok(null);
    }
}
