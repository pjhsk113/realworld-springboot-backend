package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ArticleContents {

    @Embedded
    private ArticleTitle title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String body;

    @JoinTable(name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Tag> tags = new HashSet<>();

    public ArticleContents(ArticleTitle title, String description, String body, Set<Tag> tags) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    public Set<String> tagToString() {
        return tags.stream()
                .map(Tag::toString)
                .collect(Collectors.toSet());
    }
}
