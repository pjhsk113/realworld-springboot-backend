package study.backend.realworld.application.user.domain.model;

import lombok.Getter;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.Image;
import study.backend.realworld.application.user.domain.UserName;

@Getter
public class UpdateProfileModel {
    private UserName username;
    private Email email;
    private String password;
    private String bio;
    private Image image;

    public UpdateProfileModel(UserName username, Email email, String password, String bio, Image image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }
}
