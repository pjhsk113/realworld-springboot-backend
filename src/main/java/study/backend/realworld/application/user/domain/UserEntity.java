package study.backend.realworld.application.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        return new UserEntity(email, new ProfileEntity(name), password);
    }
}
