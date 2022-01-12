package study.backend.realworld.application.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {
    @Column(name = "image")
    private String image;

}
