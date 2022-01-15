package study.backend.realworld.application.user.domain.model;

import lombok.Getter;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.UserName;

@Getter
public class UserRegisterModel {
    private final UserName userName;
    private final Email email;
    private final String password;

    public UserRegisterModel(UserName userName, Email email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
