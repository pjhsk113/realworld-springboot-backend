package study.backend.realworld.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {
    @DisplayName("같은 요소를 담은 Image 객체는 같은 hashcode를 반환한다.")
    @Test
    void when_same_image_equals_and_hashCode() {
        Image imageAddress = new Image("image-address");
        Image sameImageAddress = new Image("image-address");

        assertThat(imageAddress)
                .isEqualTo(sameImageAddress)
                .hasSameHashCodeAs(sameImageAddress);
    }

    @DisplayName("서로 다른 요소를 담은 Image 객체는 다른 hashcode를 반환한다.")
    @Test
    void when_not_same_image_equals_and_hashCode() {
        Image imageAddress = new Image("image-address");
        Image otherImageAddress = new Image("other-image-address");

        assertThat(imageAddress)
                .isNotEqualTo(otherImageAddress)
                .doesNotHaveSameHashCodeAs(otherImageAddress);
    }
}