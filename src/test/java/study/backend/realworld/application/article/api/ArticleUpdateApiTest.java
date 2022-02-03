package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import study.backend.realworld.application.IntegrationTestUtils;
import study.backend.realworld.application.article.application.ArticleCommandExecutor;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.article.domain.Tag;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleUpdateApiTest extends IntegrationTestUtils {
    @Autowired
    private ArticleCommandExecutor articleCommandExecutor;

    @DisplayName("글 업데이트 성공")
    @Test
    void update_article() throws Exception {
        articleCommandExecutor.createArticle(
                setUpUser,
                new ArticleContents(
                        ArticleTitle.of("How to train your dragon"),
                        "Ever wonder how?",
                        "Very carefully.",
                        Set.of(new Tag("dragons"))
                )
        );
        mockMvc.perform(put("/api/articles/{slug}", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"With two hands\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("With two hands"));
    }
}