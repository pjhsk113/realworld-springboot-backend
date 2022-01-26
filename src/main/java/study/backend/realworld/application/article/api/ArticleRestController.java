package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.ArticleService;
import study.backend.realworld.application.article.dto.request.ArticlePostRequest;
import study.backend.realworld.application.article.dto.request.ArticleUpdateRequest;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.article.dto.response.MultipleArticlesResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleRestController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> postArticle(@AuthenticationPrincipal User user,
                                                       @Valid @RequestBody ArticlePostRequest request) throws UserNotFountException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleService.createArticle(user, request.toArticleContents()))
        );
    }

    @GetMapping("/articles")
    public ResponseEntity<MultipleArticlesResponse> getArticles(Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findAllArticles(pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "tag" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByTag(@RequestParam String tag, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleByTag(tag, pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "author" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByAuthor(@RequestParam String author, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleByAuthor(author, pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "favorited" })
    public ResponseEntity<MultipleArticlesResponse> getArticlesByFavoritedUser(@RequestParam UserName userName, Pageable pageable) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleFavoritedByUserName(userName, pageable))
        );
    }

    @GetMapping("/articles/feed")
    public ResponseEntity<MultipleArticlesResponse> getArticlesFeed(@AuthenticationPrincipal User user, Pageable pageable) throws UserNotFountException {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleFeedByUserName(user, pageable))
        );
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(
                ArticleResponse.from(articleService.findArticleBySlug(slug))
        );
    }

    @PutMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> updateArticleBySlug(@AuthenticationPrincipal User user,
                                                               @PathVariable String slug,
                                                               @RequestBody ArticleUpdateRequest request) throws UserNotFountException, AuthenticationException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleService.updateArticle(user, slug, request.toUpdateArticleModel()))
        );
    }

    @DeleteMapping("/articles/{slug}")
    public void deleteArticleBySlug(@AuthenticationPrincipal User user,
                                                    @PathVariable String slug) throws UserNotFountException {
        articleService.deleteArticleBySlug(user, slug);
    }

    @PostMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> favoriteArticleBySlug(@AuthenticationPrincipal User user,
                                                                 @PathVariable String slug) throws UserNotFountException {

        return ResponseEntity.ok(
                ArticleResponse.from(articleService.favoriteArticle(user, slug))
        );
    }

    @DeleteMapping("/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> unfavoriteArticleBySlug(@AuthenticationPrincipal User user,
                                                                   @PathVariable String slug) throws UserNotFountException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleService.unfavoriteArticle(user, slug))
        );
    }
}
