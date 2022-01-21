package study.backend.realworld.application.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.backend.realworld.application.article.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAll();
    Optional<Tag> findFirstByValue(String value);
}
