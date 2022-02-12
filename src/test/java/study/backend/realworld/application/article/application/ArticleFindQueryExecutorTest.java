package study.backend.realworld.application.article.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import study.backend.realworld.application.article.application.tag.TagInformationFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.exception.UserNotFountException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ArticleFindQueryExecutorTest {

    private ArticleFindQueryExecutor articleFindQueryExecutor;

    @Mock
    private TagInformationFindProcessor tagInformationFindProcessor;

    @Mock
    private ArticleFindProcessor articleFindProcessor;

    @Mock
    private UserFindProcessor userFindProcessor;

    @Spy
    User author;

    @BeforeEach
    void initArticleFindQueryExecutor() {
        articleFindQueryExecutor = new ArticleFindQueryExecutor(tagInformationFindProcessor, articleFindProcessor, userFindProcessor);
    }

    @DisplayName("모든 article 조회 성공")
    @Test
    void when_find_all_articles(@Mock Page<Article> articles) {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        given(articleFindProcessor.findAll(pageable)).willReturn(articles);

        //when & then
        assertThat(articleFindQueryExecutor.findAllArticles(pageable)).isEqualTo(articles);
    }

    @DisplayName("tag로 article 조회하기")
    @Test
    void when_find_article_by_tag() {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        given(tagInformationFindProcessor.findTagValue(anyString())).willReturn(Optional.of(mock(Tag.class)));

        //when
        Optional<Tag> tag = tagInformationFindProcessor.findTagValue("tag");
        Page<Article> articles = articleFindProcessor.findByTag(tag, pageable);

        //then
        assertThat(articleFindQueryExecutor.findArticleByTag("tag", pageable)).isEqualTo(articles);
    }

    @DisplayName("사용자 이름으로 article 조회하기")
    @Test
    void when_find_article_by_userName(@Mock Page<Article> articles) {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        given(articleFindProcessor.findByAuthor("username", pageable)).willReturn(articles);

        //when
        Page<Article> pageArticles = articleFindProcessor.findByAuthor("username", pageable);

        //then
        assertThat(articleFindQueryExecutor.findArticleByAuthor("username", pageable)).isEqualTo(pageArticles);
    }

    @DisplayName("favorited 누른 사용자의 이름으로 article 조회하기")
    @Test
    void when_find_article_favorited_by_userName() throws UserNotFountException {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        UserName userName = new UserName("username");
        given(userFindProcessor.findByUserName(userName)).willReturn(author);

        //when
        Page<Article> articles = articleFindProcessor.findArticleWithFavoriteUser(author, pageable);

        //then
        assertThat(articleFindQueryExecutor.findArticleFavoritedByUserName(userName, pageable)).isEqualTo(articles);
    }

    @DisplayName("사용자 조회에 실패했을 경우 UserNotFoundException이 발생한다.")
    @Test
    void when_find_article_by_userName_not_exist_will_throws_user_not_found_exception() throws UserNotFountException {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        given(userFindProcessor.findByUserName(any())).willThrow(new UserNotFountException());

        assertThatThrownBy(() ->
                articleFindQueryExecutor.findArticleFavoritedByUserName(any(), pageable))
                .isInstanceOf(UserNotFountException.class)
                .hasMessage("member not found");
    }

    @DisplayName("즐겨찾은 유저의 article feed 조회하기")
    @Test
    void when_find_article_feed_by_userName(@Mock User user) throws UserNotFountException {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        given(userFindProcessor.findById(anyLong())).willReturn(author);

        //when
        Page<Article> articles = articleFindProcessor.findArticleWithFavoriteUser(author, pageable);

        //then
        assertThat(articleFindQueryExecutor.findArticleFeedByUserName(user, pageable)).isEqualTo(articles);
    }

    @DisplayName("slug로 article 찾기")
    @Test
    void when_find_article_by_slug(@Mock Article article) {
        //given
        given(articleFindProcessor.findBySlug(anyString())).willReturn(article);

        assertThat(articleFindQueryExecutor.findArticleBySlug("slug")).isEqualTo(article);
    }

    @DisplayName("slug로 article 조회 실패시 NoSuchElementException이 발생한다.")
    @Test
    void when_find_article_by_slug_not_exist_will_throws_exception() {
        //given
        given(articleFindProcessor.findBySlug(anyString())).willThrow(new NoSuchElementException());

        assertThatThrownBy(() ->
                articleFindQueryExecutor.findArticleBySlug("slug"))
                .isInstanceOf(NoSuchElementException.class);
    }
}