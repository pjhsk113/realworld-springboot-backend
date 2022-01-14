package study.backend.realworld.application.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Email {
    @Column(name = "email", nullable = false)
    private String email;

    public Email(String email) {
        this.email = email;
    }

}
