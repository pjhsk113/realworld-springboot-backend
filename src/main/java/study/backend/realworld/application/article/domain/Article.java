package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "articles")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // TODO Comments

    public Article(User author, ArticleContents contents) {
        this.author = author;
        this.contents = contents;
    }

    public int getFavoritedCount() {
        return userFavorited.size();
    }
}
