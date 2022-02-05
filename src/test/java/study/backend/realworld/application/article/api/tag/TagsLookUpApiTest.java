package study.backend.realworld.application.article.api.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.backend.realworld.application.IntegrationTest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagsLookUpApiTest extends IntegrationTest {

    @DisplayName("모든 태그 조회 성공")
    @Test
    void get_all_tags() throws Exception {
        //when & then
        mockMvc.perform(get("/api/tags")
                .header(AUTHORIZATION, setUpToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags").value(containsInAnyOrder("dragons", "training")));

    }

}