package study.backend.realworld.application.article.api.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import study.backend.realworld.application.IntegrationTest;
import study.backend.realworld.application.article.application.comment.CommentCommandExecutor;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.exception.UserNotFountException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleCommentDeleteApiTest extends IntegrationTest {
    @Autowired
    private CommentCommandExecutor commentCommandExecutor;

    private long commentId;

    @BeforeEach
    void setUpComment() throws UserNotFountException {
        Comment comment = commentCommandExecutor.createComment(setUpUser, "how-to-train-your-dragon", "It takes a Jacobian");
        commentId = comment.getId();
    }

    @DisplayName("댓글 삭제 성공")
    @Test
    void delete_comment_success() throws Exception {
        // when
        ResultActions results = mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", "how-to-train-your-dragon", commentId)
                        .header(AUTHORIZATION, setUpToken))
                .andDo(print());

        //then
        results.andExpect(status().isOk());
    }
}