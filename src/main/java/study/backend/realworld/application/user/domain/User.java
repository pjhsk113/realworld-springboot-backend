package study.backend.realworld.application.user.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;
import study.backend.realworld.application.user.exception.ExistsUserException;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.persistence.*;
import javax.security.sasl.AuthenticationException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
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
    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "id"))
    private Set<User> follows = new HashSet<>();

    @ManyToMany(mappedBy = "userFavorited")
    private Set<Article> articleFavorited = new HashSet<>();

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

    public Article updateArticle(Article article, ArticleUpdateModel updateArticleInformation) throws AuthenticationException {
        if (article.getAuthor() != this) {
            throw new AuthenticationException("You are not authorized to update this article");
        }
        article.updateArticle(updateArticleInformation);
        return article;
    }

    public Article favoriteArticle(Article article) {
        articleFavorited.add(article);
        return article.addUserFavoriteArticle(this);
    }

    public Article unfavoriteArticle(Article article) {
        articleFavorited.remove(article);
        return article.removeUserUnFavoriteArticle(this);
    }

    public Comment addCommentToArticle(Article article, String body) {
        return article.addComment(this, body);
    }

    public Comment deleteArticleComment(Article article, long commentId) throws AuthenticationException {
        return article.removeComment(this, commentId);
    }

    public Set<Comment> getArticleComments(Article article) {
        return article.getComments().stream()
                .map(this::getComment)
                .collect(Collectors.toSet());
    }

    private Comment getComment(Comment comment) {
        refreshFollowingStatus(comment.getAuthor());
        return comment;
    }
    private Profile refreshFollowingStatus(User user) {
        return user.profile.followingStatus(follows.contains(user));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
