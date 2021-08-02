package study.backend.realworld.application.user.domain.model;

import lombok.Getter;

@Getter
public class UserRegisterModel {
    private final String username;
    private final String email;
    private final String password;

    public UserRegisterModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
