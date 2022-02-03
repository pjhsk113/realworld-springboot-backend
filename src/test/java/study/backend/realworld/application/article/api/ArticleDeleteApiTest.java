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

class ArticleDeleteApiTest extends IntegrationTestUtils {

    @Autowired
    private ArticleCommandExecutor articleCommandExecutor;

    @DisplayName("글 삭제 성공")
    @Test
    void delete_article() throws Exception {
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