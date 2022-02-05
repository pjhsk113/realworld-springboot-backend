package study.backend.realworld.application.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.backend.realworld.application.article.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
}
