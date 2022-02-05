package study.backend.realworld.application.article.api.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.ResultMatcherUtils;
import study.backend.realworld.application.article.application.comment.CommentCommandExecutor;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.exception.UserNotFountException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleCommentLookUpApiTest extends IntegrationTest {

    @Autowired
    private CommentCommandExecutor commentCommandExecutor;

    @BeforeEach
    void setUpComment() throws UserNotFountException {
        Comment comment = commentCommandExecutor.createComment(setUpUser, "how-to-train-your-dragon", "It takes a Jacobian");
    }

    @DisplayName("모든 댓글 조회 성공")
    @Test
    void get_all_comment() throws Exception {
        // when
        ResultActions results = mockMvc.perform(get("/api/articles/{slug}/comments", "how-to-train-your-dragon")
                        .header(AUTHORIZATION, setUpToken))
                        .andDo(print());

        //then
        results.andExpect(status().isOk())
                .andExpect(ResultMatcherUtils.validMultipleComment("comments[0]"));
    }
}