package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.ArticleService;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleDeleteApi {
    private final ArticleService articleService;

    @DeleteMapping("/articles/{slug}")
    public void deleteArticleBySlug(@AuthenticationPrincipal User user,
                                    @PathVariable String slug) throws UserNotFountException {
        articleService.deleteArticleBySlug(user, slug);
    }
}
