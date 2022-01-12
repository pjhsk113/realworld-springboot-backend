package study.backend.realworld.application.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password {

    @Column(name = "password", nullable = false)
    private String password;


}
