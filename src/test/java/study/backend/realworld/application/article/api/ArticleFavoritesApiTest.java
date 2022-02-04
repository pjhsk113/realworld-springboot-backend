package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.ResultMatcherUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleFavoritesApiTest extends IntegrationTest {

    @DisplayName("글 favorite 성공")
    @Test
    void update_favorite_article() throws Exception {
        //when
        ResultActions results = mockMvc.perform(post("/api/articles/{slug}/favorite", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(ResultMatcherUtils.validSingleArticleResponse())
                .andExpect(jsonPath("$..favorited").value(true))
                .andExpect(jsonPath("$.favoritesCount").value(1));
    }

    @DisplayName("글 unFavorite 성공")
    @Test
    void update_unfavorite_article() throws Exception {
        //when
        ResultActions results = mockMvc.perform(delete("/api/articles/{slug}/favorite", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(ResultMatcherUtils.validSingleArticleResponse())
                .andExpect(jsonPath("$.favorited").value(false))
                .andExpect(jsonPath("$.favoritesCount").value(0));
    }
}