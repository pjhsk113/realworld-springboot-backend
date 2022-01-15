package study.backend.realworld.application.user.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import study.backend.realworld.application.user.domain.User;

import static java.lang.String.valueOf;

@Getter
@JsonRootName("user")
public class UserResponse {
    private final String email;
    private final String token;
    private final String username;
    private final String bio;
    private final String image;

    private UserResponse(String email, String token, String username, String bio, String image) {
        this.email = email;
        this.token = token;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                valueOf(user.getEmail()),
                "",
                valueOf(user.getName()),
                user.getBio(),
                valueOf(user.getImage()));
    }

    public static UserResponse of(User user, String token) {
        return new UserResponse(
                valueOf(user.getEmail()),
                token,
                valueOf(user.getName()),
                user.getBio(),
                valueOf(user.getImage()));
    }
}
