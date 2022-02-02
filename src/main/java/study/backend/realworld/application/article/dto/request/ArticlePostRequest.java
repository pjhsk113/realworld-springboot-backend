package study.backend.realworld.application.article.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.article.domain.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonRootName("article")
@AllArgsConstructor
@Getter
public class ArticlePostRequest {
    @NotBlank
    String title;

    @NotBlank
    String description;

    @NotBlank
    String body;

    @NotNull
    Set<Tag> tagList;

    public ArticleContents toArticleContents() {
        return new ArticleContents(
                ArticleTitle.of(title),
                description,
                body,
                tagList
        );
    }
}
