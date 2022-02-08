package study.backend.realworld.application.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PasswordTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void when_enter_rawPassword_expect_print_encode_password() {
        String rawPassword = "password";
        String encodePassword = "encode-password";
        given(passwordEncoder.encode(rawPassword)).willReturn(encodePassword);

        Password password = Password.of(rawPassword, passwordEncoder);

        assertThat(password.getPassword()).isEqualTo(encodePassword);
    }
}