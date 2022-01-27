package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.CommentService;
import study.backend.realworld.application.article.dto.request.CommentPostRequest;
import study.backend.realworld.application.article.dto.response.CommentResponse;
import study.backend.realworld.application.article.dto.response.MultipleCommentResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> postComment(@AuthenticationPrincipal User user,
                                                       @PathVariable String slug,
                                                       @RequestBody CommentPostRequest request) throws UserNotFountException {
        return ResponseEntity.ok(
                CommentResponse.from(commentService.createComment(user, slug, request.getBody()))
        );
    }

    @GetMapping("/articles/{slug}/comments")
    public ResponseEntity<MultipleCommentResponse> getComments(@AuthenticationPrincipal User user,
                                                               @PathVariable String slug) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleCommentResponse.from(commentService.findAllCommentsOnArticle(user, slug))
        );
    }

    @DeleteMapping("/articles/{slug}/comments/{id}")
    public void deleteComment(@AuthenticationPrincipal User user,
                              @PathVariable String slug,
                              @PathVariable long id) throws AuthenticationException, UserNotFountException {
        commentService.deleteCommentById(user, slug, id);
    }
}
