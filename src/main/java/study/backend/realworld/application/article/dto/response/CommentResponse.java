package study.backend.realworld.application.article.dto.response;

import lombok.Getter;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.dto.response.ProfileResponse;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProfileResponse author;

    private CommentResponse(long id, String body, LocalDateTime createdAt, LocalDateTime updatedAt, ProfileResponse author) {
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
