package study.backend.realworld.application.article.domain.model;

import lombok.Getter;
import study.backend.realworld.application.article.domain.ArticleTitle;

import java.util.Optional;

@Getter
public class ArticleUpdateModel {
    private final ArticleTitle title;
    private final String description;
    private final String body;

    private ArticleUpdateModel(ArticleTitle title, String description, String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public static ArticleUpdateModel of(String title, String description, String body) {
        return new ArticleUpdateModel(
                Optional.ofNullable(title).map(ArticleTitle::of).orElse(null),
                Optional.ofNullable(description).orElse(null),
                Optional.ofNullable(body).orElse(null)
        );

    }
}
