package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "value", unique = true, nullable = false)
    private String value;

    public Tag(String value) {
        this.value = value;
    }
}
