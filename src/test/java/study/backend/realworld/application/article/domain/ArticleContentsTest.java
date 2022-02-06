package study.backend.realworld.application.article.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import study.backend.realworld.application.article.domain.model.ArticleUpdateModel;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleContentsTest {

    private ArticleContents contents;

    @BeforeEach
    void setUp() {
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag("tag1"));
        tagList.add(new Tag("tag2"));
        tagList.add(new Tag("tag3"));
        contents = new ArticleContents(ArticleTitle.of("title"), "description", "body", tagList);
    }

    @DisplayName("tagList의 타입이 String으로 변환되어 반환된다.")
    @Test
    void tag_list_convert_type_to_string() {
        assertThat(contents.convertTagTypeToString()).hasOnlyElementsOfType(String.class);
    }

    @DisplayName("tag 정보가 중복 입력되었을 경우 중복 제거가 된다.")
    @Test
    void when_duplicate_tag_list_() {
        contents.getTags().add(new Tag("tag1"));
        contents.getTags().add(new Tag("tag2"));

        assertThat(contents.getTags().size()).isEqualTo(3);
    }

    @DisplayName("ArticleContents의 업데이트는 null이 아닌 요소에만 적용된다.")
    @ParameterizedTest
    @MethodSource("articleUpdateModel")
    void update_articleContent(ArticleUpdateModel model, String expectTitle, String expectDescription, String expectBody) {
        contents.updateArticleContents(model);

        assertThat(contents.getTitle().getTitle()).isEqualTo(expectTitle);
        assertThat(contents.getDescription()).isEqualTo(expectDescription);
        assertThat(contents.getBody()).isEqualTo(expectBody);
    }

    private static Stream<Arguments> articleUpdateModel() {
        return Stream.of(
                Arguments.of(ArticleUpdateModel.of("updateTitle", "updateDescription", "updateBody"),
                        "updateTitle", "updateDescription", "updateBody"),

                Arguments.of(ArticleUpdateModel.of(null, "updateDescription", "updateBody"),
                        "title", "updateDescription", "updateBody"),

                Arguments.of(ArticleUpdateModel.of("updateTitle", null, "updateBody"),
                        "updateTitle", "description", "updateBody"),

                Arguments.of(ArticleUpdateModel.of("updateTitle", "updateDescription", null),
                        "updateTitle", "updateDescription", "body")
        );
    }
}