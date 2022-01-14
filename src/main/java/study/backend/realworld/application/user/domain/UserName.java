package study.backend.realworld.application.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserName {
    @Column(name = "name", nullable = false)
    private String name;

    public UserName(String name) {
        this.name = name;
    }
}
