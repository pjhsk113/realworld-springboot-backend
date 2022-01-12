package study.backend.realworld.application.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserName {
    @Column(name = "name", nullable = false)
    private String name;
}
