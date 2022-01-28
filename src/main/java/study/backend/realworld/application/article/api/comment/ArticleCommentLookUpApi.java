package study.backend.realworld.application.article.api.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.CommentService;
import study.backend.realworld.application.article.dto.response.MultipleCommentResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCommentLookUpApi {
    private final CommentService commentService;

    @GetMapping("/articles/{slug}/comments")
    public ResponseEntity<MultipleCommentResponse> getComments(@AuthenticationPrincipal User user,
                                                               @PathVariable String slug) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleCommentResponse.from(commentService.findAllCommentsOnArticle(user, slug))
        );
    }
}
