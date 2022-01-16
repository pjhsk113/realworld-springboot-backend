package study.backend.realworld.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @DisplayName("Email 동치 테스트")
    @Test
    void when_same_address_euqals_test() {
        final Email email = new Email("user@email.com");
        final Email sameEmail = new Email("user@email.com");

        assertThat(email)
                .isEqualTo(sameEmail)
                .hasSameHashCodeAs(sameEmail);
    }
}