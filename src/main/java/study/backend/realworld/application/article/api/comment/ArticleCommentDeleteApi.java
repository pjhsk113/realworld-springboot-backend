package study.backend.realworld.application.article.api.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.CommentService;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCommentDeleteApi {
    private final CommentService commentService;

    @DeleteMapping("/articles/{slug}/comments/{id}")
    public void deleteComment(@AuthenticationPrincipal User user,
                              @PathVariable String slug,
                              @PathVariable long id) throws AuthenticationException, UserNotFountException {
        commentService.deleteCommentById(user, slug, id);
    }
}
