package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;
import study.backend.realworld.application.user.domain.User;

import javax.persistence.*;
import javax.security.sasl.AuthenticationException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Table(name = "articles")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Article extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Embedded
    private ArticleContents contents;

    @JoinTable(name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<User> userFavorited = new HashSet<>();

    private boolean favorited = false;

    @OneToMany(mappedBy = "article", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments = new HashSet<>();

    public Article(User author, ArticleContents contents) {
        this.author = author;
        this.contents = contents;
    }

    public int getFavoritedCount() {
        return userFavorited.size();
    }

    public Article updateFavoritedUser(User user) {
        this.favorited = userFavorited.contains(user);
        return this;
    }

    public void updateArticle(ArticleUpdateModel updateModel) {
        contents.updateArticleContents(updateModel);
    }

    public Article addUserFavoriteArticle(User user) {
        userFavorited.add(user);
        return updateFavoritedUser(user);
    }

    public Article removeUserUnFavoriteArticle(User user) {
        userFavorited.remove(user);
        return updateFavoritedUser(user);
    }

    public Comment addComment(User author, String body) {
        Comment brandNewComment = new Comment(this, author, body);
        comments.add(brandNewComment);
        return brandNewComment;
    }

    public void removeComment(User user, long commentId) throws AuthenticationException {
        Comment findComment = comments.stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        validateUserDeletePermission(user, findComment);

        comments.remove(findComment);
    }

    private void validateUserDeletePermission(User user, Comment comment) throws AuthenticationException {
        if (user.equals(author) || user.equals(comment.getAuthor())) {
            throw new AuthenticationException("Not authorized to delete this comment");
        }
    }
}
