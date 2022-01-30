package study.backend.realworld.application.article.application.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.application.ArticleFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentFindQueryExecutor {
    private final ArticleFindProcessor articleFindProcessor;
    private final UserFindProcessor userFindProcessor;

    public Set<Comment> findAllCommentsOnArticle(User user, String slug) throws UserNotFountException {
        Article article = articleFindProcessor.findBySlug(slug);

        return userFindProcessor.findById(user.getId())
                .getArticleComments(article);
    }
}
