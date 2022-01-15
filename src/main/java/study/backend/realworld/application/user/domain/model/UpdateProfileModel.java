package study.backend.realworld.application.user.domain.model;

import lombok.Getter;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.Image;
import study.backend.realworld.application.user.domain.UserName;

@Getter
public class UpdateProfileModel {
    private UserName userName;
    private Email email;
    private String password;
    private String bio;
    private Image image;

    public UpdateProfileModel(UserName userName, Email email, String password, String bio, Image image) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }
}
