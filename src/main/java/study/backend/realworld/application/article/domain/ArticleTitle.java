package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static ArticleTitle of(String title) {
        return new ArticleTitle(title, titleToSlug(title));
    }

    private static String titleToSlug(String title) {
        return String.join("-", title.toLowerCase().split(" "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleTitle that = (ArticleTitle) o;
        return Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}
