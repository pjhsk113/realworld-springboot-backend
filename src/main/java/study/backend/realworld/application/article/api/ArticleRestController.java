package study.backend.realworld.application.article.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.ArticleService;
import study.backend.realworld.application.article.dto.request.PostArticleRequest;
import study.backend.realworld.application.article.dto.response.PostArticleResponse;
import study.backend.realworld.application.user.domain.User;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleRestController {

    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<PostArticleResponse> postArticle(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody PostArticleRequest request) {

        return ResponseEntity.ok(
                PostArticleResponse.of(articleService.createArticle(user, request.toArticleContents()))
        );
    }
}
