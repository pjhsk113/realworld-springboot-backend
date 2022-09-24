package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.user.domain.User;

@Transactional
@Component
@RequiredArgsConstructor
public class ArticleCommandManager {

    private final ArticleRepository articleRepository;

    public Article saveArticle(User user, ArticleContents contents) {
        Article article = new Article(user, contents);
        return articleRepository.save(article);
    }

    public void deleteArticle(User user, String slug) {
        articleRepository.deleteArticleByAuthorAndContentsTitleSlug(user, slug);
    }

}
