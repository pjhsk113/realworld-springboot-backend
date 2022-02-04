package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.backend.realworld.application.IntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleDeleteApiTest extends IntegrationTest {

    @DisplayName("글 삭제 성공")
    @Test
    void delete_article() throws Exception {
        mockMvc.perform(get("/api/articles/{slug}", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }
}