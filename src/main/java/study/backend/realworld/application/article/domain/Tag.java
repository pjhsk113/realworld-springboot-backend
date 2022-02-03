package study.backend.realworld.application.article.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
