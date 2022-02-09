package study.backend.realworld.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("user 정보 생성 시 email은 null일 수 없다.")
    @Test
    void when_create_user_email_can_not_be_null() {
        Email email = null;
        UserName userName = new UserName("user");
        Password password = Password.of("password", passwordEncoder);

        assertThatThrownBy(() -> {
            User.of(email, userName, password);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email has null");
    }

    @DisplayName("user 정보 생성 시 이름은 null일 수 없다.")
    @Test
    void when_create_user_username_can_not_be_null() {
        Email email = new Email("user@email.com");
        UserName userName = null;
        Password password = Password.of("password", passwordEncoder);

        assertThatThrownBy(() -> {
            User.of(email, userName, password);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("username has null");
    }

    @DisplayName("user 정보 생성 시 비밀번호는 null일 수 없다.")
    @Test
    void when_create_user_password_can_not_be_null() {
        Email email = new Email("user@email.com");
        UserName userName = new UserName("user");
        Password password = null;

        assertThatThrownBy(() -> {
            User.of(email, userName, password);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("password has null");
    }

    @DisplayName("user 정보 생성 성공")
    @Test
    void when_create_user_success() {
        User user = createMockUser();

        assertThat(user.getEmail()).isEqualTo(new Email("user@email.com"));
        assertThat(user.getName()).isEqualTo(new UserName("user"));
        assertThat(user.getPassword()).isEqualTo(Password.of("password", passwordEncoder));
        assertThat(user.getImage()).isNull();
        assertThat(user.getBio()).isNull();
    }

    @DisplayName("같은 요소를 담은 User 객체는 같은 hashcode를 반환한다.")
    @Test
    void when_same_user_equals_and_hashCode() {
        User user = User.of(new Email("user@email.com"), new UserName("user"), Password.of("password", passwordEncoder));
        User sameUser = User.of(new Email("user@email.com"), new UserName("user"), Password.of("password", passwordEncoder));

        assertThat(user)
                .isEqualTo(sameUser)
                .hasSameHashCodeAs(sameUser);
    }

    @DisplayName("서로 다른 요소를 담은 User 객체는 다른 hashcode를 반환한다.")
    @Test
    void when_not_same_user_equals_and_hashCode() {
        User user = User.of(new Email("user@email.com"), new UserName("user"), Password.of("password", passwordEncoder));
        User otherUser = User.of(new Email("otherUser@email.com"), new UserName("otherUser"), Password.of("other-password", passwordEncoder));

        assertThat(user)
                .isNotEqualTo(otherUser)
                .doesNotHaveSameHashCodeAs(otherUser);
    }

    @DisplayName("user email 업데이트 성공")
    @Test
    void when_change_email_success() {
        User user = createMockUser();

        Email changedEmail = new Email("change@email.com");
        user.changeEmail(changedEmail);

        assertThat(user.getEmail()).isEqualTo(changedEmail);
    }

    @DisplayName("user 이름 업데이트 성공")
    @Test
    void when_change_userName_success() {
        User user = createMockUser();

        UserName changedName = new UserName("changeUser");
        user.changeName(changedName);

        assertThat(user.getName()).isEqualTo(changedName);
    }

    @DisplayName("user 비밀번호 업데이트 성공")
    @Test
    void when_change_password_success(@Mock Password changedPassword) {
        User user = createMockUser();

        user.changePassword(changedPassword);
        user.matchesPassword("changedPassword", passwordEncoder);

        assertThat(user.getPassword()).isEqualTo(changedPassword);
        verify(changedPassword, times(1)).matchesPassword("changedPassword", passwordEncoder);
    }

    private User createMockUser() {
        Email email = new Email("user@email.com");
        UserName userName = new UserName("user");
        Password password = Password.of("password", passwordEncoder);
        return User.of(email, userName, password);
    }
}