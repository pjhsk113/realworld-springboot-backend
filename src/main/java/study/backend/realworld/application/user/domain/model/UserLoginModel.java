package study.backend.realworld.application.user.domain.model;

import lombok.Getter;

@Getter
public class UserLoginModel {
    private final String email;
    private final String password;

    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
