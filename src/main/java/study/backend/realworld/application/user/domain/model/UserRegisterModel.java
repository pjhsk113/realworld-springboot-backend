package study.backend.realworld.application.user.domain.model;

import lombok.Getter;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.UserName;

@Getter
public class UserRegisterModel {
    private final UserName username;
    private final Email email;
    private final String password;

    public UserRegisterModel(UserName username, Email email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
