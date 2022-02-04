package study.backend.realworld.application.article.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleUpdateApiTest extends IntegrationTest {

    @DisplayName("글 업데이트 성공")
    @Test
    void update_article() throws Exception {
        //when
        ResultActions results = mockMvc.perform(put("/api/articles/{slug}", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"body\":\"With two hands\"}"))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("With two hands"));
    }
}