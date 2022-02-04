package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.ResultMatcherUtils;
import study.backend.realworld.application.article.dto.request.ArticlePostRequest;

import java.util.Collections;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleCreationApiTest extends IntegrationTest {

    @DisplayName("새로운 글 생성 요청 성공")
    @Test
    void when_post_article_success() throws Exception {
        //given
        String contents = "{\n" +
                "        \"title\": \"How to train your dragon 2\",\n" +
                "        \"description\": \"So toothless\",\n" +
                "        \"body\": \"It a dragon\",\n" +
                "        \"tagList\": [\n" +
                "            \"training\"\n" +
                "        ]\n" +
                "}";

        //when
        ResultActions results = mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, setUpToken)
                        .content(contents))
                .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("how-to-train-your-dragon-2"))
                .andExpect(jsonPath("$.title").value("How to train your dragon 2"))
                .andExpect(jsonPath("$.description").value("So toothless"))
                .andExpect(jsonPath("$.body").value("It a dragon"))
                .andExpect(jsonPath("$.tagList").isArray())
                .andExpect(jsonPath("$.favorited").value(false))
                .andExpect(jsonPath("$.favoritesCount").value(0))
                .andExpect(ResultMatcherUtils.validProfileWithPath("author"));
    }

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