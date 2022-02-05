package study.backend.realworld.application.article.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@JsonTypeName("comment")
@Getter
public class CommentPostRequest {
    @NotBlank
    private String body;
}
