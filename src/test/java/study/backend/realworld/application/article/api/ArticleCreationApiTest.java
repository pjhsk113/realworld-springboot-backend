package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import study.backend.realworld.application.IntegrationTestUtils;
import study.backend.realworld.application.article.dto.request.ArticlePostRequest;

import java.util.Collections;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleCreationApiTest extends IntegrationTestUtils {

    @DisplayName("새로운 글 생성 요청 시 객체 필수 값이 누락된 경우 400 에러가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidArticleRequest")
    void when_post_article_with_invalid_body_will_return_badRequest(ArticlePostRequest invalidRequest) throws Exception {
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, setUpToken)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidArticleRequest() {
        return Stream.of(
                Arguments.of(new ArticlePostRequest(" ", "description", "body", Collections.emptySet())),
                Arguments.of(new ArticlePostRequest("title", " ", "body", Collections.emptySet())),
                Arguments.of(new ArticlePostRequest("title", "description", " ", Collections.emptySet())),
                Arguments.of(new ArticlePostRequest(null, "description", "body", Collections.emptySet())),
                Arguments.of(new ArticlePostRequest("title", null, "body", Collections.emptySet())),
                Arguments.of(new ArticlePostRequest("title", "description", null, Collections.emptySet())),
                Arguments.of(new ArticlePostRequest("title", "description", "body", null))
        );
    }
}