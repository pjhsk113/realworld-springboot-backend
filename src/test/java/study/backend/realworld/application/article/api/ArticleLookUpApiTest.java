package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.backend.realworld.application.IntegrationTestUtils;
import study.backend.realworld.application.article.application.ArticleCommandExecutor;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.article.domain.Tag;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleLookUpApiTest extends IntegrationTestUtils {

    @Autowired
    private ArticleCommandExecutor articleCommandExecutor;

    @DisplayName("전체 글 조회 성공")
    @Test
    void find_all_article() throws Exception {
        mockMvc.perform(get("/api/articles?limit=20&offset=0")
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("태그를 기준으로 전체 글 조회 성공")
    @Test
    void find_all_article_by_tag() throws Exception {
        mockMvc.perform(get("/api/articles")
                        .param("tag", "dragons")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("작성자의 전체 글 조회 성공")
    @Test
    void find_all_article_by_author() throws Exception {
        mockMvc.perform(get("/api/articles")
                        .param("author", "user")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("좋아요 누른 유저 정보로 전체 글 조회 성공")
    @Test
    void find_all_article_by_favorited_user() throws Exception {
        mockMvc.perform(get("/api/articles")
                        .param("favorited", "user")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("전체 글 feed 조회 성공")
    @Test
    void find_all_article_feed() throws Exception {
        mockMvc.perform(get("/api/articles/feed")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("slug로 글 조회 성공")
    @Test
    void find_one_article_by_slug() throws Exception {
        articleCommandExecutor.createArticle(
                setUpUser,
                new ArticleContents(
                        ArticleTitle.of("How to train your dragon"),
                        "Ever wonder how?",
                        "Very carefully.",
                        Set.of(new Tag("dragons"))
                )
        );
        mockMvc.perform(get("/api/articles/{slug}", "how-to-train-your-dragon")
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }
}