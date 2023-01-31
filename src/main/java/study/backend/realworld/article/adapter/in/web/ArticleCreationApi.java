package study.backend.realworld.article.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.backend.realworld.application.article.application.ArticleCommandExecutor;
import study.backend.realworld.application.article.dto.request.ArticlePostRequest;
import study.backend.realworld.application.article.dto.response.ArticleResponse;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleCreationApi {
    private final ArticleCommandExecutor articleCommandExecutor;

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> postArticle(@AuthenticationPrincipal User user,
                                                       @Valid @RequestBody ArticlePostRequest request) throws UserNotFountException {
        return ResponseEntity.ok(
                ArticleResponse.from(articleCommandExecutor.createArticle(user, request.toArticleContents()))
        );
    }
}
