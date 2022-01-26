package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Comment;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;
import study.backend.realworld.application.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Comment createComment(User user, String slug, String body) throws UserNotFountException {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFountException::new);
        Article findArticle = articleRepository.findArticleByContentsTitleSlug(slug);

        return findUser.addCommentToArticle(findArticle, body);
    }
}
