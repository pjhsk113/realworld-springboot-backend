package study.backend.realworld.application.article.application.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.application.ArticleFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class CommentCommandExecutor {
    private final ArticleFindProcessor articleFindProcessor;
    private final UserFindProcessor userFindProcessor;
    private final CommentCommandManager commandManager;

    public Comment createComment(User user, String slug, String body) throws UserNotFountException {
        Article article = articleFindProcessor.findBySlug(slug);
        Comment comment = userFindProcessor.findById(user.getId())
                .addCommentToArticle(article, body);

        return commandManager.saveComment(comment);
    }

    public void deleteCommentById(User user, String slug, long commentId) throws UserNotFountException, AuthenticationException {
        Article article = articleFindProcessor.findBySlug(slug);

        Comment comment = userFindProcessor.findById(user.getId())
                .deleteArticleComment(article, commentId);

        commandManager.deleteComment(comment);
    }
}
