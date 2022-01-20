package study.backend.realworld.application.article.dto.response;

import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.user.dto.response.ProfileResponse;

import java.time.LocalDateTime;
import java.util.Set;

public class PostArticleResponse {
    private String slug;
    private String title;
    private String description;
    private String body;
    private Set<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private ProfileResponse author;

    private PostArticleResponse(String slug, String title, String description, String body, Set<String> tagList, LocalDateTime createdAt, LocalDateTime updatedAt, boolean favorited, int favoritesCount, ProfileResponse author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.author = author;
    }

    public static PostArticleResponse of(Article article) {
        final ArticleContents contents = article.getContents();
        final ArticleTitle titles = contents.getTitle();

        return new PostArticleResponse(
                titles.getSlug(),
                titles.getTitle(),
                contents.getDescription(),
                contents.getBody(),
                contents.tagToString(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.isFavorited(),
                article.getFavoritedCount(),
                ProfileResponse.of(article.getAuthor().getProfile())
        );
    }
}
