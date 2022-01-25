package study.backend.realworld.application.user.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;
import study.backend.realworld.application.user.exception.ExistsUserException;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.persistence.*;
import javax.security.sasl.AuthenticationException;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EqualsAndHashCode
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Profile profile;

    @Embedded
    private Password password;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"))
    private Set<User> follows = new HashSet<>();

    private User(Email email, Profile profile, Password password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    public static User of(Email email, UserName name, Password password) {
        Assert.notNull(email, "email has null");
        Assert.notNull(name, "username has null");
        Assert.notNull(password, "password has null");
        return new User(email, new Profile(name), password);
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

    public UserName getName() {
        return profile.getUserName();
    }

    public String getBio() {
        return profile.getBio();
    }

    public Image getImage() {
        return profile.getImage();
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changeName(UserName userName) {
        profile.changeUserName(userName);
    }

    public Article updateArticle(Article article, ArticleUpdateModel request) throws AuthenticationException {
        if (article.getAuthor() != this) {
            throw new AuthenticationException("You are not authorized to update this article");
        }
        article.updateArticle(request);
        return article;
    }
}
