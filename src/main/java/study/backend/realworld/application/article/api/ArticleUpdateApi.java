package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.backend.realworld.application.article.application.ArticleService;
import study.backend.realworld.application.article.dto.request.ArticleUpdateRequest;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleUpdateApi {
    private final ArticleService articleService;

    @PutMapping("/articles/{slug}")
    public ResponseEntity<ArticleResponse> updateArticleBySlug(@AuthenticationPrincipal User user,
                                                               @PathVariable String slug,
                                                               @RequestBody ArticleUpdateRequest request) throws UserNotFountException, AuthenticationException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleService.updateArticle(user, slug, request.toUpdateArticleModel()))
        );
    }
}
