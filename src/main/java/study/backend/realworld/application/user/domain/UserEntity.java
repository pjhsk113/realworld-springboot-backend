package study.backend.realworld.application.user.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.backend.realworld.application.user.exception.ExistsUserException;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private ProfileEntity profile;

    @Embedded
    private Password password;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> follows = new HashSet<>();

    private UserEntity(Email email, ProfileEntity profile, Password password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    public static UserEntity of(Email email, UserName name, Password password) {
        Assert.notNull(email, "email has null");
        Assert.notNull(name, "username has null");
        Assert.notNull(password, "password has null");
        return new UserEntity(email, new ProfileEntity(name), password);
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

    public boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return password.matchesPassword(rawPassword, passwordEncoder);
    }
}
