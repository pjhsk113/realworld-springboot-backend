package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.ResultMatcherUtils;
import study.backend.realworld.application.article.application.ArticleCommandExecutor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleLookUpApiTest extends IntegrationTest {

    @Autowired
    private ArticleCommandExecutor articleCommandExecutor;

    @DisplayName("전체 글 조회 성공")
    @Test
    void find_all_article() throws Exception {
        // when
        ResultActions results = mockMvc.perform(get("/api/articles?limit=20&offset=0")
                .header(AUTHORIZATION, setUpToken))
                .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articlesCount").value(1))
                .andExpect(ResultMatcherUtils.validMultipleArticleResponse("articles[0]"));
    }

    @DisplayName("태그를 기준으로 전체 글 조회 성공")
    @Test
    void find_all_article_by_tag() throws Exception {
        // when
        ResultActions results = mockMvc.perform(get("/api/articles")
                        .param("tag", "dragons")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articlesCount").value(1))
                .andExpect(ResultMatcherUtils.validMultipleArticleResponse("articles[0]"));
    }

    @DisplayName("작성자의 전체 글 조회 성공")
    @Test
    void find_all_article_by_author() throws Exception {
        // when
        ResultActions results = mockMvc.perform(get("/api/articles")
                        .param("author", "user")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articlesCount").value(1))
                .andExpect(ResultMatcherUtils.validMultipleArticleResponse("articles[0]"));
    }

    @DisplayName("좋아요 누른 유저 정보로 전체 글 조회 성공")
    @Test
    void find_all_article_by_favorited_user() throws Exception {
        //given
        articleCommandExecutor.favoriteArticle(setUpUser, "how-to-train-your-dragon");

        // when
        ResultActions results =  mockMvc.perform(get("/api/articles")
                        .param("favorited", "user")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articlesCount").value(1))
                .andExpect(ResultMatcherUtils.validMultipleArticleResponse("articles[0]"))
                .andExpect(jsonPath("$.articles[0].favorited").value(true))
                .andExpect(jsonPath("$.articles[0].favoritesCount").value(1));
    }

    @DisplayName("전체 글 feed 조회 성공")
    @Test
    void find_all_article_feed() throws Exception {
        //given
        articleCommandExecutor.favoriteArticle(setUpUser, "how-to-train-your-dragon");

        // when
        ResultActions results =  mockMvc.perform(get("/api/articles/feed")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articlesCount").value(1))
                .andExpect(ResultMatcherUtils.validMultipleArticleResponse("articles[0]"))
                .andExpect(jsonPath("$.articles[0].favorited").value(true))
                .andExpect(jsonPath("$.articles[0].favoritesCount").value(1));
    }

    @DisplayName("slug로 글 조회 성공")
    @Test
    void find_one_article_by_slug() throws Exception {
        // when
        ResultActions results =  mockMvc.perform(get("/api/articles/{slug}", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(ResultMatcherUtils.validSingleArticleResponse());
    }
}