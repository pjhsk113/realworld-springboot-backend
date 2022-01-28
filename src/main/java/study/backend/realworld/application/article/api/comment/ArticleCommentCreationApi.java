package study.backend.realworld.application.article.api.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.CommentService;
import study.backend.realworld.application.article.dto.request.CommentPostRequest;
import study.backend.realworld.application.article.dto.response.CommentResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCommentCreationApi {
    private final CommentService commentService;

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> postComment(@AuthenticationPrincipal User user,
                                                       @PathVariable String slug,
                                                       @RequestBody CommentPostRequest request) throws UserNotFountException {
        return ResponseEntity.ok(
                CommentResponse.from(commentService.createComment(user, slug, request.getBody()))
        );
    }
}
