package study.backend.realworld.application.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.exception.ExistsUserException;
import study.backend.realworld.application.user.domain.exception.UserNotFountException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Profile profile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> follows = new ArrayList<>();

    private User(String email, Profile profile, String password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    public static User of(String email, String username, String password) {
        Assert.notNull(email, "email has null");
        Assert.notNull(username, "username has null");
        Assert.notNull(password, "password has null");
        return new User(email, new Profile(username), password);
    }

    void setCurrentTime() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    void updateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfile(String email, Profile profile, String password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
        updateTime();
    }

    public void follow(User target) throws ExistsUserException {
        if (this.follows.contains(target)) {
            throw new ExistsUserException();
        }
        this.follows.add(target);
    }

    public void unfollow(User target) throws UserNotFountException {
        if (!this.follows.contains(target)) {
            throw new UserNotFountException();
        }
        this.follows.remove(target);
    }
}
