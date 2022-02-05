package study.backend.realworld.application.article.api.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.comment.CommentCommandExecutor;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.article.dto.request.CommentPostRequest;
import study.backend.realworld.application.article.dto.response.CommentResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCommentCreationApi {
    private final CommentCommandExecutor commentCommandExecutor;

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> postComment(@AuthenticationPrincipal User user,
                                                       @PathVariable String slug,
                                                       @Valid @RequestBody CommentPostRequest request) throws UserNotFountException {
        Comment comment = commentCommandExecutor.createComment(user, slug, request.getBody());
        return ResponseEntity.ok(
                CommentResponse.from(comment)
        );
    }
}
