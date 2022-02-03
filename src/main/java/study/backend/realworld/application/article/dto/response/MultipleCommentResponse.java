package study.backend.realworld.application.article.dto.response;

import lombok.Getter;
import study.backend.realworld.application.article.domain.Comment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MultipleCommentResponse {
    private List<CommentResponse> comments;

    private MultipleCommentResponse(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public static MultipleCommentResponse from(Set<Comment> comments) {
        List<CommentResponse> convertComments = comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
        return new MultipleCommentResponse(convertComments);
    }
}
