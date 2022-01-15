package study.backend.realworld.application.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileEntity {
    @Embedded
    private UserName userName;

    @Column(name = "bio")
    private String bio;

    @Embedded
    private Image image;

    private boolean following;

    private ProfileEntity(UserName userName, String bio, Image image, boolean following) {
        this.userName = userName;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    public ProfileEntity(UserName userName) {
        this(userName, null, null, false);
    }

    public void changeUserName(UserName userName) {
        this.userName = userName;
    }

    public void changeBio(String bio) {
        this.bio = bio;
    }

    public void changeImage(Image image) {
        this.image = image;
    }
}
