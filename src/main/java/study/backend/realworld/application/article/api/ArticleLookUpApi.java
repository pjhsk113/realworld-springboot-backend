package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.ArticleFindCommandExecutor;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.article.dto.response.MultipleArticlesResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleLookUpApi {
    private final ArticleFindCommandExecutor articleFindCommandExecutor;

    @GetMapping("/articles")
    public ResponseEntity<MultipleArticlesResponse> getArticles(Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleFindCommandExecutor.findAllArticles(pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "tag" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByTag(@RequestParam String tag, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleFindCommandExecutor.findArticleByTag(tag, pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "author" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByAuthor(@RequestParam String author, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleFindCommandExecutor.findArticleByAuthor(author, pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "favorited" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByFavoritedUser(@RequestParam UserName userName, Pageable pageable) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleFindCommandExecutor.findArticleFavoritedByUserName(userName, pageable))
        );
    }

    @GetMapping("/articles/feed")
    public ResponseEntity<MultipleArticlesResponse> getArticlesFeed(@AuthenticationPrincipal User user, Pageable pageable) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleFindCommandExecutor.findArticleFeedByUserName(user, pageable))
        );
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(
                ArticleResponse.from(articleFindCommandExecutor.findArticleBySlug(slug))
        );
    }
}
