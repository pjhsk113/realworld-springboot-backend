import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleFindProcessor {
    private final ArticleRepository articleRepository;

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> findByTag(Optional<Tag> tags, Pageable pageable) {
        return tags.map(tag -> articleRepository.findAllByContentsTags(tag, pageable))
                .orElse(Page.empty());
    }

    public Page<Article> findByAuthor(String author, Pageable pageable) {
        UserName userName = new UserName(author);
        return articleRepository.findAllByAuthorProfileUserName(userName, pageable);
    }

    public Page<Article> findArticleWithFavoriteUser(User user, Pageable pageable) {
        return articleRepository.findAllByUserFavoritedContains(user, pageable)
                .map(article -> article.updateFavoritedUser(user));
    }

    public Article findBySlug(String slug) {
        return articleRepository.findArticleByContentsTitleSlug(slug);
    }
}
