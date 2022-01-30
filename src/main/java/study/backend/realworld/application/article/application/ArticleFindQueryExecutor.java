package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.application.tag.TagInformationFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleFindQueryExecutor {
    private final TagInformationFindProcessor tagInformationFindProcessor;
    private final ArticleFindProcessor articleFindProcessor;
    private final UserFindProcessor userFindProcessor;

    public Page<Article> findAllArticles(Pageable pageable) {
        return articleFindProcessor.findAll(pageable);
    }

    public Page<Article> findArticleByTag(String tagName, Pageable pageable) {
        Optional<Tag> firstTagValue = tagInformationFindProcessor.findTagValue(tagName);

        return articleFindProcessor.findByTag(firstTagValue, pageable);
    }

    public Page<Article> findArticleByAuthor(String author, Pageable pageable) {
        return articleFindProcessor.findByAuthor(author, pageable);
    }

    public Page<Article> findArticleFavoritedByUserName(UserName userName, Pageable pageable) throws UserNotFountException {
        User user = userFindProcessor.findByUserName(userName);

        return articleFindProcessor.findArticleWithFavoriteUser(user, pageable);
    }

    public Page<Article> findArticleFeedByUserName(User user, Pageable pageable) throws UserNotFountException {
        User findUser = userFindProcessor.findById(user.getId());

        return articleFindProcessor.findArticleWithFavoriteUser(findUser, pageable);
    }

    public Article findArticleBySlug(String slug) {
        return articleFindProcessor.findBySlug(slug);
    }
}
