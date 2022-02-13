package study.backend.realworld.application.article.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import study.backend.realworld.application.article.application.tag.TagInformationFindProcessor;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleCommandExecutorTest {

    private ArticleCommandExecutor articleCommandExecutor;

    @Mock
    private TagInformationFindProcessor tagInformationFindProcessor;

    @Mock
    private ArticleCommandManager articleCommandManager;

    @Mock
    private ArticleFindProcessor articleFindProcessor;

    @Mock
    private UserFindProcessor userFindProcessor;

    @Spy
    User author;

    @BeforeEach
    void initArticleCommandExecutor() {
        articleCommandExecutor = new ArticleCommandExecutor(tagInformationFindProcessor, articleCommandManager, articleFindProcessor, userFindProcessor);
    }

    @Test
    void when_delete_article_user_not_found_will_return_UserNotFoundException(@Mock User user) throws UserNotFountException {
        //given
        given(userFindProcessor.findById(anyLong())).willThrow(new UserNotFountException());

        assertThatThrownBy(() ->
                articleCommandExecutor.deleteArticleBySlug(user,"slug"))
                .isInstanceOf(UserNotFountException.class)
                .hasMessage("member not found");
    }

    @DisplayName("article 생성 성공")
    @Test
    void when_create_article(@Mock User user, @Mock ArticleContents contents) throws UserNotFountException {
        given(userFindProcessor.findById(anyLong())).willReturn(author);

        Set<Tag> mappedTags = tagInformationFindProcessor.findMappedTags(contents);
        ArticleContents reloadContents = contents.refreshTags(mappedTags);
        Article expectArticle = articleCommandManager.saveArticle(user, reloadContents);

        assertThat(articleCommandExecutor.createArticle(user, contents)).isEqualTo(expectArticle);
    }

}