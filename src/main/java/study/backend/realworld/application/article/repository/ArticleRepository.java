package study.backend.realworld.application.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Tag;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article save(Article article);

    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByContentsTags(Tag tag, Pageable pageable);
}
