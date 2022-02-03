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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleFavoritesApiTest extends IntegrationTestUtils {

    @Autowired
    private ArticleCommandExecutor articleCommandExecutor;

    @DisplayName("글 favorite 성공")
    @Test
    void update_favorite_article() throws Exception {
        articleCommandExecutor.createArticle(
                setUpUser,
                new ArticleContents(
                        ArticleTitle.of("How to train your dragon"),
                        "Ever wonder how?",
                        "Very carefully.",
                        Set.of(new Tag("dragons"))
                )
        );
        mockMvc.perform(post("/api/articles/{slug}/favorite", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

    @DisplayName("글 unFavorite 성공")
    @Test
    void update_unfavorite_article() throws Exception {
        articleCommandExecutor.createArticle(
                setUpUser,
                new ArticleContents(
                        ArticleTitle.of("How to train your dragon"),
                        "Ever wonder how?",
                        "Very carefully.",
                        Set.of(new Tag("dragons"))
                )
        );
        mockMvc.perform(delete("/api/articles/{slug}/favorite", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }
}