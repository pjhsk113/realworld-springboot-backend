package study.backend.realworld.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.User;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Comment extends BaseTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Article article;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Column(name = "body", nullable = false)
    private String body;

    public Comment(Article article, User author, String body) {
        this.article = article;
        this.author = author;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(article, comment.article) && Objects.equals(author, comment.author) && Objects.equals(body, comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, author, body);
    }
}
