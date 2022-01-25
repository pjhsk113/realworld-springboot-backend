package study.backend.realworld.application.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleUpdateRequest {
    private String title;
    private String description;
    private String body;

    public ArticleUpdateModel toUpdateArticleModel() {
        return ArticleUpdateModel.of(title, description, body);
    }
}
