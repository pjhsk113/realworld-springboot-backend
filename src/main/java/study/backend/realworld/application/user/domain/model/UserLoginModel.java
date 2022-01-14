package study.backend.realworld.application.user.domain.model;

import lombok.Getter;
import study.backend.realworld.application.user.domain.Email;

@Getter
public class UserLoginModel {
    private final Email email;
    private final String password;

    public UserLoginModel(Email email, String password) {
        this.email = email;
        this.password = password;
    }
}
