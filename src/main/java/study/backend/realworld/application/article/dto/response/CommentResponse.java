package study.backend.realworld.application.article.dto.response;

import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.dto.response.ProfileResponse;

import java.time.LocalDateTime;

public class CommentResponse {
    private long id;
    private String body;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private ProfileResponse author;

    private CommentResponse(long id, String body, LocalDateTime createAt, LocalDateTime updateAt, ProfileResponse author) {
        this.id = id;
        this.body = body;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.author = author;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                ProfileResponse.of(comment.getAuthor().getProfile())
        );
    }
}
