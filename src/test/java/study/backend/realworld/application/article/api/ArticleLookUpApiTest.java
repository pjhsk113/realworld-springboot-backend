package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.Test;
import study.backend.realworld.application.IntegrationTestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleLookUpApiTest extends IntegrationTestUtils {

    @Test
    void find_all_article_success() throws Exception {
        mockMvc.perform(get("/api/articles?limit=20&offset=0")
                .header(AUTHORIZATION, setUpToken))
                .andExpect(status().isOk());
    }

}