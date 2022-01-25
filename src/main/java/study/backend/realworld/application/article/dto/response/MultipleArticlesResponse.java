package study.backend.realworld.application.article.dto.response;

import org.springframework.data.domain.Page;
import study.backend.realworld.application.article.domain.Article;

import java.util.List;
import java.util.stream.Collectors;

public class MultipleArticlesResponse {
    private List<ArticleResponse> articles;
    private int articlesCount;

    private MultipleArticlesResponse(List<ArticleResponse> articles, int articlesCount) {
        this.articles = articles;
        this.articlesCount = articlesCount;
    }

    public static MultipleArticlesResponse from(Page<Article> articles) {
        List<ArticleResponse> articleList = articlesToList(articles);
        return new MultipleArticlesResponse(articleList, articleList.size());
    }

    private static List<ArticleResponse> articlesToList(Page<Article> articles) {
        return articles.map(ArticleResponse::from)
                .stream()
                .collect(Collectors.toList());
    }
}
