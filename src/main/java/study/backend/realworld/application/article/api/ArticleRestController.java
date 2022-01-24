package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.ArticleService;
import study.backend.realworld.application.article.dto.request.PostArticleRequest;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.article.dto.response.MultipleArticlesResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleRestController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> postArticle(@AuthenticationPrincipal User user,
                                                       @Valid @RequestBody PostArticleRequest request) throws UserNotFountException {

        return ResponseEntity.ok(
                ArticleResponse.of(articleService.createArticle(user, request.toArticleContents()))
        );
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticles(Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findAllArticles(pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "tag" })
    public ResponseEntity<?> getArticlesByTag(@RequestParam String tag, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleByTag(tag, pageable))
        );
    }

    @GetMapping(value = "/articles", params = { "author" })
    public ResponseEntity<?> getArticlesByAuthor(@RequestParam String author, Pageable pageable) {
        return ResponseEntity.ok(
                MultipleArticlesResponse.from(articleService.findArticleByAuthor(author, pageable))
        );
    }
}
