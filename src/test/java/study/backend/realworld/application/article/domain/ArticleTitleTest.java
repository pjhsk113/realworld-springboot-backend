package study.backend.realworld.application.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTitleTest {

    @DisplayName("title을 입력하면 slug가 자동으로 생성된다.")
    @Test
    void create_slug_with_title() {
        String title = "test title";
        String expectSlug = "test-title";

        ArticleTitle articleTitle = ArticleTitle.of(title);

        assertThat(articleTitle.getSlug()).isEqualTo(expectSlug);
    }

    @DisplayName("같은 요소를 담은 ArticleTitle 객체는 같은 hashcode를 반환한다.")
    @Test
    void when_same_articleTitle_equals_and_hashCode() {
        ArticleTitle articleTitle1 = ArticleTitle.of("test title");
        ArticleTitle articleTitle2 = ArticleTitle.of("test title");

        assertThat(articleTitle1)
                .isEqualTo(articleTitle2)
                .hasSameHashCodeAs(articleTitle2);
    }

    @DisplayName("서로 다른 요소를 담은 ArticleTitle 객체는 다른 hashcode를 반환한다.")
    @Test
    void when_not_same_articleTitle_equals_and_hashCode() {
        ArticleTitle articleTitle = ArticleTitle.of("test title");
        ArticleTitle otherArticleTitle = ArticleTitle.of("other title");

        assertThat(articleTitle)
                .isNotEqualTo(otherArticleTitle)
                .doesNotHaveSameHashCodeAs(otherArticleTitle);
    }
}