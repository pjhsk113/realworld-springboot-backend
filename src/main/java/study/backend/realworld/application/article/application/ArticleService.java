package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.article.repository.TagRepository;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;
import study.backend.realworld.application.user.repository.UserRepository;

import javax.security.sasl.AuthenticationException;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public Article createArticle(User user, ArticleContents contents) throws UserNotFountException {
        // 태그 정보 갱신
        Set<Tag> refreshTags = contents.getTags().stream()
                .map(tag -> tagRepository.findFirstByValue(String.valueOf(tag))
                        .orElse(tag))
                .collect(Collectors.toSet());

        contents.refreshTags(refreshTags);

        // user 정보 찾기
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFountException::new);

        // 찾은 user 정보를 article author에 매핑하기
        Article newArticle = new Article(findUser, contents);
        // 저장
        return articleRepository.save(newArticle);
    }

    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> findArticleByTag(String tagName, Pageable pageable) {
        return tagRepository.findFirstByValue(tagName)
                .map(tag -> articleRepository.findAllByContentsTags(tag, pageable))
                .orElse(Page.empty());
    }

    public Page<Article> findArticleByAuthor(String author, Pageable pageable) {
        UserName userName = new UserName(author);
        return articleRepository.findAllByAuthorProfileUserName(userName, pageable);
    }

    public Page<Article> findArticleFavoritedByUserName(UserName userName, Pageable pageable) throws UserNotFountException {
        User user = userRepository.findByProfileUserName(userName)
                .orElseThrow(UserNotFountException::new);

        return articleRepository.findAllByUserFavoritedContains(user, pageable)
                .map(article -> article.updateFavoritedUser(user));
    }

    public Page<Article> findArticleFeedByUserName(User user, Pageable pageable) throws UserNotFountException {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFountException::new);

        return articleRepository.findAllByUserFavoritedContains(findUser, pageable)
                .map(article -> article.updateFavoritedUser(user));
    }

    public Article findArticleBySlug(String slug) {
        return articleRepository.findArticleByContentsTitleSlug(slug);
    }

    public Article updateArticle(User user, String slug, ArticleUpdateModel request) throws UserNotFountException, AuthenticationException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFountException::new);
        return findUser.updateArticle(findArticleBySlug(slug), request);
    }

    public Article favoriteArticle(User user, String slug) throws UserNotFountException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFountException::new);
        Article findArticle = findArticleBySlug(slug);
        return findUser.favoriteArticle(findArticle);
    }

    public Article unfavoriteArticle(User user, String slug) throws UserNotFountException {
        User findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFountException::new);
        Article findArticle = findArticleBySlug(slug);
        return findUser.unfavoriteArticle(findArticle);
    }
}
