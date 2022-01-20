package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.user.domain.User;

import javax.persistence.*;

@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EqualsAndHashCode
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
}
