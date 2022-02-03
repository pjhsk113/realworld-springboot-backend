package study.backend.realworld.application.article.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;

@JsonRootName("article")
@AllArgsConstructor
@Getter
public class ArticleUpdateRequest {
    private String title;
    private String description;
    private String body;

    public ArticleUpdateModel toUpdateArticleModel() {
        return ArticleUpdateModel.of(title, description, body);
    }
}
