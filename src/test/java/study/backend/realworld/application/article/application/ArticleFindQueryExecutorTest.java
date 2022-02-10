package study.backend.realworld.application.article.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import study.backend.realworld.application.article.application.tag.TagInformationFindProcessor;
import study.backend.realworld.application.user.application.UserFindProcessor;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.exception.UserNotFountException;

@ExtendWith(MockitoExtension.class)
class ArticleFindQueryExecutorTest {

    @Mock
    private TagInformationFindProcessor tagInformationFindProcessor;

    @Mock
    private ArticleFindProcessor articleFindProcessor;

    @Mock
    private UserFindProcessor userFindProcessor;

    @Spy
    User author;

    @Test
    void when_find_all_articles_success() throws UserNotFountException {
        //given


        //when


        //then
    }

    @Test
    void when_create_article_expect_refresh_tags() {
        //given


        //when


        //then
    }
}