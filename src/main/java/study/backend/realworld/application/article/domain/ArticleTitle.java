package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class ArticleTitle {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    private ArticleTitle(String title, String slug) {
        this.title = title;
        this.slug = slug;
    }

    public static ArticleTitle of(String title) {
        return new ArticleTitle(title, titleToSlug(title));
    }

    private static String titleToSlug(String title) {
        return String.join("-", title.toLowerCase().split(" "));
    }
}
