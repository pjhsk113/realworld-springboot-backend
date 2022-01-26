package study.backend.realworld.application.article.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentPostRequest {
    @NotBlank
    private String body;
}
