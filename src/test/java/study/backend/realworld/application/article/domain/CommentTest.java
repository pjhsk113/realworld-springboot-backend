package study.backend.realworld.application.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import study.backend.realworld.application.user.domain.User;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CommentTest {

    @DisplayName("같은 요소를 담은 Comment 객체는 같은 hashCode를 반환한다.")
    @Test
    void when_same_comment_equals_and_hashCode() {
        Article articleSample = mock(Article.class);
        User authorSample = mock(User.class);
        String bodySample = "bodySample";
        LocalDateTime createAtSample = now();

        Comment comment = new Comment(articleSample, authorSample, bodySample);
        Comment sameComment = new Comment(articleSample, authorSample, bodySample);
        ReflectionTestUtils.setField(comment, "createdAt", createAtSample);
        ReflectionTestUtils.setField(sameComment, "createdAt", createAtSample);

        assertThat(comment)
                .isEqualTo(sameComment)
                .hasSameHashCodeAs(sameComment);
    }
}