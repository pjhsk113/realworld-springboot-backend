package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.application.tag.TagInformationFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import javax.security.sasl.AuthenticationException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleCommandExecutor {

    private final TagInformationFindProcessor tagInformationFindProcessor;
    private final ArticleCommandManager articleCommandManager;
    private final ArticleFindProcessor articleFindProcessor;
    private final UserFindProcessor userFindProcessor;

    public Article createArticle(User user, ArticleContents contents) throws UserNotFountException {
        Set<Tag> mappedTags = tagInformationFindProcessor.findMappedTags(contents);

        ArticleContents reloadContents = contents.refreshTags(mappedTags);

        User findUser = userFindProcessor.findById(user.getId());

        return articleCommandManager.saveArticle(findUser, reloadContents);
    }

    public Article updateArticle(User user, String slug, ArticleUpdateModel updateArticleInformation) throws UserNotFountException, AuthenticationException {
        Article findArticle = articleFindProcessor.findBySlug(slug);

        return userFindProcessor.findById(user.getId())
                .updateArticle(findArticle, updateArticleInformation);
    }

    public void deleteArticleBySlug(User user, String slug) throws UserNotFountException {
        User findUser = userFindProcessor.findById(user.getId());
        articleCommandManager.deleteArticle(findUser, slug);
    }

    public Article favoriteArticle(User user, String slug) throws UserNotFountException {
        Article findArticle = articleFindProcessor.findBySlug(slug);

        return userFindProcessor.findById(user.getId())
                .favoriteArticle(findArticle);
    }

    public Article unfavoriteArticle(User user, String slug) throws UserNotFountException {
        Article findArticle = articleFindProcessor.findBySlug(slug);

        return userFindProcessor.findById(user.getId())
                .unfavoriteArticle(findArticle);
    }
}
