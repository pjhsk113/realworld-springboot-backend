package study.backend.realworld.application.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Column(name = "image")
    private String image;

    public Image(String image) {
        this.image = image;
    }
}
