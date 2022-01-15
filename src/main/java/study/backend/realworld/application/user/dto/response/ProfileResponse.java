package study.backend.realworld.application.user.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import study.backend.realworld.application.user.domain.Profile;

import static java.lang.String.valueOf;

@Getter
@JsonRootName("profile")
public class ProfileResponse {
    private final String username;
    private final String bio;
    private final String image;
    private final boolean following;

    private ProfileResponse(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    public static ProfileResponse of(Profile profile) {
        return new ProfileResponse(
                valueOf(profile.getUserName()),
                profile.getBio(),
                valueOf(profile.getImage()),
                profile.isFollowing());
    }
}
