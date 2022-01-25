package study.backend.realworld.application.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.article.domain.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
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
