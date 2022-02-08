package study.backend.realworld.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserNameTest {
    @DisplayName("같은 요소를 담은 UserName 객체는 같은 hashcode를 반환한다.")
    @Test
    void when_same_userName_equals_and_hashCode() {
        UserName userName = new UserName("user");
        UserName sameUserName = new UserName("user");

        assertThat(userName)
                .isEqualTo(sameUserName)
                .hasSameHashCodeAs(sameUserName);
    }

    @DisplayName("서로 다른 요소를 담은 UserName 객체는 다른 hashcode를 반환한다.")
    @Test
    void when_not_same_userName_equals_and_hashCode() {
        UserName userName = new UserName("user");
        UserName otherUserName = new UserName("otherUser");

        assertThat(userName)
                .isNotEqualTo(otherUserName)
                .doesNotHaveSameHashCodeAs(otherUserName);
    }
}