package study.backend.realworld.application.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagTest {

    @DisplayName("같은 요소를 담은 Tag 객체는 같은 hashCode를 반환한다.")
    @Test
    void when_same_tag_object_equals_and_hashCode() {
        Tag tag = new Tag("tag-value");
        Tag sameTag = new Tag("tag-value");

        assertThat(tag)
                .isEqualTo(sameTag)
                .hasSameHashCodeAs(sameTag);
    }

    @DisplayName("서로 다른 요소를 담은 Tag 객체는 다른 hashCode를 반환한다.")
    @Test
    void when_not_same_tag_object_equals_and_hashCode() {
        Tag tag = new Tag("tag-value");
        Tag otherTag = new Tag("other-tag-value");

        assertThat(tag)
                .isNotEqualTo(otherTag)
                .doesNotHaveSameHashCodeAs(otherTag);
    }
}