package study.backend.realworld.application.article.application.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.article.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class CommentCommandManager {

    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
