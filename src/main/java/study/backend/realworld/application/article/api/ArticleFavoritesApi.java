package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.ArticleCommandExecutor;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleFavoritesApi {
    private final ArticleCommandExecutor articleCommandExecutor;

    @PostMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> favoriteArticleBySlug(@AuthenticationPrincipal User user,
                                                                 @PathVariable String slug) throws UserNotFountException {

        return ResponseEntity.ok(
                ArticleResponse.from(articleCommandExecutor.favoriteArticle(user, slug))
        );
    }

    @DeleteMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> unfavoriteArticleBySlug(@AuthenticationPrincipal User user,
                                                                   @PathVariable String slug) throws UserNotFountException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleCommandExecutor.unfavoriteArticle(user, slug))
        );
    }
}
