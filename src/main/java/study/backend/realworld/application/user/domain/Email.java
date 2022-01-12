package study.backend.realworld.application.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {
    @Column(name = "email", nullable = false)
    private String email;
}
