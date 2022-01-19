package study.backend.realworld.application.article.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ArticleTitle {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    private ArticleTitle(String title, String slug) {
        this.title = title;
        this.slug = slug;
    }

    protected ArticleTitle() { }

    public static ArticleTitle of(String title) {
        return new ArticleTitle(title, titleToSlug(title));
    }

    private static String titleToSlug(String title) {
        return String.join("-", title.toLowerCase().split(" "));
    }
}
