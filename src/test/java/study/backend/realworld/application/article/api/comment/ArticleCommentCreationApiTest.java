package study.backend.realworld.application.article.api.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.ResultMatcherUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleCommentCreationApiTest extends IntegrationTest {

    @DisplayName("댓글 등록 성공")
    @Test
    void when_post_comment_success() throws Exception {
        //given
        String comment = "{\"body\":\"It takes a Jacobian\"}";

        //when
        ResultActions results = mockMvc.perform(post("/api/articles/{slug}/comments", "how-to-train-your-dragon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, setUpToken)
                        .content(comment))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(ResultMatcherUtils.validSingleComment());
    }
}