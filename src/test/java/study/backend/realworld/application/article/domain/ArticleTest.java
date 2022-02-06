package study.backend.realworld.application.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.backend.realworld.application.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleTest {

    @Mock
    private ArticleTitle title;

    @Mock
    private ArticleContents contents;

    @Mock
    private User author;

    @DisplayName("article의 favorite를 누르면 해당 user의 정보가 추가되고 true로 상태가 변경된다.")
    @Test
    void when_user_favorite_article(@Mock User someUser) {
        Article article = new Article(author, contents);

        Article favoritedArticle = article.addUserFavoriteArticle(someUser);
        assertThat(favoritedArticle.getFavoritedCount()).isEqualTo(1);
        assertThat(favoritedArticle.isFavorited()).isTrue();
    }

    @DisplayName("article의 unFavorite를 누르면 해당 user의 정보가 제거되고 false로 상태가 변경된다.")
    @Test
    void when_user_unFavorite_article(@Mock User someUser) {
        Article article = new Article(author, contents);
        Article favoritedArticle = article.addUserFavoriteArticle(someUser);

        assertThat(favoritedArticle.removeUserUnFavoriteArticle(someUser).getFavoritedCount()).isEqualTo(0);
        assertThat(favoritedArticle.removeUserUnFavoriteArticle(someUser).isFavorited()).isFalse();
    }

    @DisplayName("서로 다른 요소를 담은 Article 객체는 다른 hashCode를 반환한다.")
    @Test
    void when_not_same_article_equals_and_hashCode(@Mock ArticleContents otherContents, @Mock ArticleTitle otherTitle) {
        given(contents.getTitle()).willReturn(title);
        given(otherContents.getTitle()).willReturn(otherTitle);

        Article article = new Article(author, contents);
        Article otherArticle = new Article(author, otherContents);

        assertThat(article)
                .isNotEqualTo(otherArticle)
                .doesNotHaveSameHashCodeAs(otherArticle);
    }

    @DisplayName("글의 commets에 추가된 comment가 정상적으로 추가된다.")
    @Test
    void when_add_comment(@Mock User someUser) {
        Article article = new Article(author, contents);
        article.addComment(someUser, "this is comment");

        assertThat(article.getComments().size()).isEqualTo(1);
    }

}